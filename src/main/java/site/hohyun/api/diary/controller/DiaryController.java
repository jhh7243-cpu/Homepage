package site.hohyun.api.diary.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import lombok.RequiredArgsConstructor;

import site.hohyun.api.diary.domain.DiaryDTO;
import site.hohyun.api.diary.service.DiaryService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/diaries")
public class DiaryController 
{
    private final DiaryService diaryService;

    /**
     * 일기 목록 페이지
     */
    @GetMapping("")
    public String diaryList(Model model) 
    {
        try 
        {
            List<DiaryDTO> diaryList = diaryService.findAll();
            model.addAttribute("diaryList", diaryList);
            model.addAttribute("message", "전체 " + diaryList.size() + "개의 일기가 있습니다.");
        } 
        catch (Exception e) 
        {
            System.err.println("❌ DiaryController: 일기 목록 조회 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "일기 목록 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "diary/list";
    }

    /**
     * 일기 상세 페이지
     */
    @GetMapping("/{id}")
    public String diaryDetail(@PathVariable("id") Long id, Model model) 
    {
        try 
        {
            DiaryDTO diary = diaryService.findById(id);
            if (diary != null) 
            {
                model.addAttribute("diary", diary);
            } 
            else 
            {
                model.addAttribute("error", "일기를 찾을 수 없습니다.");
                return "diary/list";
            }
        } 
        catch (Exception e) 
        {
            System.err.println("❌ DiaryController: 일기 조회 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "일기 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "diary/detail";
    }

    /**
     * 일기 작성 페이지 - GET
     */
    @GetMapping("/save")
    public String diarySavePage(Model model) 
    {
        model.addAttribute("diary", new DiaryDTO());
        return "diary/save";
    }

    /**
     * 일기 저장 - POST
     */
    @PostMapping("")
    public String diarySave(DiaryDTO diary, Model model) 
    {
        try 
        {
            diaryService.save(diary);
            model.addAttribute("message", "일기가 저장되었습니다.");
            model.addAttribute("diary", new DiaryDTO());
        } 
        catch (Exception e) 
        {
            System.err.println("❌ DiaryController: 일기 저장 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "일기 저장 중 오류가 발생했습니다: " + e.getMessage());
            model.addAttribute("diary", diary);
        }
        return "diary/save";
    }

    /**
     * 일기 수정 페이지 - GET
     */
    @GetMapping("/update/{id}")
    public String diaryUpdatePage(@PathVariable("id") Long id, Model model) 
    {
        try 
        {
            DiaryDTO diary = diaryService.findById(id);
            if (diary != null) 
            {
                model.addAttribute("diary", diary);
            } 
            else 
            {
                model.addAttribute("error", "일기를 찾을 수 없습니다.");
                return "diary/list";
            }
        } 
        catch (Exception e) 
        {
            System.err.println("❌ DiaryController: 일기 조회 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "일기 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "diary/update";
    }

    /**
     * 일기 수정 - PUT
     */
    @PutMapping("/update/{id}")
    public String diaryUpdate(@PathVariable("id") Long id, DiaryDTO diary, Model model) 
    {
        try 
        {
            DiaryDTO updatedDiary = diaryService.save(diary);
            model.addAttribute("message", "일기가 수정되었습니다.");
            model.addAttribute("diary", updatedDiary);
        } 
        catch (Exception e) 
        {
            System.err.println("❌ DiaryController: 일기 수정 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "일기 수정 중 오류가 발생했습니다: " + e.getMessage());
            model.addAttribute("diary", diary);
        }
        return "diary/update";
    }

    /**
     * 일기 삭제 - GET (브라우저용)
     */
    @GetMapping("/delete/{id}")
    public String diaryDelete(@PathVariable("id") Long id, Model model) 
    {
        try 
        {
            diaryService.delete(id);
            model.addAttribute("message", "일기가 삭제되었습니다.");
        } 
        catch (Exception e) 
        {
            System.err.println("❌ DiaryController: 일기 삭제 오류: " + e.getMessage());
            e.printStackTrace();
            model.addAttribute("error", "일기 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
        return "redirect:/diaries";
    }
}
