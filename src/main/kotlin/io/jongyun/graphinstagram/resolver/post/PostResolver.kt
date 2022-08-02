package io.jongyun.graphinstagram.resolver.post

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.service.post.PostService
import io.jongyun.graphinstagram.types.Post

@DgsComponent
class PostResolver(
    private val postService: PostService
) {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.GetPost)
    fun getPost(@InputArgument postId: Long): Post {
        return postService.getPost(postId)
    }
}