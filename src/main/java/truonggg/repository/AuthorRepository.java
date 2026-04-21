package truonggg.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import truonggg.entity.Author;

import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {
    Page<Author> findAllByDeletedFalse(Pageable pageable);
    Optional<Author> findByIdAndDeletedFalse(Integer id);
}
