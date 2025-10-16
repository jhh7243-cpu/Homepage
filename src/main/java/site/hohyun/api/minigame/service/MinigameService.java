package site.hohyun.api.minigame.service;

import org.springframework.stereotype.Service;
import site.hohyun.api.minigame.domain.MinigameDTO;
import site.hohyun.api.minigame.domain.MinigameVO;

import java.util.List;

/**
 * 미니게임 서비스
 * 미니게임 비즈니스 로직 처리
 */
@Service
public class MinigameService 
{
    private final MinigameVO minigameVO;

    public MinigameService() 
    {
        this.minigameVO = new MinigameVO();
    }

    /**
     * 모든 미니게임 목록 조회
     * @return 미니게임 목록
     */
    public List<MinigameDTO> getAllMinigames() 
    {
        System.out.println("미니게임 서비스: 모든 미니게임 목록 조회 요청");
        
        List<MinigameDTO> minigames = minigameVO.getAllMinigames();
        
        System.out.println("조회된 미니게임 개수: " + minigames.size());
        for (MinigameDTO game : minigames) 
        {
            System.out.println("- " + game.getGameName() + " (" + game.getGameGenre() + ")");
        }
        
        return minigames;
    }

    /**
     * 게임 ID로 미니게임 조회
     * @param gameId 게임 ID
     * @return 미니게임 정보
     */
    public MinigameDTO getMinigameById(String gameId) 
    {
        System.out.println("미니게임 서비스: 게임 ID로 조회 요청 - " + gameId);
        
        MinigameDTO minigame = minigameVO.getMinigameById(gameId);
        
        if (minigame != null) 
        {
            System.out.println("조회된 게임: " + minigame.getGameName());
        } 
        else 
        {
            System.out.println("해당 ID의 게임을 찾을 수 없습니다: " + gameId);
        }
        
        return minigame;
    }

    /**
     * 장르별 미니게임 조회
     * @param genre 장르
     * @return 해당 장르의 미니게임 목록
     */
    public List<MinigameDTO> getMinigamesByGenre(String genre) 
    {
        System.out.println("미니게임 서비스: 장르별 조회 요청 - " + genre);
        
        List<MinigameDTO> minigames = minigameVO.getMinigamesByGenre(genre);
        
        System.out.println(genre + " 장르 게임 개수: " + minigames.size());
        for (MinigameDTO game : minigames) 
        {
            System.out.println("- " + game.getGameName());
        }
        
        return minigames;
    }

    /**
     * 미니게임 통계 정보 조회
     * @return 미니게임 통계 정보
     */
    public String getMinigameStats() 
    {
        System.out.println("미니게임 서비스: 통계 정보 조회 요청");
        
        int totalGames = minigameVO.getMinigameCount();
        List<MinigameDTO> racingGames = minigameVO.getMinigamesByGenre("레이싱");
        
        String stats = "총 " + totalGames + "개의 미니게임이 있습니다. (레이싱: " + racingGames.size() + "개)";
        
        System.out.println("통계 정보: " + stats);
        
        return stats;
    }
}
