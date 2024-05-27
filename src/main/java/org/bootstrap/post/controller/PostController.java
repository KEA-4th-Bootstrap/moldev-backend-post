package org.bootstrap.post.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.bootstrap.post.common.SuccessResponse;
import org.bootstrap.post.dto.request.PostRequestDto;
import org.bootstrap.post.dto.response.*;
import org.bootstrap.post.entity.CategoryType;
import org.bootstrap.post.service.PostCompositionService;
import org.bootstrap.post.service.PostService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/post")
@RestController
public class PostController {
    private final PostService postService;
    private final PostCompositionService postCompositionService;

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> getPosts(@RequestHeader("Authorization") final Long memberId,
                                                       final Pageable pageable) {
        final PostsResponseDto responseDto = postService.getPosts(memberId, pageable);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/{postId}/detail")
    public ResponseEntity<PostDetailResponseDto> getPostDetail(@PathVariable final Long postId,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response) {
        final PostDetailResponseDto responseDto = postService.getPostDetail(postId, request, response);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/{moldevId}/category")
    public ResponseEntity<CompositionCategoryPostResponseDto> getCompositionPostForCategory(@PathVariable final String moldevId,
                                                                                            @RequestParam final CategoryType type) {
        final CompositionCategoryPostResponseDto responseDto
                = postCompositionService.getPostForCategoryAndMoldevId(moldevId, type);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/{postId}/category/list")
    public ResponseEntity<SuccessResponse<?>> getSameCategoryPosts(@PathVariable final Long postId,
                                                                   @RequestParam final CategoryType type,
                                                                   @RequestParam final Integer preCount,
                                                                   @RequestParam final Integer postCount) {
        final SameCategoryPostsResponseDto responseDto = postService.getSameCategoryPosts(postId, type, preCount, postCount);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/mission-control")
    public ResponseEntity<SuccessResponse<?>> getPostForCategory(@RequestParam final String moldevId,
                                                                 @RequestParam final CategoryType type,
                                                                 final Pageable pageable) {
        final PostsCategoryResponseDto responseDto = postService.getPostForCategory(moldevId, type, pageable);
        return SuccessResponse.ok(responseDto);
    }

    @GetMapping("/{postId}/images")
    public ResponseEntity<SuccessResponse<?>> getPostImages(@PathVariable final Long postId) {
        final PostImagesResponseDto responseDto = postService.getPostImages(postId);
        return SuccessResponse.ok(responseDto);
    }

    @PostMapping
    public ResponseEntity<SuccessResponse<?>> createPost(@RequestHeader("Authorization") final Long memberId,
                                                         @RequestBody final PostRequestDto requestDto) {
        final CreatePostResponseDto responseDto = postService.createPost(memberId, requestDto);
        return SuccessResponse.created(responseDto);
    }

    @PostMapping("/image")
    public ResponseEntity<SuccessResponse<?>> createPostImage(@RequestPart final MultipartFile thumbnail) {
        final String responseUrl = postService.createPostImage(thumbnail);
        return SuccessResponse.created(responseUrl);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SuccessResponse<?>> updatePost(@PathVariable("id") final Long postId,
                                                         @RequestBody final PostRequestDto requestDto) {
        postService.updatePost(postId, requestDto);
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
                                                          HttpServletResponse response) {
        postService.viewCountUpByCookie(postId, request, response);
        return SuccessResponse.ok(null);
    }

    @GetMapping("/trend")
    public ResponseEntity<TrendingPostsResponseDto> getTrendingPosts() {
        final TrendingPostsResponseDto responseDto = postService.getTrendingPosts();
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @GetMapping("/{moldevId}/recents")
    public ResponseEntity<RecentPostsResponseListDto> getRecentsPosts(@PathVariable String moldevId) {
        final RecentPostsResponseListDto responseDto = postService.getRecentsPosts(moldevId);
        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

}
