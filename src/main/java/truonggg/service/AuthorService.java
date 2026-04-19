package truonggg.service;

import truonggg.dto.AuthorRequestDTO;
import truonggg.dto.AuthorResponseDTO;
import truonggg.response.PagedResult;

public interface AuthorService {

    PagedResult<AuthorResponseDTO> getAll(int page, int size);

    AuthorResponseDTO save(AuthorRequestDTO dto);

    AuthorResponseDTO update(AuthorRequestDTO dto,Integer id);

    void delete(Integer id);

}
