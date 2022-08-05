package io.jongyun.graphinstagram.dataloader.post

import com.netflix.graphql.dgs.DgsDataLoader
import io.jongyun.graphinstagram.entity.post.Post
import io.jongyun.graphinstagram.entity.post.PostRepository
import io.jongyun.graphinstagram.toFuture
import org.dataloader.MappedBatchLoader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import kotlin.streams.toList

@DgsDataLoader(name = "posts", maxBatchSize = 5)
class PostDataLoader(private val loader: PostLoader) : MappedBatchLoader<Long, List<Post>> {
    override fun load(keys: Set<Long>?): CompletionStage<Map<Long, List<Post>>> {
        return if (keys != null) {
            CompletableFuture.supplyAsync { loader.find(keys.stream().toList()) }
        } else {
            emptyMap<Long, List<Post>>().toFuture()
        }
    }
}

@Service
class PostLoader(private val repository: PostRepository) {

    @Transactional(readOnly = true)
    fun find(ids: List<Long>): Map<Long, List<Post>> {
        return repository.findByIdIn(ids).groupBy { it.id!! }
    }
}