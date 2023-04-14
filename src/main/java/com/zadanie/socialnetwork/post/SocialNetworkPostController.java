package com.zadanie.socialnetwork.post;

import io.swagger.annotations.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/post")
@Api(value = "Post Management Api Controller", tags = {"Post Management"})
class SocialNetworkPostController {

    private final SocialNetworkPostService socialNetworkPostService;

    SocialNetworkPostController(SocialNetworkPostService socialNetworkPostService) {
        this.socialNetworkPostService = socialNetworkPostService;
    }

    @PostMapping
    @ApiOperation(value = "Create a new post and return its id")
    ResponseEntity<Long> createPost(@RequestBody @ApiParam(value = "Post to be created") CreateSocialNetworkPostRequest socialNetworkPostDTO) {
        return ResponseEntity.ok(socialNetworkPostService.createPost(socialNetworkPostDTO));
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Get post by ID", response = SocialNetworkPostDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post found"),
            @ApiResponse(code = 404, message = "Post not found")
    })
    ResponseEntity<SocialNetworkPostDTO> getPost(@PathVariable @ApiParam(value = "ID of the post to be retrieved") Long id) {
        return ResponseEntity.ok(socialNetworkPostService.getPost(id));
    }

    @GetMapping
    @ApiOperation(value = "Get pages of posts ", response = Page.class)
    ResponseEntity<Page<SocialNetworkPostDTO>> getPosts(Pageable pageable) {
        return ResponseEntity.ok(socialNetworkPostService.getPageOfPosts(pageable));
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Update post by ID", response = SocialNetworkPostDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post updated"),
            @ApiResponse(code = 404, message = "Post not found")
    })
    ResponseEntity<SocialNetworkPostDTO> updatePost(@PathVariable @ApiParam(value = "ID of the post to be updated") Long id,
                                                    @RequestBody @ApiParam(value = "Updated post request") UpdateSocialNetworkPostRequest request) {
        return ResponseEntity.ok(socialNetworkPostService.updatePost(id, request));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Delete post by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Post deleted"),
            @ApiResponse(code = 404, message = "Post not found")
    })
    ResponseEntity<Void> deletePost(@PathVariable @ApiParam(value = "ID of the post to be deleted") Long id) {
        socialNetworkPostService.deletePost(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/top")
    @ApiOperation(value = "Get top 10 highest post by ID")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Retrieved post with top ten highest view count")
    })
    ResponseEntity<List<SocialNetworkPostDTO>> retrievePostWithTopTenHighestViewCount() {
        return ResponseEntity.ok(socialNetworkPostService.retrievePostWithTopTenHighestViewCount());
    }
}
