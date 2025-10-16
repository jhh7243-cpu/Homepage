package site.hohyun.api.minigame.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * 미니게임 값 객체
 * 미니게임 데이터를 저장하고 관리하는 VO
 */
public class MinigameVO 
{
    private List<MinigameDTO> minigames;

    public MinigameVO() 
    {
        this.minigames = new ArrayList<>();
        initializeMinigames();
    }

    /**
     * 미니게임 목록 초기화
     */
    private void initializeMinigames() 
    {
        // 운전 게임
        MinigameDTO racingGame = new MinigameDTO();
        racingGame.setGameId("racing-001");
        racingGame.setGameName("운전 게임");
        racingGame.setGameDescription("스릴 넘치는 레이싱 게임을 즐겨보세요! 빠른 속도로 달리며 다른 차량들을 추월하고 최고 기록을 세워보세요.");
        racingGame.setGameGenre("레이싱");
        racingGame.setGameIcon("🏎️");
        racingGame.setDifficulty(4);
        racingGame.setGameUrl("/dashboard.html");
        
        minigames.add(racingGame);

        // 테트리스 게임
        MinigameDTO tetrisGame = new MinigameDTO();
        tetrisGame.setGameId("tetris-001");
        tetrisGame.setGameName("테트리스");
        tetrisGame.setGameDescription("전 세계적으로 사랑받는 퍼즐 게임 테트리스! 떨어지는 블록을 맞춰서 라인을 완성하는 게임입니다.");
        tetrisGame.setGameGenre("퍼즐");
        tetrisGame.setGameIcon("🧩");
        tetrisGame.setDifficulty(4);
        tetrisGame.setGameUrl("/minigame/tetris");
        
        minigames.add(tetrisGame);
    }

    /**
     * 모든 미니게임 목록 조회
     * @return 미니게임 목록
     */
    public List<MinigameDTO> getAllMinigames() 
    {
        return new ArrayList<>(minigames);
    }

    /**
     * 게임 ID로 미니게임 조회
     * @param gameId 게임 ID
     * @return 미니게임 정보
     */
    public MinigameDTO getMinigameById(String gameId) 
    {
        return minigames.stream()
                .filter(game -> game.getGameId().equals(gameId))
                .findFirst()
                .orElse(null);
    }

    /**
     * 장르별 미니게임 조회
     * @param genre 장르
     * @return 해당 장르의 미니게임 목록
     */
    public List<MinigameDTO> getMinigamesByGenre(String genre) 
    {
        return minigames.stream()
                .filter(game -> game.getGameGenre().equals(genre))
                .toList();
    }

    /**
     * 미니게임 총 개수 조회
     * @return 미니게임 개수
     */
    public int getMinigameCount() 
    {
        return minigames.size();
    }
}
