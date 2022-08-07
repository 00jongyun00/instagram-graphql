package io.jongyun.graphinstagram.datafetchers.post

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.service.post.PostLikesService
import io.jongyun.graphinstagram.types.LikeCancelPostInput
import io.jongyun.graphinstagram.types.LikePostInput
import io.jongyun.graphinstagram.util.getAuthName

@DgsComponent
class PostLikeMutationFetcher(
    private val postLikesService: PostLikesService
) {

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.PostLike)
    fun addLike(@InputArgument likePostInput: LikePostInput): Boolean {
        return postLikesService.addLike(getAuthName(), likePostInput)
    }

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.PostLikeCancel)
    fun likeCancel(@InputArgument likeCancelPostInput: LikeCancelPostInput): Boolean {
        return postLikesService.likeCancel(getAuthName(), likeCancelPostInput)
    }
}
