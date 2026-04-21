package truonggg.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truonggg.entity.Review;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
    Page<Review> findAllByDeletedFalse(Pageable pageable);
    Optional<Review> findByIdAndDeletedFalse(Integer id);
    List<Review> findAllByBookIdAndDeletedFalse(Integer bookId);
}
