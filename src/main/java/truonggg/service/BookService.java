package truonggg.service;

import truonggg.dto.BookRequestDTO;
import truonggg.dto.BookResponseDTO;

import java.util.List;

public interface BookService {

    List<BookResponseDTO>getAll();
    BookResponseDTO save (BookRequestDTO dto);
    BookResponseDTO update(BookRequestDTO dto,Integer id);
    void delete(Integer id);
}
