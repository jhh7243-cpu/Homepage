package site.hohyun.api.minigame.tetris.service;

import org.springframework.stereotype.Service;
import site.hohyun.api.minigame.tetris.domain.TetrisGame;
import site.hohyun.api.minigame.tetris.domain.TetrisRepository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 테트리스 게임 서비스
 * 게임의 비즈니스 로직과 유스케이스를 처리
 */
@Service
public class TetrisService 
{
    private final TetrisRepository tetrisRepository;
    private final ScheduledExecutorService scheduler;
    private final ConcurrentHashMap<String, Boolean> activeGames;
    
    public TetrisService(TetrisRepository tetrisRepository) 
    {
        this.tetrisRepository = tetrisRepository;
        this.scheduler = Executors.newScheduledThreadPool(10);
        this.activeGames = new ConcurrentHashMap<>();
    }
    
    /**
     * 새 게임 시작
     * @param sessionId 세션 ID
     * @return 새로 생성된 게임
     */
    public TetrisGame startNewGame(String sessionId) 
    {
        System.out.println("테트리스 서비스: 새 게임 시작 - " + sessionId);
        
        // 기존 자동 낙하 중지
        stopAutoDrop(sessionId);
        
        TetrisGame game = new TetrisGame();
        game.startNewGame();
        
        tetrisRepository.save(sessionId, game);
        
        // 자동 낙하 시작
        startAutoDrop(sessionId);
        
        return game;
    }
    
    /**
     * 게임 상태 조회
     * @param sessionId 세션 ID
     * @return 게임 상태
     */
    public TetrisGame getGame(String sessionId) 
    {
        TetrisGame game = tetrisRepository.findById(sessionId);
        if (game == null) 
        {
            game = startNewGame(sessionId);
        }
        return game;
    }
    
    /**
     * 블록 이동 처리
     * @param sessionId 세션 ID
     * @param direction 이동 방향
     * @return 업데이트된 게임 상태
     */
    public TetrisGame moveBlock(String sessionId, String direction) 
    {
        TetrisGame game = getGame(sessionId);
        
        if (game == null || !game.isGameActive()) 
        {
            return game;
        }
        
        System.out.println("테트리스 서비스: 블록 이동 - " + direction);
        
        boolean moved = false;
        switch (direction) 
        {
            case "left":
                moved = game.moveBlock(-1, 0);
                break;
            case "right":
                moved = game.moveBlock(1, 0);
                break;
            case "down":
                moved = game.moveBlock(0, 1);
                if (moved) 
                {
                    game.setScore(game.getScore() + 1);
                }
                break;
        }
        
        tetrisRepository.save(sessionId, game);
        return game;
    }
    
    /**
     * 블록 회전 처리 (오른쪽 회전)
     * @param sessionId 세션 ID
     * @return 업데이트된 게임 상태
     */
    public TetrisGame rotateBlock(String sessionId) 
    {
        return rotateBlockRight(sessionId);
    }
    
    /**
     * 블록 오른쪽 회전 처리
     * @param sessionId 세션 ID
     * @return 업데이트된 게임 상태
     */
    public TetrisGame rotateBlockRight(String sessionId) 
    {
        TetrisGame game = getGame(sessionId);
        
        if (game == null || !game.isGameActive()) 
        {
            return game;
        }
        
        System.out.println("테트리스 서비스: 블록 오른쪽 회전");
        
        game.rotateBlock();
        tetrisRepository.save(sessionId, game);
        return game;
    }
    
    /**
     * 블록 왼쪽 회전 처리
     * @param sessionId 세션 ID
     * @return 업데이트된 게임 상태
     */
    public TetrisGame rotateBlockLeft(String sessionId) 
    {
        TetrisGame game = getGame(sessionId);
        
        if (game == null || !game.isGameActive()) 
        {
            return game;
        }
        
        System.out.println("테트리스 서비스: 블록 왼쪽 회전");
        
        // 왼쪽 회전은 오른쪽 회전을 3번 하는 것과 같음
        for (int i = 0; i < 3; i++) 
        {
            game.rotateBlock();
        }
        
        tetrisRepository.save(sessionId, game);
        return game;
    }
    
    /**
     * 블록 즉시 낙하 처리
     * @param sessionId 세션 ID
     * @return 업데이트된 게임 상태
     */
    public TetrisGame dropBlock(String sessionId) 
    {
        TetrisGame game = getGame(sessionId);
        
        if (game == null || !game.isGameActive()) 
        {
            return game;
        }
        
        System.out.println("테트리스 서비스: 블록 즉시 낙하");
        
        game.dropBlock();
        tetrisRepository.save(sessionId, game);
        return game;
    }
    
    /**
     * 게임 일시정지/재개 처리
     * @param sessionId 세션 ID
     * @return 업데이트된 게임 상태
     */
    public TetrisGame togglePause(String sessionId) 
    {
        TetrisGame game = getGame(sessionId);
        
        if (game == null || game.isGameOver()) 
        {
            return game;
        }
        
        System.out.println("테트리스 서비스: 게임 일시정지/재개");
        
        game.togglePause();
        tetrisRepository.save(sessionId, game);
        return game;
    }
    
    /**
     * 게임 재시작 처리
     * @param sessionId 세션 ID
     * @return 재시작된 게임 상태
     */
    public TetrisGame restartGame(String sessionId) 
    {
        System.out.println("테트리스 서비스: 게임 재시작");
        
        TetrisGame game = new TetrisGame();
        game.restart();
        
        tetrisRepository.save(sessionId, game);
        return game;
    }
    
    /**
     * 게임 삭제
     * @param sessionId 세션 ID
     */
    public void deleteGame(String sessionId) 
    {
        System.out.println("테트리스 서비스: 게임 삭제 - " + sessionId);
        stopAutoDrop(sessionId);
        tetrisRepository.delete(sessionId);
    }
    
    /**
     * 자동 낙하 시작
     * @param sessionId 세션 ID
     */
    private void startAutoDrop(String sessionId) 
    {
        activeGames.put(sessionId, true);
        
        scheduler.scheduleAtFixedRate(() -> {
            if (activeGames.getOrDefault(sessionId, false)) 
            {
                TetrisGame game = tetrisRepository.findById(sessionId);
                if (game != null && game.isGameActive()) 
                {
                    // 자동으로 아래로 이동
                    boolean moved = game.moveBlock(0, 1);
                    if (!moved) 
                    {
                        // 더 이상 이동할 수 없으면 블록을 고정하고 새 블록 생성
                        game.placeCurrentBlock();
                    }
                    tetrisRepository.save(sessionId, game);
                }
                else 
                {
                    // 게임이 끝났거나 일시정지된 경우 자동 낙하 중지
                    stopAutoDrop(sessionId);
                }
            }
        }, 1000, getDropInterval(sessionId), TimeUnit.MILLISECONDS);
    }
    
    /**
     * 레벨에 따른 낙하 간격 계산
     * @param sessionId 세션 ID
     * @return 낙하 간격 (밀리초)
     */
    private long getDropInterval(String sessionId) 
    {
        TetrisGame game = tetrisRepository.findById(sessionId);
        if (game != null) 
        {
            // 레벨이 높을수록 빠르게 낙하 (최소 200ms, 최대 1000ms)
            return Math.max(200, 1000 - (game.getLevel() - 1) * 100);
        }
        return 1000; // 기본값
    }
    
    /**
     * 자동 낙하 중지
     * @param sessionId 세션 ID
     */
    private void stopAutoDrop(String sessionId) 
    {
        activeGames.put(sessionId, false);
    }
    
    /**
     * 자동 낙하 일시정지/재개
     * @param sessionId 세션 ID
     */
    public void toggleAutoDrop(String sessionId) 
    {
        TetrisGame game = getGame(sessionId);
        if (game != null) 
        {
            if (game.isPaused()) 
            {
                stopAutoDrop(sessionId);
            }
            else 
            {
                startAutoDrop(sessionId);
            }
        }
    }
    
    /**
     * 게임 통계 조회
     * @param sessionId 세션 ID
     * @return 게임 통계 정보
     */
    public GameStats getGameStats(String sessionId) 
    {
        TetrisGame game = getGame(sessionId);
        
        return new GameStats(
            game.getScore(),
            game.getLevel(),
            game.getLines(),
            game.isGameOver(),
            game.isGameStarted(),
            game.isPaused()
        );
    }
    
    /**
     * 게임 통계 DTO
     */
    public static class GameStats 
    {
        private final int score;
        private final int level;
        private final int lines;
        private final boolean gameOver;
        private final boolean gameStarted;
        private final boolean paused;
        
        public GameStats(int score, int level, int lines, boolean gameOver, boolean gameStarted, boolean paused) 
        {
            this.score = score;
            this.level = level;
            this.lines = lines;
            this.gameOver = gameOver;
            this.gameStarted = gameStarted;
            this.paused = paused;
        }
        
        // Getter methods
        public int getScore() { return score; }
        public int getLevel() { return level; }
        public int getLines() { return lines; }
        public boolean isGameOver() { return gameOver; }
        public boolean isGameStarted() { return gameStarted; }
        public boolean isPaused() { return paused; }
    }
}
