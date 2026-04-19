package truonggg.service;

import truonggg.dto.AuthorRequestDTO;
import truonggg.dto.AuthorResponseDTO;

import java.util.List;

public interface AuthorService {

    List<AuthorResponseDTO> getAll();

    AuthorResponseDTO save(AuthorRequestDTO dto);

    AuthorResponseDTO update(AuthorRequestDTO dto,Integer id);

    void delete(Integer id);

}
