package truonggg.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import truonggg.dto.BookRequestDTO;
import truonggg.dto.BookResponseDTO;
import truonggg.service.BookService;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookResponseDTO>getAll(){
        return this.bookService.getAll();
    }

    @PostMapping
    public BookResponseDTO save( @RequestBody @Valid BookRequestDTO dto){
        return this.bookService.save(dto);
    }

    @PutMapping("/{id}")
    public BookResponseDTO update(@RequestBody @Valid BookRequestDTO dto,@PathVariable Integer id){
        return this.bookService.update(dto,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @PathVariable Integer id){
        this.bookService.delete(id);
    }
}
