package io.jongyun.graphinstagram.resolver.post

import com.netflix.graphql.dgs.DgsComponent
import io.jongyun.graphinstagram.service.post.PostService

@DgsComponent
class PostResolver(
    private val postService: PostService
) {


}