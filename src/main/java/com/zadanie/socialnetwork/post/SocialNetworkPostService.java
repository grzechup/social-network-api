package com.zadanie.socialnetwork.post;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
class SocialNetworkPostService {

    private final SocialNetworkPostRepository socialNetworkPostRepository;

    SocialNetworkPostService(SocialNetworkPostRepository socialNetworkPostRepository) {
        this.socialNetworkPostRepository = socialNetworkPostRepository;
    }

    Long createPost(CreateSocialNetworkPostRequest createSocialNetworkPostRequest) {
        SocialNetworkPost socialNetworkPost = SocialNetworkPostMapper.createNewSocialNetworkPostEntity(createSocialNetworkPostRequest);
        return socialNetworkPostRepository.save(socialNetworkPost).getId();
    }

    SocialNetworkPostDTO getPost(Long id) {
        return socialNetworkPostRepository.findById(id)
                .map(SocialNetworkPostMapper::createSocialNetworkPostDTO)
                .orElseThrow(SocialNetworkPostNotFoundException::new);
    }

    SocialNetworkPostDTO updatePost(Long id, UpdateSocialNetworkPostRequest request) {
        SocialNetworkPost socialNetworkPost = socialNetworkPostRepository.findById(id)
                .orElseThrow(SocialNetworkPostNotFoundException::new);
        SocialNetworkPost updatedEntity = SocialNetworkPostMapper.createUpdatedSocialNetworkPostDTO(socialNetworkPost, request);
        return SocialNetworkPostMapper.createSocialNetworkPostDTO(socialNetworkPostRepository.save(updatedEntity));
    }

    void deletePost(Long id) {
        socialNetworkPostRepository.deleteById(id);
    }

    Page<SocialNetworkPostDTO> getPageOfPosts(Pageable pageable) {
        return socialNetworkPostRepository.findAll(pageable)
                .map(SocialNetworkPostMapper::createSocialNetworkPostDTO);
    }

    List<SocialNetworkPostDTO> retrievePostWithTopTenHighestViewCount() {
        return socialNetworkPostRepository.findAllByOrderByViewCountDesc(PageRequest.of(0, 10)).stream()
                .map(SocialNetworkPostMapper::createSocialNetworkPostDTO)
                .collect(Collectors.toList());

    }
}
