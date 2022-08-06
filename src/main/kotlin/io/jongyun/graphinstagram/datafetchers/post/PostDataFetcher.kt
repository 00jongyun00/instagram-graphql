package io.jongyun.graphinstagram.datafetchers.post

import com.netflix.graphql.dgs.*
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.dataloader.post.PostDataLoader
import io.jongyun.graphinstagram.service.post.PostService
import io.jongyun.graphinstagram.types.CreatePostInput
import io.jongyun.graphinstagram.types.Member
import io.jongyun.graphinstagram.types.Post
import io.jongyun.graphinstagram.types.UpdatePostInput
import io.jongyun.graphinstagram.util.getAuthName
import org.dataloader.DataLoader
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
    fun posts(): List<Post> {
        return postService.getAll()
    }

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.PostCreate)
    fun createPost(@InputArgument createPostInput: CreatePostInput): Boolean {
        return postService.createPost(getAuthName(), createPostInput)
    }

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.PostUpdate)
    fun updatePost(@InputArgument updatePostInput: UpdatePostInput): Boolean {
        return postService.updatePost(getAuthName(), updatePostInput)
    }

    @DgsData(parentType = DgsConstants.MEMBER.TYPE_NAME, field = DgsConstants.MEMBER.Posts)
    fun posts(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<Post>> {
        val postsDataLoader: DataLoader<Long, List<Post>> = dfe.getDataLoader(PostDataLoader::class.java)
        val member: Member = dfe.getSource()
        return postsDataLoader.load(member.id.toLong())
    }
}