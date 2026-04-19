package truonggg.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import truonggg.constants.ApiPath;
import truonggg.dto.AuthorRequestDTO;
import truonggg.dto.AuthorResponseDTO;
import truonggg.response.SuccessReponse;
import truonggg.service.AuthorService;

@RestController
@RequestMapping(ApiPath.AUTHOR)
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping
    public SuccessReponse<?> getAll(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size){
        return SuccessReponse.ofPaged(this.authorService.getAll(page, size));
    }

    @PostMapping
    public SuccessReponse<AuthorResponseDTO> save(@Valid @RequestBody AuthorRequestDTO dto){
        return SuccessReponse.of(this.authorService.save(dto));
    }

    @PutMapping("/{id}")
    public SuccessReponse<AuthorResponseDTO> update(@Valid @RequestBody AuthorRequestDTO dto,@PathVariable Integer id){
        return SuccessReponse.of(this.authorService.update(dto,id));
    }

    @DeleteMapping("/{id}")
    public SuccessReponse<String> delete(@Valid @PathVariable Integer id){
         this.authorService.delete(id);
         return SuccessReponse.of("Delete successfully");
    }
}
