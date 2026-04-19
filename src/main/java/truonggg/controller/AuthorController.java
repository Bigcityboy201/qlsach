package truonggg.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import truonggg.dto.AuthorRequestDTO;
import truonggg.dto.AuthorResponseDTO;
import truonggg.service.AuthorService;

import java.util.List;

@RestController
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public List<AuthorResponseDTO> getAll(){
        return this.authorService.getAll();
    }

    @PostMapping
    public AuthorResponseDTO save(@Valid @RequestBody AuthorRequestDTO dto){
        return this.authorService.save(dto);
    }

    @PutMapping("/{id}")
    public AuthorResponseDTO update(@Valid @RequestBody AuthorRequestDTO dto,@PathVariable Integer id){
        return this.authorService.update(dto,id);
    }

    @DeleteMapping("/{id}")
    public void delete(@Valid @PathVariable Integer id){
         this.authorService.delete(id);
    }
}
