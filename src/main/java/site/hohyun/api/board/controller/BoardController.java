package site.hohyun.api.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import site.hohyun.api.board.domain.BoardDTO;
import site.hohyun.api.board.domain.BoardVO;
import site.hohyun.api.board.service.BoardService;

import java.util.List;
import java.util.Optional;

/**
 * 게시판 컨트롤러
 * 웹 요청을 처리하는 컨트롤러 계층
 */
@Controller
@RequestMapping("/board")
public class BoardController 
{
    private final BoardService boardService;

    public BoardController(BoardService boardService) 
    {
        this.boardService = boardService;
    }

    /**
     * 게시판 목록 페이지 표시
     */
    @GetMapping
    public String boardList(Model model) 
    {
        System.out.println("게시판 컨트롤러: 게시판 목록 페이지 요청");
        
        List<BoardVO> boards = boardService.getAllBoards();
        model.addAttribute("boards", boards);
        model.addAttribute("boardCount", boards.size());
        
        return "board/board";
    }

    /**
     * 게시글 상세 페이지 표시
     */
    @GetMapping("/detail")
    public String boardDetail(@RequestParam("id") Long id, Model model) 
    {
        System.out.println("게시판 컨트롤러: 게시글 상세 페이지 요청 - ID: " + id);
        
        Optional<BoardVO> board = boardService.getBoardById(id);
        
        if (board.isPresent()) 
        {
            // 조회수 증가
            boardService.incrementViewCount(id);
            model.addAttribute("board", board.get());
            return "board/detail";
        } 
        else 
        {
            model.addAttribute("error", "해당 게시글을 찾을 수 없습니다.");
            return "board/board";
        }
    }

    /**
     * 게시글 작성 페이지 표시
     */
    @GetMapping("/write")
    public String boardWriteForm(Model model) 
    {
        System.out.println("게시판 컨트롤러: 게시글 작성 페이지 요청");
        
        model.addAttribute("boardDTO", new BoardDTO());
        return "board/write";
    }

    /**
     * 게시글 작성 처리
     */
    @PostMapping("/write")
    public String boardWrite(@ModelAttribute BoardDTO boardDTO, Model model) 
    {
        System.out.println("게시판 컨트롤러: 게시글 작성 요청 - " + boardDTO.getTitle());
        
        var messenger = boardService.createBoard(boardDTO);
        model.addAttribute("message", messenger.getMessage());
        
        if (messenger.getCode() == 0) 
        {
            // 성공 시 게시판 목록으로 리다이렉트
            return "redirect:/board";
        } 
        else 
        {
            // 실패 시 작성 페이지로 돌아가기
            model.addAttribute("boardDTO", boardDTO);
            model.addAttribute("error", messenger.getMessage());
            return "board/write";
        }
    }

    /**
     * 게시글 수정 페이지 표시
     */
    @GetMapping("/edit")
    public String boardEditForm(@RequestParam("id") Long id, Model model) 
    {
        System.out.println("게시판 컨트롤러: 게시글 수정 페이지 요청 - ID: " + id);
        
        Optional<BoardVO> board = boardService.getBoardById(id);
        
        if (board.isPresent()) 
        {
            // VO를 DTO로 변환
            BoardDTO boardDTO = new BoardDTO();
            boardDTO.setId(board.get().getId());
            boardDTO.setTitle(board.get().getTitle());
            boardDTO.setContent(board.get().getContent());
            boardDTO.setAuthor(board.get().getAuthor());
            boardDTO.setCategory(board.get().getCategory());
            boardDTO.setCreatedAt(board.get().getCreatedAt());
            boardDTO.setUpdatedAt(board.get().getUpdatedAt());
            boardDTO.setViewCount(board.get().getViewCount());
            
            model.addAttribute("boardDTO", boardDTO);
            return "board/edit";
        } 
        else 
        {
            model.addAttribute("error", "해당 게시글을 찾을 수 없습니다.");
            return "redirect:/board";
        }
    }

    /**
     * 게시글 수정 처리
     */
    @PostMapping("/edit")
    public String boardEdit(@ModelAttribute BoardDTO boardDTO, Model model) 
    {
        System.out.println("게시판 컨트롤러: 게시글 수정 요청 - ID: " + boardDTO.getId());
        
        var messenger = boardService.updateBoard(boardDTO.getId(), boardDTO);
        model.addAttribute("message", messenger.getMessage());
        
        if (messenger.getCode() == 0) 
        {
            // 성공 시 게시글 상세 페이지로 리다이렉트
            return "redirect:/board/detail?id=" + boardDTO.getId();
        } 
        else 
        {
            // 실패 시 수정 페이지로 돌아가기
            model.addAttribute("boardDTO", boardDTO);
            model.addAttribute("error", messenger.getMessage());
            return "board/edit";
        }
    }

    /**
     * 게시글 삭제 처리
     */
    @PostMapping("/delete")
    public String boardDelete(@RequestParam("id") Long id, Model model) 
    {
        System.out.println("게시판 컨트롤러: 게시글 삭제 요청 - ID: " + id);
        
        var messenger = boardService.deleteBoard(id);
        model.addAttribute("message", messenger.getMessage());
        
        return "redirect:/board";
    }

    /**
     * 카테고리별 게시글 조회
     */
    @GetMapping("/category")
    public String boardsByCategory(@RequestParam("category") String category, Model model) 
    {
        System.out.println("게시판 컨트롤러: 카테고리별 게시글 조회 - " + category);
        
        List<BoardVO> boards = boardService.getBoardsByCategory(category);
        model.addAttribute("boards", boards);
        model.addAttribute("category", category);
        model.addAttribute("boardCount", boards.size());
        
        return "board/board";
    }

    /**
     * 게시글 검색
     */
    @GetMapping("/search")
    public String searchBoards(@RequestParam("keyword") String keyword, Model model) 
    {
        System.out.println("게시판 컨트롤러: 게시글 검색 - " + keyword);
        
        List<BoardVO> boards = boardService.searchBoardsByTitle(keyword);
        model.addAttribute("boards", boards);
        model.addAttribute("keyword", keyword);
        model.addAttribute("boardCount", boards.size());
        
        return "board/board";
    }
}
