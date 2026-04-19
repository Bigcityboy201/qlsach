package truonggg.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import truonggg.dto.AuthorRequestDTO;
import truonggg.dto.AuthorResponseDTO;
import truonggg.entity.Author;
import truonggg.mapper.AuthorMapper;
import truonggg.repository.AuthorRepository;
import truonggg.service.AuthorService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    @Override
    public List<AuthorResponseDTO> getAll() {
        return this.authorMapper.toDTOList(this.authorRepository.findAll());
    }

    @Override
    public AuthorResponseDTO save(AuthorRequestDTO dto) {
        return this.authorMapper.toDTO(this.authorRepository.save(this.authorMapper.toEntity(dto)));
    }

    @Override
    public AuthorResponseDTO update(AuthorRequestDTO dto,Integer id) {
        Author author=this.authorRepository.findById(id).orElseThrow(()->new RuntimeException("Author not found!"));
        author.setName(dto.getName());
        author.setUpdateAt(LocalDateTime.now());
        return this.authorMapper.toDTO(this.authorRepository.save(author));
    }

    @Override
    public void delete(Integer id) {
        Author author=this.authorRepository.findById(id).orElseThrow(()->new RuntimeException("Author not found !"));
        this.authorRepository.deleteById(id);
    }

}
