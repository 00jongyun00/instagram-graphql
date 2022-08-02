package io.jongyun.graphinstagram.entity.post

import io.jongyun.graphinstagram.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository

interface PostLikesRepository : JpaRepository<PostLikes, Long> {

    fun existsByLikedByAndPost(likedBy: Member, post: Post): Boolean
}