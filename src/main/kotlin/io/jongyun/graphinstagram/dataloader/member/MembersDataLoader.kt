package io.jongyun.graphinstagram.dataloader.member

import com.netflix.graphql.dgs.DgsDataLoader
import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.toFuture
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import kotlin.streams.toList
import org.dataloader.MappedBatchLoader
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@DgsDataLoader(name = "members", maxBatchSize = 100)
class MembersDataLoader(private val memberLoader: MemberLoader) : MappedBatchLoader<Long, List<Member>> {
    override fun load(keys: Set<Long>?): CompletionStage<Map<Long, List<Member>>> {
        return if (keys != null) {
            CompletableFuture.supplyAsync { memberLoader.find(keys.stream().toList()) }
        } else {
            emptyMap<Long, List<Member>>().toFuture()
        }
    }
}

@Service
class MemberLoader(private val repository: MemberRepository) {

    @Transactional(readOnly = true)
    fun find(ids: List<Long>): Map<Long, List<Member>> {
        return repository.findAllByIdIn(ids).groupBy { it.id!! }
    }
}