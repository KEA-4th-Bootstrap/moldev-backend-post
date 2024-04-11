package org.bootstrap.post.service;

import lombok.RequiredArgsConstructor;
import org.bootstrap.post.dto.request.PostRequestDto;
import org.bootstrap.post.dto.response.FrontUrlResponseDto;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.helper.PostHelper;
import org.bootstrap.post.mapper.PostMapper;
import org.bootstrap.post.utils.FrontUrlGenerator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostMapper postMapper;
    private final PostHelper postHelper;

    public FrontUrlResponseDto createPost(Long memberId, PostRequestDto requestDto, MultipartFile thumbnail) {
        String thumbnailString = postHelper.createStringThumbnail(thumbnail);
        Post post = createPostAndSave(memberId, requestDto, thumbnailString);
        String frontendUrl = FrontUrlGenerator.createFrontUrl(post, requestDto.moldevId());
        post.updateFrontUrl(frontendUrl);
        return postMapper.toFrontUrlResponseDto(frontendUrl);
    }

    private Post createPostAndSave(Long memberId, PostRequestDto requestDto, String thumbnail) {
        Post post = postMapper.toEntity(requestDto, memberId, thumbnail);
        return postHelper.savePost(post);
    }
}
