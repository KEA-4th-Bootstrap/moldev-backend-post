package org.bootstrap.post.controller;

import lombok.RequiredArgsConstructor;
import org.bootstrap.post.common.SuccessResponse;
import org.bootstrap.post.dto.request.PostRequestDto;
import org.bootstrap.post.dto.response.CreatePostResponseDto;
import org.bootstrap.post.dto.response.PostDetailResponseDto;
import org.bootstrap.post.dto.response.PostsCategoryResponseDto;
import org.bootstrap.post.dto.response.PostsResponseDto;
import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<SuccessResponse<?>> getPosts(@RequestHeader("id") final Long memberId,
                                                       final Pageable pageable) {
        final PostsResponseDto responseDto = postService.getPosts(memberId, pageable);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> getPost(@PathVariable("id") final Long postId) {
        final PostDetailResponseDto responseDto = postService.getPost(postId);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/category")
    public ResponseEntity<SuccessResponse<?>> getPostForCategory(@RequestHeader("id") final Long memberId,
                                                                 @RequestParam final CategoryType type,
                                                                 final Pageable pageable) {
        final PostsCategoryResponseDto responseDto = postService.getPostForCategory(memberId, type, pageable);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createPost(@RequestHeader("id") final Long memberId,
                                                         @RequestPart final MultipartFile thumbnail,
                                                         @RequestPart final PostRequestDto requestDto) {
        final CreatePostResponseDto responseDto = postService.createPost(memberId, requestDto, thumbnail);
        return SuccessResponse.created(responseDto);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> updatePost(@PathVariable("id") final Long postId,
                                                         @RequestPart(required = false) final MultipartFile thumbnail,
                                                         @RequestPart(required = false) final PostRequestDto requestDto) {
        postService.updatePost(postId, requestDto, thumbnail);
        return SuccessResponse.ok(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> deletePost(@PathVariable("id") final Long postId) {
        postService.deletePost(postId);
        return SuccessResponse.ok(null);
    }
}
