package truonggg.service.impl;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import truonggg.dto.AuthorRequestDTO;
import truonggg.dto.AuthorResponseDTO;
import truonggg.entity.Author;
import truonggg.helper.ServiceValidationHelper;
import truonggg.handler.BusinessException;
import truonggg.mapper.AuthorMapper;
import truonggg.repository.AuthorRepository;
import truonggg.response.ErrorCode;
import truonggg.response.PagedResult;
import truonggg.service.AuthorService;
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorMapper authorMapper;
    private final AuthorRepository authorRepository;

    @Override
    public PagedResult<AuthorResponseDTO> getAll(int page, int size) {
        log.info("Get all authors with page={}, size={}", page, size);
        ServiceValidationHelper.validatePageAndSize(page, size, "author");
        Page<Author> authors = this.authorRepository.findAll(PageRequest.of(page, size));
        return PagedResult.from(authors, this.authorMapper.toDTOList(authors.getContent()));
    }

    @Override
    public AuthorResponseDTO save(AuthorRequestDTO dto) {
        log.info("Create author with name={}", dto.getName());
        Author author = this.authorMapper.toEntity(dto);
        author.markCreatedNow();
        return this.authorMapper.toDTO(this.authorRepository.save(author));
    }

    @Override
    public AuthorResponseDTO update(AuthorRequestDTO dto,Integer id) {
        log.info("Update author id={}", id);
        Author author = ServiceValidationHelper.requireExists(this.authorRepository.findById(id),
                () -> new BusinessException("Author not found!", ErrorCode.NOT_FOUND, "author"));
        author.rename(dto.getName());
        return this.authorMapper.toDTO(this.authorRepository.save(author));
    }

    @Override
    public void delete(Integer id) {
        log.info("Delete author id={}", id);
        ServiceValidationHelper.requireExists(this.authorRepository.findById(id),
                () -> new BusinessException("Author not found!", ErrorCode.NOT_FOUND, "author"));
        this.authorRepository.deleteById(id);
    }

}
