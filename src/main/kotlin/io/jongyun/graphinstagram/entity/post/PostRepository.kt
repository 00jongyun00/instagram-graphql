package io.jongyun.graphinstagram.entity.post

import io.jongyun.graphinstagram.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : JpaRepository<Post, Long> {
    fun findByCreatedByAndId(createdBy: Member, id: Long): Post?

    fun findByCreatedBy(createdBy: Member): List<Post>
}