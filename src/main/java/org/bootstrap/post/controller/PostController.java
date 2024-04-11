package org.bootstrap.post.controller;

import lombok.RequiredArgsConstructor;
import org.bootstrap.post.common.SuccessResponse;
import org.bootstrap.post.dto.request.PostRequestDto;
import org.bootstrap.post.dto.response.FrontUrlResponseDto;
import org.bootstrap.post.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {
    private final PostService postService;
    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createPost(final Long memberId,
                                                         @RequestPart final MultipartFile thumbnail,
                                                         @RequestPart final PostRequestDto requestDto) {
        final FrontUrlResponseDto responseDto = postService.createPost(memberId, requestDto, thumbnail);
        return SuccessResponse.created(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> updatePost(@PathVariable final Long postId,
                                                         @RequestPart final MultipartFile thumbnail,
                                                         @RequestPart final PostRequestDto requestDto) {
        postService.updatePost(postId, requestDto, thumbnail);
        return SuccessResponse.ok(null);
    }
}
