package site.hohyun.api.minigame.domain;

/**
 * 미니게임 데이터 전송 객체
 * 미니게임 정보를 전달하기 위한 DTO
 */
public class MinigameDTO 
{
    private String gameId;
    private String gameName;
    private String gameDescription;
    private String gameGenre;
    private String gameIcon;
    private int difficulty;
    private String gameUrl;

    // Getter
    public String getGameId() 
    {
        return gameId;
    }

    public String getGameName() 
    {
        return gameName;
    }

    public String getGameDescription() 
    {
        return gameDescription;
    }

    public String getGameGenre() 
    {
        return gameGenre;
    }

    public String getGameIcon() 
    {
        return gameIcon;
    }

    public int getDifficulty() 
    {
        return difficulty;
    }

    public String getGameUrl() 
    {
        return gameUrl;
    }

    // Setter
    public void setGameId(String gameId) 
    {
        this.gameId = gameId;
    }

    public void setGameName(String gameName) 
    {
        this.gameName = gameName;
    }

    public void setGameDescription(String gameDescription) 
    {
        this.gameDescription = gameDescription;
    }

    public void setGameGenre(String gameGenre) 
    {
        this.gameGenre = gameGenre;
    }

    public void setGameIcon(String gameIcon) 
    {
        this.gameIcon = gameIcon;
    }

    public void setDifficulty(int difficulty) 
    {
        this.difficulty = difficulty;
    }

    public void setGameUrl(String gameUrl) 
    {
        this.gameUrl = gameUrl;
    }

    @Override
    public String toString() 
    {
        return "MinigameDTO{" +
                "gameId='" + gameId + '\'' +
                ", gameName='" + gameName + '\'' +
                ", gameDescription='" + gameDescription + '\'' +
                ", gameGenre='" + gameGenre + '\'' +
                ", gameIcon='" + gameIcon + '\'' +
                ", difficulty=" + difficulty +
                ", gameUrl='" + gameUrl + '\'' +
                '}';
    }
}
