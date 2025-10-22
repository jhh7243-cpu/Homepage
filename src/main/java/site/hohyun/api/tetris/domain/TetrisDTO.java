package site.hohyun.api.tetris.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

/**
 * 테트리스 게임 데이터 전송 객체
 * 클라이언트와 서버 간 게임 상태를 전송하기 위한 객체
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TetrisDTO 
{
    private String gameId;
    private String playerId;
    private int[][] board;
    private TetrisPiece currentPiece;
    private TetrisPiece nextPiece;
    private int score;
    private int level;
    private int lines;
    private boolean gameOver;
    private boolean gamePaused;
    private long timestamp;
    
    /**
     * 테트리스 블록 정보
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TetrisPiece 
    {
        private int[][] shape;
        private String color;
        private int x;
        private int y;
        private int rotation;
    }
    
    /**
     * 게임 액션 정보
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameAction 
    {
        private String action; // MOVE, ROTATE, DROP, PAUSE, RESUME
        private int dx;
        private int dy;
        private int rotation;
        private String playerId;
        private long timestamp;
    }
    
    /**
     * 게임 룸 정보
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameRoom 
    {
        private String roomId;
        private List<String> players;
        private int maxPlayers;
        private boolean isActive;
        private long createdAt;
    }
    
    /**
     * 점수 계산 결과
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ScoreResult 
    {
        private int baseScore;
        private int levelBonus;
        private int timeBonus;
        private int totalScore;
        private int rank;
        private String message;
    }
    
    /**
     * 게임 결과 정보
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GameResult 
    {
        private String gameId;
        private String playerId;
        private String playerName;
        private int finalScore;
        private int lines;
        private int level;
        private long gameTime; // 게임 시간 (밀리초)
        private long timestamp;
        private int rank;
        private boolean isPersonalBest;
    }
}
