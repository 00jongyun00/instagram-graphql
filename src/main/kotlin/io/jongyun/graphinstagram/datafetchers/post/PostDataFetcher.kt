package io.jongyun.graphinstagram.datafetchers.post

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.dataloader.post.PostDataLoader
import io.jongyun.graphinstagram.service.post.PostService
import io.jongyun.graphinstagram.types.CreatePostInput
import io.jongyun.graphinstagram.types.Member
import io.jongyun.graphinstagram.types.Post
import io.jongyun.graphinstagram.types.UpdatePostInput
import io.jongyun.graphinstagram.util.getAuthName
import io.jongyun.graphinstagram.util.mapToGraphql
import java.util.concurrent.CompletableFuture
import org.dataloader.DataLoader

@DgsComponent
class PostDataFetcher(
    private val postService: PostService
) {

    @DgsData(parentType = DgsConstants.QUERY.TYPE_NAME, field = DgsConstants.QUERY.GetPost)
    fun getPost(@InputArgument postId: Long): Post {
        return postService.getPost(postId)
    }

    @DgsQuery
    fun posts(): List<Post> {
        return postService.getAll()
    }

    @DgsQuery(field = DgsConstants.QUERY.MyPostList)
    fun getMyPost(): List<Post> {
        return postService.getMyPost(getAuthName()).map { mapToGraphql(it) }
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
        // DataLoader 를 이름으로 로드하는 대신 DgsDataFetchingEnvironment 를 사용하고 DataLoader 클래스 이름을 전달할 수 있습니다.
        val postsDataLoader: DataLoader<Long, List<Post>> = dfe.getDataLoader(PostDataLoader::class.java)

        val member: Member = dfe.getSource()
        return postsDataLoader.load(member.id.toLong())
    }
}