package org.bootstrap.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bootstrap.post.common.SuccessResponse;
import org.bootstrap.post.dto.request.PostRequestDto;
import org.bootstrap.post.dto.response.*;
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

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> getPosts(@RequestHeader("id") final Long memberId,
                                                       final Pageable pageable) {
        final PostsResponseDto responseDto = postService.getPosts(memberId, pageable);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/{postId}/detail")
    public ResponseEntity<SuccessResponse<?>> getPostDetail(@PathVariable final Long postId) {
        final PostDetailResponseDto responseDto = postService.getPostDetail(postId);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/{postId}/category/list")
    public ResponseEntity<SuccessResponse<?>> getSameCategoryPosts(@PathVariable final Long postId,
                                                                   @RequestParam final CategoryType type) {
        final SameCategoryPostsResponseDto responseDto = postService.getSameCategoryPosts(postId, type);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/mission-control")
    public ResponseEntity<SuccessResponse<?>> getPostForCategory(@RequestParam final String moldevId,
                                                                 @RequestParam final CategoryType type,
                                                                 final Pageable pageable) {
        final PostsCategoryResponseDto responseDto = postService.getPostForCategory(moldevId, type, pageable);
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

    @PostMapping("/view/{postId}")
    public ResponseEntity<SuccessResponse<?>> viewCountUp(@PathVariable Long postId,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response){
        postService.viewCountUpByCookie(postId, request, response);
        return SuccessResponse.ok(null);
    }

    @GetMapping("/trend")
    public ResponseEntity<SuccessResponse<?>> getTrendingPosts() {
        final TrendingPostsResponseDto responseDto = postService.getTrendingPosts();
        return SuccessResponse.ok(responseDto);
    }
}
