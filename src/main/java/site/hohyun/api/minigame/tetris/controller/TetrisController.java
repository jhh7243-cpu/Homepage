package site.hohyun.api.minigame.tetris.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.hohyun.api.minigame.tetris.service.TetrisService;
import site.hohyun.api.minigame.tetris.domain.TetrisGame;

import jakarta.servlet.http.HttpSession;

/**
 * 테트리스 게임 컨트롤러
 * 웹 요청을 처리하고 뷰를 반환
 */
@Controller
@RequestMapping("/minigame/tetris")
public class TetrisController 
{
    private final TetrisService tetrisService;
    
    public TetrisController(TetrisService tetrisService) 
    {
        this.tetrisService = tetrisService;
    }
    
    /**
     * 테트리스 게임 페이지
     * @param session HTTP 세션
     * @param model 모델
     * @return 테트리스 게임 페이지
     */
    @GetMapping
    public String tetrisGame(HttpSession session, Model model) 
    {
        System.out.println("테트리스 컨트롤러: 테트리스 게임 페이지 요청");
        
        String sessionId = session.getId();
        TetrisGame game = tetrisService.getGame(sessionId);
        
        model.addAttribute("game", game);
        model.addAttribute("boardHtml", game.getBoardAsHtml());
        model.addAttribute("nextBlockHtml", game.getNextBlockAsHtml());
        
        return "minigame/tetris/tetris";
    }
    
    /**
     * 새 게임 시작
     * @param session HTTP 세션
     * @return 테트리스 게임 페이지로 리다이렉트
     */
    @PostMapping("/new")
    public String startNewGame(HttpSession session) 
    {
        System.out.println("테트리스 컨트롤러: 새 게임 시작 요청");
        
        String sessionId = session.getId();
        tetrisService.startNewGame(sessionId);
        
        return "redirect:/minigame/tetris";
    }
    
    /**
     * 블록 이동 처리
     * @param direction 이동 방향
     * @param session HTTP 세션
     * @return 테트리스 게임 페이지로 리다이렉트
     */
    @PostMapping("/move")
    public String moveBlock(@RequestParam("direction") String direction, HttpSession session) 
    {
        System.out.println("테트리스 컨트롤러: 블록 이동 요청 - " + direction);
        
        String sessionId = session.getId();
        tetrisService.moveBlock(sessionId, direction);
        
        return "redirect:/minigame/tetris";
    }
    
    /**
     * 블록 회전 처리
     * @param session HTTP 세션
     * @return 테트리스 게임 페이지로 리다이렉트
     */
    @PostMapping("/rotate")
    public String rotateBlock(HttpSession session) 
    {
        System.out.println("테트리스 컨트롤러: 블록 회전 요청");
        
        String sessionId = session.getId();
        tetrisService.rotateBlock(sessionId);
        
        return "redirect:/minigame/tetris";
    }
    
    /**
     * 블록 즉시 낙하 처리
     * @param session HTTP 세션
     * @return 테트리스 게임 페이지로 리다이렉트
     */
    @PostMapping("/drop")
    public String dropBlock(HttpSession session) 
    {
        System.out.println("테트리스 컨트롤러: 블록 즉시 낙하 요청");
        
        String sessionId = session.getId();
        tetrisService.dropBlock(sessionId);
        
        return "redirect:/minigame/tetris";
    }
    
    /**
     * 게임 일시정지/재개
     * @param session HTTP 세션
     * @return 테트리스 게임 페이지로 리다이렉트
     */
    @PostMapping("/pause")
    public String togglePause(HttpSession session) 
    {
        System.out.println("테트리스 컨트롤러: 게임 일시정지/재개 요청");
        
        String sessionId = session.getId();
        tetrisService.togglePause(sessionId);
        
        return "redirect:/minigame/tetris";
    }
    
    /**
     * 게임 재시작
     * @param session HTTP 세션
     * @return 테트리스 게임 페이지로 리다이렉트
     */
    @PostMapping("/restart")
    public String restartGame(HttpSession session) 
    {
        System.out.println("테트리스 컨트롤러: 게임 재시작 요청");
        
        String sessionId = session.getId();
        tetrisService.restartGame(sessionId);
        
        return "redirect:/minigame/tetris";
    }
    
    /**
     * 게임 통계 API (JSON 응답)
     * @param session HTTP 세션
     * @return 게임 통계
     */
    @GetMapping("/api/stats")
    @ResponseBody
    public TetrisService.GameStats getGameStats(HttpSession session) 
    {
        System.out.println("테트리스 컨트롤러: 게임 통계 API 요청");
        
        String sessionId = session.getId();
        return tetrisService.getGameStats(sessionId);
    }
    
    /**
     * 키보드 이벤트 처리
     * @param action 키보드 액션 (left, right, down, up, space, rotateLeft, rotateRight)
     * @param session HTTP 세션
     * @return 테트리스 게임 페이지로 리다이렉트
     */
    @PostMapping("/keyboard")
    public String handleKeyboard(@RequestParam("action") String action, HttpSession session) 
    {
        System.out.println("테트리스 컨트롤러: 키보드 입력 요청 - " + action);
        
        String sessionId = session.getId();
        
        switch (action) 
        {
            case "left":
                tetrisService.moveBlock(sessionId, "left");
                break;
            case "right":
                tetrisService.moveBlock(sessionId, "right");
                break;
            case "down":
                tetrisService.moveBlock(sessionId, "down");
                break;
            case "up":
                tetrisService.rotateBlockRight(sessionId);
                break;
            case "space":
                tetrisService.dropBlock(sessionId);
                break;
            case "rotateLeft":
                tetrisService.rotateBlockLeft(sessionId);
                break;
            case "rotateRight":
                tetrisService.rotateBlockRight(sessionId);
                break;
        }
        
        return "redirect:/minigame/tetris";
    }
    
    /**
     * 마우스 클릭 이벤트 처리 (기존 호환성 유지)
     * @param action 클릭 액션 (rotate, drop)
     * @param session HTTP 세션
     * @return 테트리스 게임 페이지로 리다이렉트
     */
    @PostMapping("/click")
    public String handleMouseClick(@RequestParam("action") String action, HttpSession session) 
    {
        System.out.println("테트리스 컨트롤러: 마우스 클릭 요청 - " + action);
        
        String sessionId = session.getId();
        
        switch (action) 
        {
            case "rotate":
                tetrisService.rotateBlockRight(sessionId);
                break;
            case "drop":
                tetrisService.dropBlock(sessionId);
                break;
        }
        
        return "redirect:/minigame/tetris";
    }
    
    /**
     * 게임 삭제
     * @param session HTTP 세션
     * @return 미니게임 목록으로 리다이렉트
     */
    @PostMapping("/delete")
    public String deleteGame(HttpSession session) 
    {
        System.out.println("테트리스 컨트롤러: 게임 삭제 요청");
        
        String sessionId = session.getId();
        tetrisService.deleteGame(sessionId);
        
        return "redirect:/minigame";
    }
}
