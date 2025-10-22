package site.hohyun.api.tetris.service;

import org.springframework.stereotype.Service;
import site.hohyun.api.tetris.domain.TetrisDTO;
import lombok.RequiredArgsConstructor;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 테트리스 게임 로직 서비스
 * 게임 규칙, 점수 계산, 멀티플레이어 동기화를 담당
 */
@Service
@RequiredArgsConstructor
public class TetrisService 
{
    // 게임 상수
    private static final int BOARD_WIDTH = 10;
    private static final int BOARD_HEIGHT = 20;
    
    // 테트리스 블록 정의
    private static final List<TetrisDTO.TetrisPiece> TETRIS_PIECES = Arrays.asList(
        new TetrisDTO.TetrisPiece(new int[][]{{1,1,1,1}}, "#00f0f0", 0, 0, 0), // I
        new TetrisDTO.TetrisPiece(new int[][]{{1,1},{1,1}}, "#f0f000", 0, 0, 0), // O
        new TetrisDTO.TetrisPiece(new int[][]{{0,1,0},{1,1,1}}, "#a000f0", 0, 0, 0), // T
        new TetrisDTO.TetrisPiece(new int[][]{{0,1,1},{1,1,0}}, "#00f000", 0, 0, 0), // S
        new TetrisDTO.TetrisPiece(new int[][]{{1,1,0},{0,1,1}}, "#f00000", 0, 0, 0), // Z
        new TetrisDTO.TetrisPiece(new int[][]{{1,0,0},{1,1,1}}, "#f0a000", 0, 0, 0), // J
        new TetrisDTO.TetrisPiece(new int[][]{{0,0,1},{1,1,1}}, "#0000f0", 0, 0, 0)  // L
    );
    
    // 게임 상태 저장소
    private final Map<String, TetrisDTO> gameStates = new ConcurrentHashMap<>();
    private final Map<String, TetrisDTO.GameRoom> gameRooms = new ConcurrentHashMap<>();
    
    // 점수 기록 저장소 (실제 구현에서는 데이터베이스 사용)
    private final List<TetrisDTO.GameResult> gameResults = new ArrayList<>();
    private final Map<String, List<TetrisDTO.GameResult>> playerScores = new ConcurrentHashMap<>();
    
    /**
     * 새 게임 시작
     */
    public TetrisDTO startNewGame(String playerId, String gameId) 
    {
        System.out.println("새 게임 시작 - 플레이어: " + playerId + ", 게임ID: " + gameId);
        
        TetrisDTO gameState = new TetrisDTO();
        gameState.setGameId(gameId);
        gameState.setPlayerId(playerId);
        gameState.setBoard(createEmptyBoard());
        gameState.setCurrentPiece(generateRandomPiece());
        gameState.setNextPiece(generateRandomPiece());
        gameState.setScore(0);
        gameState.setLevel(1);
        gameState.setLines(0);
        gameState.setGameOver(false);
        gameState.setGamePaused(false);
        gameState.setTimestamp(System.currentTimeMillis());
        
        gameStates.put(gameId, gameState);
        return gameState;
    }
    
    /**
     * 게임 액션 처리
     */
    public TetrisDTO processGameAction(String gameId, TetrisDTO.GameAction action) 
    {
        TetrisDTO gameState = gameStates.get(gameId);
        if (gameState == null || gameState.isGameOver()) {
            return gameState;
        }
        
        System.out.println("게임 액션 처리: " + action.getAction() + " - 게임ID: " + gameId);
        
        switch (action.getAction()) {
            case "MOVE":
                if (isValidMove(gameState, action.getDx(), action.getDy())) {
                    gameState.getCurrentPiece().setX(gameState.getCurrentPiece().getX() + action.getDx());
                    gameState.getCurrentPiece().setY(gameState.getCurrentPiece().getY() + action.getDy());
                }
                break;
                
            case "ROTATE":
                if (isValidRotation(gameState)) {
                    rotatePiece(gameState.getCurrentPiece());
                }
                break;
                
            case "DROP":
                dropPiece(gameState);
                break;
                
            case "PAUSE":
                gameState.setGamePaused(true);
                break;
                
            case "RESUME":
                gameState.setGamePaused(false);
                break;
        }
        
        gameState.setTimestamp(System.currentTimeMillis());
        return gameState;
    }
    
    /**
     * 게임 상태 업데이트 (자동 낙하)
     */
    public TetrisDTO updateGameState(String gameId) 
    {
        TetrisDTO gameState = gameStates.get(gameId);
        if (gameState == null || gameState.isGameOver() || gameState.isGamePaused()) {
            return gameState;
        }
        
        // 자동 낙하 처리
        if (isValidMove(gameState, 0, 1)) {
            gameState.getCurrentPiece().setY(gameState.getCurrentPiece().getY() + 1);
        } else {
            // 블록을 보드에 고정
            placePiece(gameState);
            // 완성된 라인 체크 및 제거
            int linesCleared = clearLines(gameState);
            if (linesCleared > 0) {
                updateScore(gameState, linesCleared);
            }
            // 새 블록 생성
            spawnNewPiece(gameState);
        }
        
        gameState.setTimestamp(System.currentTimeMillis());
        return gameState;
    }
    
    /**
     * 게임 룸 생성
     */
    public TetrisDTO.GameRoom createGameRoom(String roomId, int maxPlayers) 
    {
        TetrisDTO.GameRoom room = new TetrisDTO.GameRoom();
        room.setRoomId(roomId);
        room.setPlayers(new ArrayList<>());
        room.setMaxPlayers(maxPlayers);
        room.setActive(true);
        room.setCreatedAt(System.currentTimeMillis());
        
        gameRooms.put(roomId, room);
        System.out.println("게임 룸 생성: " + roomId);
        return room;
    }
    
    /**
     * 플레이어를 게임 룸에 추가
     */
    public boolean joinGameRoom(String roomId, String playerId) 
    {
        TetrisDTO.GameRoom room = gameRooms.get(roomId);
        if (room == null || room.getPlayers().size() >= room.getMaxPlayers()) {
            return false;
        }
        
        room.getPlayers().add(playerId);
        System.out.println("플레이어 " + playerId + "가 룸 " + roomId + "에 참가");
        return true;
    }
    
    /**
     * 게임 룸의 모든 플레이어 상태 조회
     */
    public List<TetrisDTO> getRoomGameStates(String roomId) 
    {
        TetrisDTO.GameRoom room = gameRooms.get(roomId);
        if (room == null) {
            return new ArrayList<>();
        }
        
        List<TetrisDTO> states = new ArrayList<>();
        for (String playerId : room.getPlayers()) {
            // 플레이어별 게임 상태를 찾아서 반환
            gameStates.values().stream()
                .filter(state -> state.getPlayerId().equals(playerId))
                .forEach(states::add);
        }
        return states;
    }
    
    /**
     * 점수 계산 및 저장
     */
    public TetrisDTO.ScoreResult calculateAndSaveScore(String playerId, int lines, int level, long gameTime) 
    {
        System.out.println("점수 계산 요청 - 플레이어: " + playerId + ", 라인: " + lines + ", 레벨: " + level + ", 시간: " + gameTime);
        
        // 기본 점수 계산
        int baseScore = lines * 100;
        
        // 레벨 보너스
        int levelBonus = level * 50;
        
        // 시간 보너스 (빠른 클리어 보너스)
        int timeBonus = Math.max(0, (int)((300000 - gameTime) / 1000) * 10); // 5분 이내 클리어 시 보너스
        
        int totalScore = baseScore + levelBonus + timeBonus;
        
        // 순위 계산
        int rank = calculateRank(totalScore);
        
        TetrisDTO.ScoreResult result = new TetrisDTO.ScoreResult();
        result.setBaseScore(baseScore);
        result.setLevelBonus(levelBonus);
        result.setTimeBonus(timeBonus);
        result.setTotalScore(totalScore);
        result.setRank(rank);
        result.setMessage("훌륭한 점수입니다! 순위: " + rank + "위");
        
        System.out.println("점수 계산 완료: " + totalScore + "점 (순위: " + rank + "위)");
        return result;
    }
    
    /**
     * 게임 결과 저장
     */
    public TetrisDTO.GameResult saveGameResult(TetrisDTO.GameResult gameResult) 
    {
        System.out.println("게임 결과 저장 - 플레이어: " + gameResult.getPlayerId() + ", 점수: " + gameResult.getFinalScore());
        
        // 타임스탬프 설정
        gameResult.setTimestamp(System.currentTimeMillis());
        
        // 개인 최고 기록 확인
        List<TetrisDTO.GameResult> playerHistory = playerScores.getOrDefault(gameResult.getPlayerId(), new ArrayList<>());
        boolean isPersonalBest = playerHistory.stream()
            .allMatch(result -> result.getFinalScore() < gameResult.getFinalScore());
        gameResult.setPersonalBest(isPersonalBest);
        
        // 결과 저장
        gameResults.add(gameResult);
        playerHistory.add(gameResult);
        playerScores.put(gameResult.getPlayerId(), playerHistory);
        
        // 전체 순위 업데이트
        updateRanks();
        
        System.out.println("게임 결과 저장 완료 - 개인 최고: " + isPersonalBest);
        return gameResult;
    }
    
    /**
     * 플레이어 점수 기록 조회
     */
    public List<TetrisDTO.GameResult> getPlayerScores(String playerId) 
    {
        List<TetrisDTO.GameResult> scores = playerScores.getOrDefault(playerId, new ArrayList<>());
        return scores.stream()
            .sorted((a, b) -> Integer.compare(b.getFinalScore(), a.getFinalScore()))
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * 리더보드 조회
     */
    public List<TetrisDTO.GameResult> getLeaderboard(int limit) 
    {
        return gameResults.stream()
            .sorted((a, b) -> Integer.compare(b.getFinalScore(), a.getFinalScore()))
            .limit(limit)
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }
    
    /**
     * 순위 계산
     */
    private int calculateRank(int score) 
    {
        long higherScores = gameResults.stream()
            .mapToInt(TetrisDTO.GameResult::getFinalScore)
            .filter(s -> s > score)
            .count();
        return (int) higherScores + 1;
    }
    
    /**
     * 모든 순위 업데이트
     */
    private void updateRanks() 
    {
        List<TetrisDTO.GameResult> sortedResults = gameResults.stream()
            .sorted((a, b) -> Integer.compare(b.getFinalScore(), a.getFinalScore()))
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
        
        for (int i = 0; i < sortedResults.size(); i++) 
        {
            sortedResults.get(i).setRank(i + 1);
        }
    }
    
    // === 게임 로직 메서드들 ===
    
    private int[][] createEmptyBoard() 
    {
        int[][] board = new int[BOARD_HEIGHT][BOARD_WIDTH];
        for (int i = 0; i < BOARD_HEIGHT; i++) {
            Arrays.fill(board[i], 0);
        }
        return board;
    }
    
    private TetrisDTO.TetrisPiece generateRandomPiece() 
    {
        TetrisDTO.TetrisPiece piece = TETRIS_PIECES.get(new Random().nextInt(TETRIS_PIECES.size()));
        TetrisDTO.TetrisPiece newPiece = new TetrisDTO.TetrisPiece();
        newPiece.setShape(piece.getShape());
        newPiece.setColor(piece.getColor());
        newPiece.setX(BOARD_WIDTH / 2 - piece.getShape()[0].length / 2);
        newPiece.setY(0);
        newPiece.setRotation(0);
        return newPiece;
    }
    
    private boolean isValidMove(TetrisDTO gameState, int dx, int dy) 
    {
        TetrisDTO.TetrisPiece piece = gameState.getCurrentPiece();
        int newX = piece.getX() + dx;
        int newY = piece.getY() + dy;
        
        return isValidPosition(gameState.getBoard(), piece.getShape(), newX, newY);
    }
    
    private boolean isValidRotation(TetrisDTO gameState) 
    {
        TetrisDTO.TetrisPiece piece = gameState.getCurrentPiece();
        int[][] rotatedShape = rotateShape(piece.getShape());
        
        return isValidPosition(gameState.getBoard(), rotatedShape, piece.getX(), piece.getY());
    }
    
    private boolean isValidPosition(int[][] board, int[][] shape, int x, int y) 
    {
        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j] != 0) {
                    int boardX = x + j;
                    int boardY = y + i;
                    
                    if (boardX < 0 || boardX >= BOARD_WIDTH || 
                        boardY >= BOARD_HEIGHT || 
                        (boardY >= 0 && board[boardY][boardX] != 0)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    private void rotatePiece(TetrisDTO.TetrisPiece piece) 
    {
        piece.setShape(rotateShape(piece.getShape()));
        piece.setRotation((piece.getRotation() + 1) % 4);
    }
    
    private int[][] rotateShape(int[][] shape) 
    {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        return rotated;
    }
    
    private void dropPiece(TetrisDTO gameState) 
    {
        while (isValidMove(gameState, 0, 1)) {
            gameState.getCurrentPiece().setY(gameState.getCurrentPiece().getY() + 1);
        }
    }
    
    private void placePiece(TetrisDTO gameState) 
    {
        TetrisDTO.TetrisPiece piece = gameState.getCurrentPiece();
        int[][] board = gameState.getBoard();
        
        for (int i = 0; i < piece.getShape().length; i++) {
            for (int j = 0; j < piece.getShape()[i].length; j++) {
                if (piece.getShape()[i][j] != 0) {
                    int boardY = piece.getY() + i;
                    int boardX = piece.getX() + j;
                    if (boardY >= 0 && boardY < BOARD_HEIGHT && boardX >= 0 && boardX < BOARD_WIDTH) {
                        board[boardY][boardX] = 1; // 색상은 클라이언트에서 처리
                    }
                }
            }
        }
    }
    
    private int clearLines(TetrisDTO gameState) 
    {
        int[][] board = gameState.getBoard();
        int linesCleared = 0;
        
        for (int y = BOARD_HEIGHT - 1; y >= 0; y--) {
            boolean isLineFull = true;
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (board[y][x] == 0) {
                    isLineFull = false;
                    break;
                }
            }
            
            if (isLineFull) {
                // 라인 제거
                for (int moveY = y; moveY > 0; moveY--) {
                    System.arraycopy(board[moveY - 1], 0, board[moveY], 0, BOARD_WIDTH);
                }
                Arrays.fill(board[0], 0);
                linesCleared++;
                y++; // 같은 라인 다시 체크
            }
        }
        
        return linesCleared;
    }
    
    private void updateScore(TetrisDTO gameState, int linesCleared) 
    {
        gameState.setLines(gameState.getLines() + linesCleared);
        gameState.setScore(gameState.getScore() + linesCleared * 100 * gameState.getLevel());
        gameState.setLevel(gameState.getLines() / 10 + 1);
    }
    
    private void spawnNewPiece(TetrisDTO gameState) 
    {
        gameState.setCurrentPiece(gameState.getNextPiece());
        gameState.setNextPiece(generateRandomPiece());
        
        // 게임 오버 체크
        if (!isValidPosition(gameState.getBoard(), gameState.getCurrentPiece().getShape(), 
                           gameState.getCurrentPiece().getX(), gameState.getCurrentPiece().getY())) {
            gameState.setGameOver(true);
        }
    }
}
