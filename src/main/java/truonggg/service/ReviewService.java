package truonggg.service;

import truonggg.dto.ReviewRequestDTO;
import truonggg.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewService {

    List<ReviewResponseDTO> getAll();
    ReviewResponseDTO save(ReviewRequestDTO dto);
    ReviewResponseDTO update(ReviewRequestDTO dto,Integer id);
    void delete(Integer id);
}
