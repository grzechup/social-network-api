package com.zadanie.socialnetwork.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;
import java.util.Date;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SocialNetworkPostDTO {

    private Date postDate;
    private String author;
    private String content;
    private BigInteger viewCount;

}
