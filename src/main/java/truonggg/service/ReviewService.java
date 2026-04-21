package truonggg.service;

import truonggg.dto.ReviewRequestDTO;
import truonggg.dto.ReviewResponseDTO;
import truonggg.dto.DeleteStatusRequestDTO;
import truonggg.response.PagedResult;

public interface ReviewService {

    PagedResult<ReviewResponseDTO> getAll(int page, int size);
    ReviewResponseDTO save(ReviewRequestDTO dto);
    ReviewResponseDTO update(ReviewRequestDTO dto,Integer id);
    void updateDeleteStatus(Integer id, DeleteStatusRequestDTO dto);
}
