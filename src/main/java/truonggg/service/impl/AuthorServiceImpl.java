package truonggg.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import truonggg.dto.AuthorRequestDTO;
import truonggg.dto.AuthorResponseDTO;
import truonggg.entity.Author;
import truonggg.handler.BusinessException;
import truonggg.mapper.AuthorMapper;
import truonggg.repository.AuthorRepository;
import truonggg.response.ErrorCode;
import truonggg.response.PagedResult;
import truonggg.service.AuthorService;

import java.time.LocalDateTime;
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    @Override
    public PagedResult<AuthorResponseDTO> getAll(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new BusinessException("Page must be >= 0 and size must be > 0", ErrorCode.BAD_REQUEST, "author");
        }
        Page<Author> authors = this.authorRepository.findAll(PageRequest.of(page, size));
        return PagedResult.from(authors, this.authorMapper.toDTOList(authors.getContent()));
    }

    @Override
    public AuthorResponseDTO save(AuthorRequestDTO dto) {
        Author author = this.authorMapper.toEntity(dto);
        LocalDateTime now = LocalDateTime.now();
        author.setCreateAt(now);
        author.setUpdateAt(now);
        return this.authorMapper.toDTO(this.authorRepository.save(author));
    }

    @Override
    public AuthorResponseDTO update(AuthorRequestDTO dto,Integer id) {
        Author author = this.authorRepository.findById(id).orElseThrow(
                () -> new BusinessException("Author not found!", ErrorCode.NOT_FOUND, "author"));
        author.setName(dto.getName());
        author.setUpdateAt(LocalDateTime.now());
        return this.authorMapper.toDTO(this.authorRepository.save(author));
    }

    @Override
    public void delete(Integer id) {
        this.authorRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Author not found!", ErrorCode.NOT_FOUND, "author"));
        this.authorRepository.deleteById(id);
    }

}
