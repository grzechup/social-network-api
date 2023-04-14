package com.zadanie.socialnetwork.post;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface SocialNetworkPostRepository extends JpaRepository<SocialNetworkPost, Long> {

    List<SocialNetworkPost> findAllByOrderByViewCountDesc(Pageable pageable);
}
