package io.jongyun.graphinstagram.datafetchers.post

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.service.post.PostService
import io.jongyun.graphinstagram.types.CreatePostInput
import io.jongyun.graphinstagram.types.UpdatePostInput

@DgsComponent
class PostMutationFetcher(
    private val postService: PostService
) {

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.PostCreate)
    fun createPost(@InputArgument createPostInput: CreatePostInput): Boolean {
        return postService.createPost(createPostInput)
    }

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.PostUpdate)
    fun updatePost(@InputArgument updatePostInput: UpdatePostInput): Boolean {
        return postService.updatePost(updatePostInput)
    }
}