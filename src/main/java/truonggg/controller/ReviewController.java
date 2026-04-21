package truonggg.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import truonggg.constants.ApiPath;
import truonggg.dto.DeleteStatusRequestDTO;
import truonggg.dto.ReviewRequestDTO;
import truonggg.dto.ReviewResponseDTO;
import truonggg.response.SuccessReponse;
import truonggg.service.ReviewService;
import java.util.List;

@RestController
@RequestMapping(ApiPath.REVIEW)
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    public SuccessReponse<List<ReviewResponseDTO>> getAll(@RequestParam(defaultValue = "0") int page,
                                                          @RequestParam(defaultValue = "10") int size){
        return SuccessReponse.ofPaged(this.reviewService.getAll(page, size));
    }

    @PostMapping
    public SuccessReponse<ReviewResponseDTO> save(@RequestBody @Valid ReviewRequestDTO dto){
        return SuccessReponse.of(this.reviewService.save(dto));
    }

    @PutMapping("/{id}")
    public SuccessReponse<ReviewResponseDTO> update(@Valid @RequestBody ReviewRequestDTO dto, @PathVariable Integer id){
        return SuccessReponse.of(this.reviewService.update(dto,id));
    }

    @PatchMapping("/{id}/delete-status")
    public SuccessReponse<String> updateDeleteStatus(@Valid @RequestBody DeleteStatusRequestDTO dto,
                                                     @Valid @PathVariable Integer id){
        this.reviewService.updateDeleteStatus(id, dto);
        return SuccessReponse.of("Update delete status successfully");
    }
}
