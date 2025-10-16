package site.hohyun.api.minigame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 미니게임 리다이렉트 컨트롤러
 * HTML 확장자 요청을 적절한 컨트롤러로 리다이렉트
 */
@Controller
public class MinigameRedirectController 
{
    /**
     * /minigame.html 요청을 /minigame으로 리다이렉트
     * @return 리다이렉트 URL
     */
    @GetMapping("/minigame.html")
    public String redirectToMinigame() 
    {
        System.out.println("미니게임 리다이렉트: /minigame.html -> /minigame");
        return "redirect:/minigame";
    }
}
