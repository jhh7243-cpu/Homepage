package site.hohyun.api.minigame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import site.hohyun.api.minigame.domain.MinigameDTO;
import site.hohyun.api.minigame.service.MinigameService;

import java.util.List;

/**
 * 미니게임 관련 컨트롤러
 * 미니게임 기능 및 게임 목록 처리
 */
@Controller
@RequestMapping("/minigame")
public class MinigameController 
{
    private final MinigameService minigameService;

    public MinigameController(MinigameService minigameService) 
    {
        this.minigameService = minigameService;
    }

    /**
     * 미니게임 목록 페이지 표시
     * @param error 에러 파라미터
     * @param model 모델 객체
     * @return 미니게임 목록 페이지 템플릿
     */
    @GetMapping
    public String minigamePage(@RequestParam(value = "error", required = false) String error, Model model) 
    {
        System.out.println("미니게임 컨트롤러: 미니게임 목록 페이지 요청");
        
        try 
        {
            List<MinigameDTO> minigames = minigameService.getAllMinigames();
            String stats = minigameService.getMinigameStats();
            
            model.addAttribute("minigames", minigames);
            model.addAttribute("stats", stats);
            model.addAttribute("success", true);
            
            // URL 파라미터로 전달된 에러 처리
            if (error != null) 
            {
                if ("game_not_found".equals(error)) 
                {
                    model.addAttribute("error", "해당 게임을 찾을 수 없습니다.");
                } 
                else if ("play_failed".equals(error)) 
                {
                    model.addAttribute("error", "게임을 실행하는 중 오류가 발생했습니다.");
                }
            }
            
            System.out.println("미니게임 목록 페이지 로드 성공");
        } 
        catch (Exception e) 
        {
            System.out.println("미니게임 목록 페이지 로드 실패: " + e.getMessage());
            model.addAttribute("error", "미니게임 목록을 불러오는 중 오류가 발생했습니다.");
            model.addAttribute("success", false);
        }
        
        return "minigame/minigame";
    }


    /**
     * 장르별 게임 목록 조회
     * @param genre 장르
     * @param model 모델 객체
     * @return 장르별 게임 목록 페이지
     */
    @GetMapping("/genre")
    public String getGamesByGenre(@RequestParam("genre") String genre, Model model) 
    {
        System.out.println("미니게임 컨트롤러: 장르별 게임 목록 요청 - " + genre);
        
        try 
        {
            List<MinigameDTO> minigames = minigameService.getMinigamesByGenre(genre);
            
            model.addAttribute("minigames", minigames);
            model.addAttribute("selectedGenre", genre);
            model.addAttribute("success", true);
            
            System.out.println(genre + " 장르 게임 " + minigames.size() + "개 조회 성공");
        } 
        catch (Exception e) 
        {
            System.out.println("장르별 게임 목록 조회 실패: " + e.getMessage());
            model.addAttribute("error", "장르별 게임 목록을 불러오는 중 오류가 발생했습니다.");
            model.addAttribute("success", false);
        }
        
        return "minigame/minigame";
    }
}
