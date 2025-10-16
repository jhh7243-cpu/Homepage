package site.hohyun.api.minigame.tetris.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 테트리스 게임 보드 도메인 엔티티
 * 게임 보드의 상태와 블록 배치를 관리
 */
public class Board 
{
    public static final int WIDTH = 10;
    public static final int HEIGHT = 20;
    
    private int[][] grid;
    private List<Block> placedBlocks;
    
    public Board() 
    {
        this.grid = new int[HEIGHT][WIDTH];
        this.placedBlocks = new ArrayList<>();
        clearBoard();
    }
    
    /**
     * 보드 초기화
     */
    public void clearBoard() 
    {
        for (int y = 0; y < HEIGHT; y++) 
        {
            for (int x = 0; x < WIDTH; x++) 
            {
                grid[y][x] = 0;
            }
        }
        placedBlocks.clear();
    }
    
    /**
     * 특정 위치가 비어있는지 확인
     */
    public boolean isEmpty(int x, int y) 
    {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) 
        {
            return false;
        }
        return grid[y][x] == 0;
    }
    
    /**
     * 특정 위치에 블록이 있는지 확인
     */
    public boolean isOccupied(int x, int y) 
    {
        if (x < 0 || x >= WIDTH || y < 0 || y >= HEIGHT) 
        {
            return true; // 경계 밖은 점유된 것으로 간주
        }
        return grid[y][x] == 1;
    }
    
    /**
     * 블록이 보드에 배치 가능한지 확인
     */
    public boolean canPlaceBlock(Block block) 
    {
        int[][] positions = block.getCellPositions();
        
        for (int[] position : positions) 
        {
            int x = position[0];
            int y = position[1];
            
            if (!isEmpty(x, y)) 
            {
                return false;
            }
        }
        return true;
    }
    
    /**
     * 블록을 보드에 배치
     */
    public boolean placeBlock(Block block) 
    {
        if (!canPlaceBlock(block)) 
        {
            return false;
        }
        
        int[][] positions = block.getCellPositions();
        
        for (int[] position : positions) 
        {
            int x = position[0];
            int y = position[1];
            
            if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) 
            {
                grid[y][x] = 1;
            }
        }
        
        placedBlocks.add(block);
        return true;
    }
    
    /**
     * 완성된 라인들을 찾아서 제거
     * @return 제거된 라인 수
     */
    public int clearCompletedLines() 
    {
        List<Integer> completedLines = new ArrayList<>();
        
        // 완성된 라인 찾기
        for (int y = 0; y < HEIGHT; y++) 
        {
            boolean isComplete = true;
            for (int x = 0; x < WIDTH; x++) 
            {
                if (grid[y][x] == 0) 
                {
                    isComplete = false;
                    break;
                }
            }
            if (isComplete) 
            {
                completedLines.add(y);
            }
        }
        
        // 완성된 라인 제거
        for (int lineIndex : completedLines) 
        {
            // 위쪽 라인들을 아래로 이동
            for (int y = lineIndex; y > 0; y--) 
            {
                System.arraycopy(grid[y - 1], 0, grid[y], 0, WIDTH);
            }
            // 맨 위 라인을 비움
            for (int x = 0; x < WIDTH; x++) 
            {
                grid[0][x] = 0;
            }
        }
        
        return completedLines.size();
    }
    
    /**
     * 게임 오버 조건 확인 (맨 위 라인에 블록이 있는지)
     */
    public boolean isGameOver() 
    {
        for (int x = 0; x < WIDTH; x++) 
        {
            if (grid[0][x] == 1) 
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * 보드의 특정 위치에 값 설정
     */
    public void setCell(int x, int y, int value) 
    {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) 
        {
            grid[y][x] = value;
        }
    }
    
    /**
     * 보드의 특정 위치 값 가져오기
     */
    public int getCell(int x, int y) 
    {
        if (x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT) 
        {
            return grid[y][x];
        }
        return -1; // 경계 밖
    }
    
    /**
     * 보드를 HTML 테이블로 변환
     */
    public String toHtml() 
    {
        StringBuilder html = new StringBuilder();
        html.append("<table class='tetris-board'>");
        
        for (int y = 0; y < HEIGHT; y++) 
        {
            html.append("<tr>");
            for (int x = 0; x < WIDTH; x++) 
            {
                String cellClass = grid[y][x] == 1 ? "fixed-piece" : "empty";
                String cellColor = grid[y][x] == 1 ? "background-color: #666" : "";
                
                html.append("<td class='").append(cellClass).append("' style='").append(cellColor).append("'></td>");
            }
            html.append("</tr>");
        }
        
        html.append("</table>");
        return html.toString();
    }
    
    /**
     * 현재 블록과 함께 보드를 HTML로 변환
     */
    public String toHtmlWithCurrentBlock(Block currentBlock) 
    {
        StringBuilder html = new StringBuilder();
        html.append("<table class='tetris-board'>");
        
        for (int y = 0; y < HEIGHT; y++) 
        {
            html.append("<tr>");
            for (int x = 0; x < WIDTH; x++) 
            {
                String cellClass = "empty";
                String cellColor = "";
                
                // 현재 블록이 있는 위치
                if (currentBlock != null && currentBlock.isAt(x, y)) 
                {
                    cellClass = "current-piece";
                    cellColor = "background-color: " + currentBlock.getColor();
                }
                // 고정된 블록이 있는 위치
                else if (grid[y][x] == 1) 
                {
                    cellClass = "fixed-piece";
                    cellColor = "background-color: #666";
                }
                
                html.append("<td class='").append(cellClass).append("' style='").append(cellColor).append("'></td>");
            }
            html.append("</tr>");
        }
        
        html.append("</table>");
        return html.toString();
    }
    
    /**
     * 보드 상태를 2차원 배열로 반환
     */
    public int[][] getGrid() 
    {
        int[][] copy = new int[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) 
        {
            System.arraycopy(grid[y], 0, copy[y], 0, WIDTH);
        }
        return copy;
    }
    
    /**
     * 보드 상태 설정
     */
    public void setGrid(int[][] grid) 
    {
        if (grid.length == HEIGHT && grid[0].length == WIDTH) 
        {
            for (int y = 0; y < HEIGHT; y++) 
            {
                System.arraycopy(grid[y], 0, this.grid[y], 0, WIDTH);
            }
        }
    }
    
    // Getter and Setter methods
    public List<Block> getPlacedBlocks() 
    {
        return new ArrayList<>(placedBlocks);
    }
    
    public void setPlacedBlocks(List<Block> placedBlocks) 
    {
        this.placedBlocks = new ArrayList<>(placedBlocks);
    }
    
    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Board:\n");
        for (int y = 0; y < HEIGHT; y++) 
        {
            for (int x = 0; x < WIDTH; x++) 
            {
                sb.append(grid[y][x] == 1 ? "■" : "□");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
