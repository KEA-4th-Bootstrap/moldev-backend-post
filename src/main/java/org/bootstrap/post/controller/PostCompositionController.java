package org.bootstrap.post.controller;

import lombok.RequiredArgsConstructor;
import org.bootstrap.post.dto.response.CompositionCategoryPostResponseDto;
import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.service.PostCompositionService;
import org.bootstrap.post.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/compose/post")
@RestController
public class PostCompositionController {
    private final PostCompositionService postCompositionService;

    @GetMapping("/{moldevId}/category")
    public ResponseEntity<CompositionCategoryPostResponseDto> getCompositionPostForCategory(@PathVariable final String moldevId,
                                                                                            @RequestParam final CategoryType type) {
        final CompositionCategoryPostResponseDto responseDto
                = postCompositionService.getPostForCategoryAndMoldevId(moldevId, type);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
