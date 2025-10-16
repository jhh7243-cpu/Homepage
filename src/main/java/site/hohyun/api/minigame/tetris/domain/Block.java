package site.hohyun.api.minigame.tetris.domain;

import java.util.Arrays;

/**
 * 테트리스 블록 도메인 엔티티
 * 블록의 모양, 위치, 회전 상태를 관리
 */
public class Block 
{
    private int[][] shape;
    private int x;
    private int y;
    private String color;
    private BlockType type;
    
    // 테트리스 블록 모양들
    public static final int[][][] BLOCK_SHAPES = {
        // I 블록
        {{1, 1, 1, 1}},
        // O 블록
        {{1, 1}, {1, 1}},
        // T 블록
        {{0, 1, 0}, {1, 1, 1}},
        // S 블록
        {{0, 1, 1}, {1, 1, 0}},
        // Z 블록
        {{1, 1, 0}, {0, 1, 1}},
        // L 블록
        {{1, 0, 0}, {1, 1, 1}},
        // J 블록
        {{0, 0, 1}, {1, 1, 1}}
    };
    
    public static final String[] BLOCK_COLORS = {
        "#00f5ff", // I - 청록
        "#ffff00", // O - 노랑
        "#800080", // T - 보라
        "#00ff00", // S - 초록
        "#ff0000", // Z - 빨강
        "#ff8c00", // L - 주황
        "#0000ff"  // J - 파랑
    };
    
    public enum BlockType 
    {
        I, O, T, S, Z, L, J
    }
    
    public Block(BlockType type) 
    {
        this.type = type;
        this.shape = copyShape(BLOCK_SHAPES[type.ordinal()]);
        this.color = BLOCK_COLORS[type.ordinal()];
        this.x = 0;
        this.y = 0;
    }
    
    public Block(int[][] shape, String color) 
    {
        this.shape = copyShape(shape);
        this.color = color;
        this.x = 0;
        this.y = 0;
        this.type = determineType(shape);
    }
    
    /**
     * 블록 모양 복사
     */
    private int[][] copyShape(int[][] original) 
    {
        int[][] copy = new int[original.length][];
        for (int i = 0; i < original.length; i++) 
        {
            copy[i] = Arrays.copyOf(original[i], original[i].length);
        }
        return copy;
    }
    
    /**
     * 블록 타입 결정
     */
    private BlockType determineType(int[][] shape) 
    {
        for (int i = 0; i < BLOCK_SHAPES.length; i++) 
        {
            if (Arrays.deepEquals(shape, BLOCK_SHAPES[i])) 
            {
                return BlockType.values()[i];
            }
        }
        return BlockType.I; // 기본값
    }
    
    /**
     * 블록 회전
     */
    public void rotate() 
    {
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] rotated = new int[cols][rows];
        
        for (int i = 0; i < rows; i++) 
        {
            for (int j = 0; j < cols; j++) 
            {
                rotated[j][rows - 1 - i] = shape[i][j];
            }
        }
        
        this.shape = rotated;
    }
    
    /**
     * 블록 이동
     */
    public void move(int dx, int dy) 
    {
        this.x += dx;
        this.y += dy;
    }
    
    /**
     * 블록 위치 설정
     */
    public void setPosition(int x, int y) 
    {
        this.x = x;
        this.y = y;
    }
    
    /**
     * 블록이 특정 위치에 있는지 확인
     */
    public boolean isAt(int x, int y) 
    {
        for (int i = 0; i < shape.length; i++) 
        {
            for (int j = 0; j < shape[i].length; j++) 
            {
                if (shape[i][j] == 1 && this.x + j == x && this.y + i == y) 
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 블록의 모든 셀 위치 반환
     */
    public int[][] getCellPositions() 
    {
        int cellCount = 0;
        for (int[] row : shape) 
        {
            for (int cell : row) 
            {
                if (cell == 1) cellCount++;
            }
        }
        
        int[][] positions = new int[cellCount][2];
        int index = 0;
        
        for (int i = 0; i < shape.length; i++) 
        {
            for (int j = 0; j < shape[i].length; j++) 
            {
                if (shape[i][j] == 1) 
                {
                    positions[index][0] = x + j; // x 좌표
                    positions[index][1] = y + i; // y 좌표
                    index++;
                }
            }
        }
        
        return positions;
    }
    
    /**
     * 블록의 너비 반환
     */
    public int getWidth() 
    {
        return shape[0].length;
    }
    
    /**
     * 블록의 높이 반환
     */
    public int getHeight() 
    {
        return shape.length;
    }
    
    // Getter and Setter methods
    public int[][] getShape() 
    {
        return copyShape(shape);
    }
    
    public void setShape(int[][] shape) 
    {
        this.shape = copyShape(shape);
    }
    
    public int getX() 
    {
        return x;
    }
    
    public void setX(int x) 
    {
        this.x = x;
    }
    
    public int getY() 
    {
        return y;
    }
    
    public void setY(int y) 
    {
        this.y = y;
    }
    
    public String getColor() 
    {
        return color;
    }
    
    public void setColor(String color) 
    {
        this.color = color;
    }
    
    public BlockType getType() 
    {
        return type;
    }
    
    public void setType(BlockType type) 
    {
        this.type = type;
    }
    
    @Override
    public String toString() 
    {
        return "Block{" +
                "type=" + type +
                ", x=" + x +
                ", y=" + y +
                ", color='" + color + '\'' +
                '}';
    }
}
