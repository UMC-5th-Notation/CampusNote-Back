package UMC.campusNote.page.repository;

import UMC.campusNote.page.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PageRepository extends JpaRepository<Page, Long> {
    Page findByNoteIdAndPageNumber(Long noteId, Integer pageNumber);
    List<Page> findAllByNoteId(Long noteId);
}
