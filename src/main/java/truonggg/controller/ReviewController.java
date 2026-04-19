package truonggg.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import truonggg.dto.ReviewRequestDTO;
import truonggg.dto.ReviewResponseDTO;
import truonggg.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public List<ReviewResponseDTO> getAll(){
        return this.reviewService.getAll();
    }

    @PostMapping
    public ReviewResponseDTO save(@RequestBody ReviewRequestDTO dto){
        return this.reviewService.save(dto);
    }

    @PutMapping("/{id}")
    public ReviewResponseDTO update(@Valid @RequestBody ReviewRequestDTO dto, @PathVariable Integer id){
        return this.reviewService.update(dto,id);
    }

    @DeleteMapping("/{id}")
    public void delte(@Valid @PathVariable Integer id){
        this.reviewService.delete(id);
    }
}
