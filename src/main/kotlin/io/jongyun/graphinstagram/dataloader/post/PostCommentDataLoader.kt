package io.jongyun.graphinstagram.dataloader.post

import com.netflix.graphql.dgs.DgsDataLoader
import io.jongyun.graphinstagram.entity.post.PostComment
import io.jongyun.graphinstagram.entity.post.PostCommentCustomRepository
import io.jongyun.graphinstagram.toFuture
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import kotlin.streams.toList
import org.dataloader.MappedBatchLoader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@DgsDataLoader(name = "postComments", maxBatchSize = 100)
class PostCommentDataLoader(private val loader: PostCommentLoader) : MappedBatchLoader<Long, List<PostComment>> {

    override fun load(keys: Set<Long>?): CompletionStage<Map<Long, List<PostComment>>> {
        return if (keys != null) {
            CompletableFuture.supplyAsync { loader.find(keys.stream().toList()) }
        } else {
            emptyMap<Long, List<PostComment>>().toFuture()
        }
    }
}

@Service
class PostCommentLoader(private val repository: PostCommentCustomRepository) {

    @Transactional(readOnly = true)
    fun find(ids: List<Long>): Map<Long, List<PostComment>> {
        return repository.findAllByPostIds(ids).groupBy { it.post!!.id!! }
    }
}