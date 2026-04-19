package truonggg.service;

import truonggg.dto.ReviewRequestDTO;
import truonggg.dto.ReviewResponseDTO;
import truonggg.response.PagedResult;

public interface ReviewService {

    PagedResult<ReviewResponseDTO> getAll(int page, int size);
    ReviewResponseDTO save(ReviewRequestDTO dto);
    ReviewResponseDTO update(ReviewRequestDTO dto,Integer id);
    void delete(Integer id);
}
