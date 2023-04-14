package com.zadanie.socialnetwork.post;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Post not found")
class SocialNetworkPostNotFoundException extends RuntimeException {

}
