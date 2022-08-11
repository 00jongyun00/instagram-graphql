package io.jongyun.graphinstagram.datafetchers.post

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.service.post.PostService
import io.jongyun.graphinstagram.types.Post
import io.jongyun.graphinstagram.util.getAuthName

@DgsComponent
class PostLikeDataFetcher(
    private val postService: PostService
) {

    @DgsQuery(field = DgsConstants.QUERY.MyLikedPostList)
    fun myLikedPostList(): List<Post> {
        return postService.getAllMyLikedPostByMemberId(getAuthName())
    }
}