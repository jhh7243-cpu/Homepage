package site.hohyun.api.minigame.tetris.domain;

import java.util.Random;

/**
 * 테트리스 게임 도메인 엔티티
 * 게임의 전체 상태와 규칙을 관리
 */
public class TetrisGame 
{
    private Board board;
    private Block currentBlock;
    private Block nextBlock;
    private int score;
    private int level;
    private int lines;
    private boolean gameOver;
    private boolean gameStarted;
    private boolean paused;
    private Random random;
    
    public TetrisGame() 
    {
        this.board = new Board();
        this.score = 0;
        this.level = 1;
        this.lines = 0;
        this.gameOver = false;
        this.gameStarted = false;
        this.paused = false;
        this.random = new Random();
        generateNewBlock();
    }
    
    /**
     * 새 게임 시작
     */
    public void startNewGame() 
    {
        board.clearBoard();
        score = 0;
        level = 1;
        lines = 0;
        gameOver = false;
        gameStarted = true;
        paused = false;
        generateNewBlock();
    }
    
    /**
     * 새 블록 생성
     */
    public void generateNewBlock() 
    {
        currentBlock = nextBlock;
        if (currentBlock == null) 
        {
            currentBlock = createRandomBlock();
        }
        
        nextBlock = createRandomBlock();
        
        // 블록을 보드 중앙 상단에 배치
        int centerX = Board.WIDTH / 2 - currentBlock.getWidth() / 2;
        currentBlock.setPosition(centerX, 0);
        
        // 게임 오버 체크
        if (!board.canPlaceBlock(currentBlock)) 
        {
            gameOver = true;
            gameStarted = false;
        }
    }
    
    /**
     * 랜덤 블록 생성
     */
    private Block createRandomBlock() 
    {
        Block.BlockType[] types = Block.BlockType.values();
        Block.BlockType randomType = types[random.nextInt(types.length)];
        return new Block(randomType);
    }
    
    /**
     * 블록 이동
     */
    public boolean moveBlock(int dx, int dy) 
    {
        if (!gameStarted || gameOver || paused) 
        {
            return false;
        }
        
        Block testBlock = new Block(currentBlock.getShape(), currentBlock.getColor());
        testBlock.setPosition(currentBlock.getX() + dx, currentBlock.getY() + dy);
        
        if (board.canPlaceBlock(testBlock)) 
        {
            currentBlock.move(dx, dy);
            return true;
        }
        
        // 아래로 이동할 수 없으면 블록을 고정
        if (dy > 0) 
        {
            placeCurrentBlock();
        }
        
        return false;
    }
    
    /**
     * 블록 회전
     */
    public boolean rotateBlock() 
    {
        if (!gameStarted || gameOver || paused) 
        {
            return false;
        }
        
        Block testBlock = new Block(currentBlock.getShape(), currentBlock.getColor());
        testBlock.setPosition(currentBlock.getX(), currentBlock.getY());
        testBlock.rotate();
        
        if (board.canPlaceBlock(testBlock)) 
        {
            currentBlock.rotate();
            return true;
        }
        
        return false;
    }
    
    /**
     * 블록 즉시 낙하
     */
    public int dropBlock() 
    {
        if (!gameStarted || gameOver || paused) 
        {
            return 0;
        }
        
        int dropDistance = 0;
        while (moveBlock(0, 1)) 
        {
            dropDistance++;
        }
        
        // 낙하 거리에 따른 보너스 점수
        int bonusScore = dropDistance * 2;
        addScore(bonusScore);
        
        return dropDistance;
    }
    
    /**
     * 현재 블록을 보드에 고정
     */
    public void placeCurrentBlock() 
    {
        if (board.placeBlock(currentBlock)) 
        {
            // 완성된 라인 제거
            int clearedLines = board.clearCompletedLines();
            if (clearedLines > 0) 
            {
                lines += clearedLines;
                int lineScore = clearedLines * 100 * level;
                addScore(lineScore);
                
                // 레벨 업
                level = lines / 10 + 1;
            }
            
            // 다음 블록 생성
            generateNewBlock();
        }
    }
    
    /**
     * 점수 추가
     */
    private void addScore(int points) 
    {
        score += points;
    }
    
    /**
     * 게임 일시정지/재개
     */
    public void togglePause() 
    {
        if (gameStarted && !gameOver) 
        {
            paused = !paused;
        }
    }
    
    /**
     * 게임 재시작
     */
    public void restart() 
    {
        startNewGame();
    }
    
    /**
     * 게임 상태 확인
     */
    public boolean isGameActive() 
    {
        return gameStarted && !gameOver && !paused;
    }
    
    /**
     * 보드를 HTML로 변환 (현재 블록 포함)
     */
    public String getBoardAsHtml() 
    {
        return board.toHtmlWithCurrentBlock(currentBlock);
    }
    
    /**
     * 다음 블록을 HTML로 변환
     */
    public String getNextBlockAsHtml() 
    {
        if (nextBlock == null) 
        {
            return "";
        }
        
        StringBuilder html = new StringBuilder();
        html.append("<table class='next-piece'>");
        
        int[][] shape = nextBlock.getShape();
        for (int i = 0; i < shape.length; i++) 
        {
            html.append("<tr>");
            for (int j = 0; j < shape[i].length; j++) 
            {
                String cellClass = shape[i][j] == 1 ? "piece-cell" : "empty-cell";
                String cellColor = shape[i][j] == 1 ? "background-color: " + nextBlock.getColor() : "";
                html.append("<td class='").append(cellClass).append("' style='").append(cellColor).append("'></td>");
            }
            html.append("</tr>");
        }
        
        html.append("</table>");
        return html.toString();
    }
    
    // Getter and Setter methods
    public Board getBoard() 
    {
        return board;
    }
    
    public void setBoard(Board board) 
    {
        this.board = board;
    }
    
    public Block getCurrentBlock() 
    {
        return currentBlock;
    }
    
    public void setCurrentBlock(Block currentBlock) 
    {
        this.currentBlock = currentBlock;
    }
    
    public Block getNextBlock() 
    {
        return nextBlock;
    }
    
    public void setNextBlock(Block nextBlock) 
    {
        this.nextBlock = nextBlock;
    }
    
    public int getScore() 
    {
        return score;
    }
    
    public void setScore(int score) 
    {
        this.score = score;
    }
    
    public int getLevel() 
    {
        return level;
    }
    
    public void setLevel(int level) 
    {
        this.level = level;
    }
    
    public int getLines() 
    {
        return lines;
    }
    
    public void setLines(int lines) 
    {
        this.lines = lines;
    }
    
    public boolean isGameOver() 
    {
        return gameOver;
    }
    
    public void setGameOver(boolean gameOver) 
    {
        this.gameOver = gameOver;
    }
    
    public boolean isGameStarted() 
    {
        return gameStarted;
    }
    
    public void setGameStarted(boolean gameStarted) 
    {
        this.gameStarted = gameStarted;
    }
    
    public boolean isPaused() 
    {
        return paused;
    }
    
    public void setPaused(boolean paused) 
    {
        this.paused = paused;
    }
    
    @Override
    public String toString() 
    {
        return "TetrisGame{" +
                "score=" + score +
                ", level=" + level +
                ", lines=" + lines +
                ", gameOver=" + gameOver +
                ", gameStarted=" + gameStarted +
                ", paused=" + paused +
                '}';
    }
}
