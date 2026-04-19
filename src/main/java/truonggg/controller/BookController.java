package truonggg.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import truonggg.constants.ApiPath;
import truonggg.dto.BookRequestDTO;
import truonggg.dto.BookResponseDTO;
import truonggg.response.SuccessReponse;
import truonggg.service.BookService;

@RestController
@RequestMapping(ApiPath.BOOK)
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public SuccessReponse<?> getAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size){
        return SuccessReponse.ofPaged(this.bookService.getAll(page, size));
    }

    @PostMapping
    public SuccessReponse<BookResponseDTO> save( @RequestBody @Valid BookRequestDTO dto){
        return SuccessReponse.of(this.bookService.save(dto));
    }

    @PutMapping("/{id}")
    public SuccessReponse<BookResponseDTO> update(@RequestBody @Valid BookRequestDTO dto,@PathVariable Integer id){
        return SuccessReponse.of(this.bookService.update(dto,id));
    }

    @DeleteMapping("/{id}")
    public SuccessReponse<String> delete(@Valid @PathVariable Integer id){
        this.bookService.delete(id);
        return SuccessReponse.of("Delete successfully");
    }
}
