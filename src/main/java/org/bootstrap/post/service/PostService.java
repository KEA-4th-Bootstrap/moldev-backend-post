package org.bootstrap.post.service;

import lombok.RequiredArgsConstructor;
import org.bootstrap.post.dto.request.PostRequestDto;
import org.bootstrap.post.entity.Post;
import org.bootstrap.post.helper.PostHelper;
import org.bootstrap.post.mapper.PostMapper;
import org.bootstrap.post.utils.FrontUrlGenerator;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostService {
    private final PostMapper postMapper;
    private final PostHelper postHelper;

    public void createPost(Long memberId, PostRequestDto requestDto) {
        Post post = createPostAndSave(memberId, requestDto);
        String frontendUrl = FrontUrlGenerator.createFrontUrl(post.getId(), requestDto.moldevId(), post.getCategory());
        post.updateFrontUrl(frontendUrl);
    }

    private Post createPostAndSave(Long memberId, PostRequestDto requestDto) {
        Post post = postMapper.toEntity(requestDto, memberId, "");
        return postHelper.savePost(post);
    }
}
