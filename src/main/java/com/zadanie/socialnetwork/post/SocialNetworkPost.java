package com.zadanie.socialnetwork.post;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "social_network_posts")
class SocialNetworkPost {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "post_date")
    private Date postDate;

    @Getter
    @NotNull
    @Column(name = "author")
    private String author;

    @Setter
    @Getter
    @NotNull
    @Column(name = "content")
    private String content;

    @Getter
    @NotNull
    @Column(name = "view_count")
    private BigInteger viewCount;


}
