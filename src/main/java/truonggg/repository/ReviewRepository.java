package truonggg.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truonggg.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review,Integer> {
}
