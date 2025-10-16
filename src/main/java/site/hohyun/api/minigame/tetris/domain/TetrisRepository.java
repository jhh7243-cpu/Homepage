package site.hohyun.api.minigame.tetris.domain;

import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

/**
 * 테트리스 게임 저장소
 * 게임 상태를 메모리에 저장하고 관리
 */
@Repository
public class TetrisRepository 
{
    private final Map<String, TetrisGame> gameStorage;
    
    public TetrisRepository() 
    {
        this.gameStorage = new ConcurrentHashMap<>();
    }
    
    /**
     * 게임 저장
     * @param sessionId 세션 ID
     * @param game 게임 상태
     */
    public void save(String sessionId, TetrisGame game) 
    {
        if (sessionId == null || game == null) 
        {
            throw new IllegalArgumentException("세션 ID와 게임 상태는 null일 수 없습니다.");
        }
        
        System.out.println("테트리스 저장소: 게임 저장 - " + sessionId);
        gameStorage.put(sessionId, game);
    }
    
    /**
     * 게임 조회
     * @param sessionId 세션 ID
     * @return 게임 상태 (없으면 null)
     */
    public TetrisGame findById(String sessionId) 
    {
        if (sessionId == null) 
        {
            return null;
        }
        
        TetrisGame game = gameStorage.get(sessionId);
        if (game != null) 
        {
            System.out.println("테트리스 저장소: 게임 조회 성공 - " + sessionId);
        }
        else 
        {
            System.out.println("테트리스 저장소: 게임 조회 실패 - " + sessionId);
        }
        
        return game;
    }
    
    /**
     * 게임 존재 여부 확인
     * @param sessionId 세션 ID
     * @return 게임 존재 여부
     */
    public boolean exists(String sessionId) 
    {
        return sessionId != null && gameStorage.containsKey(sessionId);
    }
    
    /**
     * 게임 삭제
     * @param sessionId 세션 ID
     * @return 삭제된 게임 (없으면 null)
     */
    public TetrisGame delete(String sessionId) 
    {
        if (sessionId == null) 
        {
            return null;
        }
        
        TetrisGame removedGame = gameStorage.remove(sessionId);
        if (removedGame != null) 
        {
            System.out.println("테트리스 저장소: 게임 삭제 성공 - " + sessionId);
        }
        else 
        {
            System.out.println("테트리스 저장소: 게임 삭제 실패 (게임 없음) - " + sessionId);
        }
        
        return removedGame;
    }
    
    /**
     * 모든 게임 조회
     * @return 모든 게임의 Map
     */
    public Map<String, TetrisGame> findAll() 
    {
        return new ConcurrentHashMap<>(gameStorage);
    }
    
    /**
     * 저장된 게임 수 조회
     * @return 게임 수
     */
    public int count() 
    {
        return gameStorage.size();
    }
    
    /**
     * 모든 게임 삭제
     */
    public void deleteAll() 
    {
        System.out.println("테트리스 저장소: 모든 게임 삭제 - " + gameStorage.size() + "개");
        gameStorage.clear();
    }
    
    /**
     * 게임 업데이트 (기존 게임이 있는 경우에만)
     * @param sessionId 세션 ID
     * @param game 게임 상태
     * @return 업데이트 성공 여부
     */
    public boolean update(String sessionId, TetrisGame game) 
    {
        if (sessionId == null || game == null) 
        {
            return false;
        }
        
        if (gameStorage.containsKey(sessionId)) 
        {
            gameStorage.put(sessionId, game);
            System.out.println("테트리스 저장소: 게임 업데이트 성공 - " + sessionId);
            return true;
        }
        
        System.out.println("테트리스 저장소: 게임 업데이트 실패 (게임 없음) - " + sessionId);
        return false;
    }
    
    /**
     * 저장소 상태 정보
     * @return 저장소 상태 문자열
     */
    public String getStorageInfo() 
    {
        return String.format("TetrisRepository: %d개 게임 저장됨", gameStorage.size());
    }
}
