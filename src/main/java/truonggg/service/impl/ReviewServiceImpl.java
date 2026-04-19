package truonggg.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import truonggg.dto.ReviewRequestDTO;
import truonggg.dto.ReviewResponseDTO;
import truonggg.entity.Book;
import truonggg.entity.Review;
import truonggg.mapper.ReviewMapper;
import truonggg.repository.BookRepository;
import truonggg.repository.ReviewRepository;
import truonggg.service.ReviewService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewMapper reviewMapper;
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    @Override
    public List<ReviewResponseDTO> getAll() {
        return this.reviewMapper.toDTOList(this.reviewRepository.findAll());
    }

    @Override
    public ReviewResponseDTO save(ReviewRequestDTO dto) {

        Book book=this.bookRepository.findById(dto.getBookId()).orElseThrow(()->new RuntimeException("Book not found!"));

        return this.reviewMapper.toDTO(this.reviewRepository.save(this.reviewMapper.toEntity(dto,book)));
    }

    @Override
    public ReviewResponseDTO update(ReviewRequestDTO dto, Integer id) {
        Book book=this.bookRepository.findById(dto.getBookId()).orElseThrow(()->new RuntimeException("Book not found!"));
        Review review=this.reviewRepository.findById(id).orElseThrow(()-> new RuntimeException("Review not found!"));
        review.setBook(book);
        review.setContent(dto.getContent());
        review.setUpdateAt(LocalDateTime.now());
        return this.reviewMapper.toDTO(this.reviewRepository.save(review));
    }

    @Override
    public void delete(Integer id) {
        this.reviewRepository.deleteById(id);
    }
}
