package site.hohyun.api.news.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import site.hohyun.api.news.service.NewsService;

/**
 * 공지사항 관련 컨트롤러
 * 공지사항 조회 및 관리 기능을 처리
 */
@Controller
@RequestMapping("/news")
public class NewsController 
{
    private final NewsService newsService;
    
    public NewsController(NewsService newsService)
    {
        this.newsService = newsService;
    }

    /**
     * 공지사항 목록 페이지 표시
     * @param model 모델 객체
     * @return 공지사항 페이지 템플릿
     */
    @GetMapping
    public String newsList(Model model) 
    {
        System.out.println("공지사항 컨트롤러로 들어옴");
        
        // 공지사항 목록을 모델에 추가
        model.addAttribute("notices", newsService.getAllNotices());
        
        return "news/news";
    }

    /**
     * 공지사항 상세 페이지 표시
     * @param id 공지사항 ID
     * @param model 모델 객체
     * @return 공지사항 상세 페이지 템플릿
     */
    @GetMapping("/detail")
    public String newsDetail(@RequestParam("id") Long id, Model model) 
    {
        System.out.println("공지사항 상세 컨트롤러로 들어옴 - ID: " + id);
        
        // 공지사항 상세 정보를 모델에 추가
        model.addAttribute("notice", newsService.getNoticeById(id));
        
        return "news/detail";
    }
}
