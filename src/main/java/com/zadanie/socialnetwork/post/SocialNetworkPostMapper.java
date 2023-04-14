package com.zadanie.socialnetwork.post;

import java.math.BigInteger;
import java.util.Date;

class SocialNetworkPostMapper {

    static SocialNetworkPostDTO createSocialNetworkPostDTO(SocialNetworkPost entity){
        return SocialNetworkPostDTO.builder()
                .author(entity.getAuthor())
                .content(entity.getContent())
                .viewCount(entity.getViewCount())
                .postDate(entity.getPostDate())
                .build();
    }

    static SocialNetworkPost createUpdatedSocialNetworkPostDTO(SocialNetworkPost entity, UpdateSocialNetworkPostRequest request){
        entity.setContent(request.getContent());
        return entity;
    }

    static SocialNetworkPost createNewSocialNetworkPostEntity(CreateSocialNetworkPostRequest createSocialNetworkPostRequest) {
        return SocialNetworkPost.builder()
                .author(createSocialNetworkPostRequest.getAuthor())
                .content(createSocialNetworkPostRequest.getContent())
                .postDate(new Date())
                .viewCount(BigInteger.ZERO)
                .build();

    }
}
