package site.hohyun.api.tetris.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.hohyun.api.tetris.domain.TetrisDTO;
import site.hohyun.api.tetris.service.TetrisService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

/**
 * 테트리스 게임 컨트롤러
 * REST API와 WebSocket을 통한 실시간 멀티플레이어 테트리스
 */
@Controller
@RequestMapping("/tetris")
@RequiredArgsConstructor
public class TetrisController 
{
    private final TetrisService tetrisService;
    
    /**
     * 테트리스 게임 페이지 표시
     */
    @GetMapping
    public String tetrisPage(Model model) 
    {
        System.out.println("테트리스 게임 페이지 요청");
        return "tetris/tetris";
    }
    
    /**
     * 새 게임 시작 (REST API)
     */
    @PostMapping("/start")
    @ResponseBody
    public TetrisDTO startGame(@RequestParam String playerId) 
    {
        String gameId = UUID.randomUUID().toString();
        return tetrisService.startNewGame(playerId, gameId);
    }
    
    /**
     * 게임 액션 처리 (REST API)
     */
    @PostMapping("/action")
    @ResponseBody
    public TetrisDTO processAction(@RequestParam String gameId, 
                                  @RequestParam String action,
                                  @RequestParam(defaultValue = "0") int dx,
                                  @RequestParam(defaultValue = "0") int dy) 
    {
        TetrisDTO.GameAction gameAction = new TetrisDTO.GameAction();
        gameAction.setAction(action);
        gameAction.setDx(dx);
        gameAction.setDy(dy);
        gameAction.setTimestamp(System.currentTimeMillis());
        
        return tetrisService.processGameAction(gameId, gameAction);
    }
    
    /**
     * 게임 상태 조회 (REST API)
     */
    @GetMapping("/state/{gameId}")
    @ResponseBody
    public TetrisDTO getGameState(@PathVariable String gameId) 
    {
        return tetrisService.updateGameState(gameId);
    }
    
    /**
     * 게임 룸 생성 (REST API)
     */
    @PostMapping("/room/create")
    @ResponseBody
    public TetrisDTO.GameRoom createRoom(@RequestParam(defaultValue = "4") int maxPlayers) 
    {
        String roomId = UUID.randomUUID().toString();
        return tetrisService.createGameRoom(roomId, maxPlayers);
    }
    
    /**
     * 게임 룸 참가 (REST API)
     */
    @PostMapping("/room/join")
    @ResponseBody
    public boolean joinRoom(@RequestParam String roomId, @RequestParam String playerId) 
    {
        return tetrisService.joinGameRoom(roomId, playerId);
    }
    
    /**
     * 룸의 모든 게임 상태 조회 (REST API)
     */
    @GetMapping("/room/{roomId}/states")
    @ResponseBody
    public List<TetrisDTO> getRoomStates(@PathVariable String roomId) 
    {
        return tetrisService.getRoomGameStates(roomId);
    }
    
    /**
     * 게임 점수 계산 및 저장 (REST API)
     */
    @PostMapping("/score/calculate")
    @ResponseBody
    public TetrisDTO.ScoreResult calculateScore(@RequestParam String playerId,
                                             @RequestParam int lines,
                                             @RequestParam int level,
                                             @RequestParam long gameTime) 
    {
        return tetrisService.calculateAndSaveScore(playerId, lines, level, gameTime);
    }
    
    /**
     * 게임 결과 저장 (REST API)
     */
    @PostMapping("/score/save")
    @ResponseBody
    public TetrisDTO.GameResult saveGameResult(@RequestBody TetrisDTO.GameResult gameResult) 
    {
        return tetrisService.saveGameResult(gameResult);
    }
    
    /**
     * 플레이어 점수 기록 조회 (REST API)
     */
    @GetMapping("/score/player/{playerId}")
    @ResponseBody
    public List<TetrisDTO.GameResult> getPlayerScores(@PathVariable String playerId) 
    {
        return tetrisService.getPlayerScores(playerId);
    }
    
    /**
     * 최고 점수 순위 조회 (REST API)
     */
    @GetMapping("/score/leaderboard")
    @ResponseBody
    public List<TetrisDTO.GameResult> getLeaderboard(@RequestParam(defaultValue = "10") int limit) 
    {
        return tetrisService.getLeaderboard(limit);
    }
    
}
