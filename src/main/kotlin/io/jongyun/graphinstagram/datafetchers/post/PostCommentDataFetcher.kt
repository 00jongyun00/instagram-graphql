package io.jongyun.graphinstagram.datafetchers.post

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.dataloader.post.PostCommentDataLoader
import io.jongyun.graphinstagram.types.Post
import io.jongyun.graphinstagram.types.PostComment
import java.util.concurrent.CompletableFuture
import org.dataloader.DataLoader

@DgsComponent
class PostCommentDataFetcher {

    @DgsData(parentType = DgsConstants.POST.TYPE_NAME, field = DgsConstants.POST.PostComments)
    fun postComments(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<PostComment>> {
        val postCommentDataLoader: DataLoader<Long, List<PostComment>> =
            dfe.getDataLoader(PostCommentDataLoader::class.java)

        val post: Post = dfe.getSource()
        return postCommentDataLoader.load(post.id!!.toLong());
    }
}