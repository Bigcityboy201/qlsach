package truonggg.service;

import truonggg.dto.BookRequestDTO;
import truonggg.dto.BookResponseDTO;
import truonggg.response.PagedResult;

public interface BookService {

    PagedResult<BookResponseDTO> getAll(int page, int size);
    BookResponseDTO save (BookRequestDTO dto);
    BookResponseDTO update(BookRequestDTO dto,Integer id);
    void delete(Integer id);
}
