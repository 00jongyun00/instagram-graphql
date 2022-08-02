package io.jongyun.graphinstagram.datafetchers.post

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.service.post.PostService
import io.jongyun.graphinstagram.types.CreatePostInput
import io.jongyun.graphinstagram.types.Post
import io.jongyun.graphinstagram.types.UpdatePostInput
import kotlinx.coroutines.coroutineScope
import java.util.concurrent.CompletableFuture

@DgsComponent
class PostDataFetcher(
    private val postService: PostService
) {

    @DgsData(parentType = DgsConstants.POST.TYPE_NAME, field = DgsConstants.QUERY.GetPost)
    fun getPost(@InputArgument postId: Long): Post {
        return postService.getPost(postId)
    }

    @DgsQuery(field = DgsConstants.QUERY.MyPostList)
    fun getMyPosts(): CompletableFuture<List<Post>> {
        return CompletableFuture.supplyAsync { postService.getMyPosts() }
    }

    @DgsQuery
    suspend fun posts(): List<Post> = coroutineScope {
        postService.getAll()
    }

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.PostCreate)
    fun createPost(@InputArgument createPostInput: CreatePostInput): Boolean {
        return postService.createPost(createPostInput)
    }

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.PostUpdate)
    fun updatePost(@InputArgument updatePostInput: UpdatePostInput): Boolean {
        return postService.updatePost(updatePostInput)
    }
}