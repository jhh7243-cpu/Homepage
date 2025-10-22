package site.hohyun.api.board.service;

import org.springframework.stereotype.Service;
import site.hohyun.api.board.domain.BoardDTO;
import site.hohyun.api.board.domain.BoardEntity;
import site.hohyun.api.board.domain.BoardVO;
import site.hohyun.api.board.repository.BoardRepository;
import site.hohyun.api.common.domain.Messenger;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 게시판 서비스
 * 비즈니스 로직을 담당하는 서비스 계층
 */
@Service
public class BoardService 
{
    private final BoardRepository boardRepository;

    public BoardService(BoardRepository boardRepository) 
    {
        this.boardRepository = boardRepository;
    }

    /**
     * 모든 게시글 조회
     */
    public List<BoardVO> getAllBoards() 
    {
        System.out.println("게시판 서비스: 모든 게시글 조회");
        return boardRepository.findAll().stream()
                .map(BoardVO::new)
                .collect(Collectors.toList());
    }

    /**
     * ID로 게시글 조회
     */
    public Optional<BoardVO> getBoardById(Long id) 
    {
        System.out.println("게시판 서비스: ID로 게시글 조회 - " + id);
        return boardRepository.findById(id)
                .map(BoardVO::new);
    }

    /**
     * 게시글 생성
     */
    public Messenger createBoard(BoardDTO boardDTO) 
    {
        System.out.println("게시판 서비스: 게시글 생성 - " + boardDTO.getTitle());
        
        Messenger messenger = new Messenger();
        
        try 
        {
            // 유효성 검사
            if (boardDTO.getTitle() == null || boardDTO.getTitle().trim().isEmpty()) 
            {
                messenger.setCode(1);
                messenger.setMessage("제목을 입력해주세요.");
                return messenger;
            }
            
            if (boardDTO.getContent() == null || boardDTO.getContent().trim().isEmpty()) 
            {
                messenger.setCode(2);
                messenger.setMessage("내용을 입력해주세요.");
                return messenger;
            }
            
            if (boardDTO.getAuthor() == null || boardDTO.getAuthor().trim().isEmpty()) 
            {
                messenger.setCode(3);
                messenger.setMessage("작성자를 입력해주세요.");
                return messenger;
            }

            // DTO를 Entity로 변환하여 저장
            BoardEntity entity = boardDTO.toEntity();
            BoardEntity savedEntity = boardRepository.save(entity);
            
            messenger.setCode(0);
            messenger.setMessage("게시글이 성공적으로 작성되었습니다. (ID: " + savedEntity.getId() + ")");
            
        } 
        catch (Exception e) 
        {
            System.err.println("게시글 생성 오류: " + e.getMessage());
            messenger.setCode(999);
            messenger.setMessage("게시글 작성 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return messenger;
    }

    /**
     * 게시글 수정
     */
    public Messenger updateBoard(Long id, BoardDTO boardDTO) 
    {
        System.out.println("게시판 서비스: 게시글 수정 - ID: " + id);
        
        Messenger messenger = new Messenger();
        
        try 
        {
            Optional<BoardEntity> existingBoard = boardRepository.findById(id);
            
            if (existingBoard.isEmpty()) 
            {
                messenger.setCode(404);
                messenger.setMessage("해당 게시글을 찾을 수 없습니다.");
                return messenger;
            }
            
            // 유효성 검사
            if (boardDTO.getTitle() == null || boardDTO.getTitle().trim().isEmpty()) 
            {
                messenger.setCode(1);
                messenger.setMessage("제목을 입력해주세요.");
                return messenger;
            }
            
            if (boardDTO.getContent() == null || boardDTO.getContent().trim().isEmpty()) 
            {
                messenger.setCode(2);
                messenger.setMessage("내용을 입력해주세요.");
                return messenger;
            }

            // 기존 게시글 정보 유지하면서 수정
            BoardEntity entity = existingBoard.get();
            entity.setTitle(boardDTO.getTitle());
            entity.setContent(boardDTO.getContent());
            entity.setCategory(boardDTO.getCategory());
            entity.setUpdatedAt(LocalDateTime.now());
            
            boardRepository.save(entity);
            
            messenger.setCode(0);
            messenger.setMessage("게시글이 성공적으로 수정되었습니다.");
            
        } 
        catch (Exception e) 
        {
            System.err.println("게시글 수정 오류: " + e.getMessage());
            messenger.setCode(999);
            messenger.setMessage("게시글 수정 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return messenger;
    }

    /**
     * 게시글 삭제
     */
    public Messenger deleteBoard(Long id) 
    {
        System.out.println("게시판 서비스: 게시글 삭제 - ID: " + id);
        
        Messenger messenger = new Messenger();
        
        try 
        {
            boolean deleted = boardRepository.deleteById(id);
            
            if (deleted) 
            {
                messenger.setCode(0);
                messenger.setMessage("게시글이 성공적으로 삭제되었습니다.");
            } 
            else 
            {
                messenger.setCode(404);
                messenger.setMessage("해당 게시글을 찾을 수 없습니다.");
            }
            
        } 
        catch (Exception e) 
        {
            System.err.println("게시글 삭제 오류: " + e.getMessage());
            messenger.setCode(999);
            messenger.setMessage("게시글 삭제 중 오류가 발생했습니다: " + e.getMessage());
        }
        
        return messenger;
    }

    /**
     * 조회수 증가
     */
    public void incrementViewCount(Long id) 
    {
        System.out.println("게시판 서비스: 조회수 증가 - ID: " + id);
        boardRepository.incrementViewCount(id);
    }

    /**
     * 카테고리별 게시글 조회
     */
    public List<BoardVO> getBoardsByCategory(String category) 
    {
        System.out.println("게시판 서비스: 카테고리별 게시글 조회 - " + category);
        return boardRepository.findByCategory(category).stream()
                .map(BoardVO::new)
                .collect(Collectors.toList());
    }

    /**
     * 제목으로 게시글 검색
     */
    public List<BoardVO> searchBoardsByTitle(String keyword) 
    {
        System.out.println("게시판 서비스: 제목으로 게시글 검색 - " + keyword);
        return boardRepository.findByTitleContaining(keyword).stream()
                .map(BoardVO::new)
                .collect(Collectors.toList());
    }

    /**
     * 게시글 수 조회
     */
    public long getBoardCount() 
    {
        return boardRepository.count();
    }
}