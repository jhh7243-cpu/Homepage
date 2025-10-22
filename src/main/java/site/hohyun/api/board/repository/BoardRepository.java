package site.hohyun.api.board.repository;

import org.springframework.stereotype.Repository;
import site.hohyun.api.board.domain.BoardEntity;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 게시판 레포지토리
 * 데이터 접근 계층을 담당하는 레포지토리
 */
@Repository
public class BoardRepository 
{
    // 임시 데이터 저장소 (실제로는 DB를 사용)
    private final List<BoardEntity> boards = new ArrayList<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    // 초기 데이터 생성
    public BoardRepository() 
    {
        initializeData();
    }

    /**
     * 초기 데이터 생성
     */
    private void initializeData() 
    {
        LocalDateTime now = LocalDateTime.now();
        
        boards.add(new BoardEntity(idGenerator.getAndIncrement(), 
                "게시판 이용 안내", 
                "게시판을 이용해주셔서 감사합니다. 건전한 게시판 문화를 위해 서로 존중하며 이용해주세요.", 
                "관리자", 
                "공지", 
                now.minusDays(5), 
                now.minusDays(5), 
                45));
        
        boards.add(new BoardEntity(idGenerator.getAndIncrement(), 
                "첫 번째 게시글입니다", 
                "안녕하세요! 첫 번째 게시글을 작성해봅니다.", 
                "사용자1", 
                "일반", 
                now.minusDays(3), 
                now.minusDays(3), 
                12));
        
        boards.add(new BoardEntity(idGenerator.getAndIncrement(), 
                "질문이 있습니다", 
                "게시판 사용법에 대해 궁금한 점이 있습니다.", 
                "사용자2", 
                "질문", 
                now.minusDays(1), 
                now.minusDays(1), 
                8));
        
        boards.add(new BoardEntity(idGenerator.getAndIncrement(), 
                "정보 공유합니다", 
                "유용한 정보를 공유하고 싶어서 글을 올립니다.", 
                "사용자3", 
                "정보", 
                now.minusHours(12), 
                now.minusHours(12), 
                23));
        
        boards.add(new BoardEntity(idGenerator.getAndIncrement(), 
                "자유게시판 테스트", 
                "자유롭게 의견을 나눠보세요.", 
                "사용자4", 
                "자유", 
                now.minusHours(6), 
                now.minusHours(6), 
                15));
    }

    /**
     * 모든 게시글 조회
     */
    public List<BoardEntity> findAll() 
    {
        return new ArrayList<>(boards);
    }

    /**
     * ID로 게시글 조회
     */
    public Optional<BoardEntity> findById(Long id) 
    {
        return boards.stream()
                .filter(board -> board.getId().equals(id))
                .findFirst();
    }

    /**
     * 게시글 저장
     */
    public BoardEntity save(BoardEntity board) 
    {
        if (board.getId() == null) 
        {
            // 새 게시글 생성
            board.setId(idGenerator.getAndIncrement());
            board.setCreatedAt(LocalDateTime.now());
            board.setUpdatedAt(LocalDateTime.now());
            board.setViewCount(0);
            boards.add(board);
        } 
        else 
        {
            // 기존 게시글 수정
            board.setUpdatedAt(LocalDateTime.now());
            boards.replaceAll(b -> b.getId().equals(board.getId()) ? board : b);
        }
        return board;
    }

    /**
     * 게시글 삭제
     */
    public boolean deleteById(Long id) 
    {
        return boards.removeIf(board -> board.getId().equals(id));
    }

    /**
     * 조회수 증가
     */
    public void incrementViewCount(Long id) 
    {
        boards.stream()
                .filter(board -> board.getId().equals(id))
                .findFirst()
                .ifPresent(board -> board.setViewCount(board.getViewCount() + 1));
    }

    /**
     * 카테고리별 게시글 조회
     */
    public List<BoardEntity> findByCategory(String category) 
    {
        return boards.stream()
                .filter(board -> board.getCategory().equals(category))
                .toList();
    }

    /**
     * 작성자별 게시글 조회
     */
    public List<BoardEntity> findByAuthor(String author) 
    {
        return boards.stream()
                .filter(board -> board.getAuthor().equals(author))
                .toList();
    }

    /**
     * 제목으로 게시글 검색
     */
    public List<BoardEntity> findByTitleContaining(String keyword) 
    {
        return boards.stream()
                .filter(board -> board.getTitle().contains(keyword))
                .toList();
    }

    /**
     * 게시글 수 조회
     */
    public long count() 
    {
        return boards.size();
    }
}