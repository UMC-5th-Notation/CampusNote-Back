package UMC.campusNote.image.repository;

import UMC.campusNote.image.entity.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findAllByNoteId(Long noteId);
    Image findByIdAndNoteId(Long imageId, Long noteId);
    void deleteAllByNoteId(Long noteId);
}
