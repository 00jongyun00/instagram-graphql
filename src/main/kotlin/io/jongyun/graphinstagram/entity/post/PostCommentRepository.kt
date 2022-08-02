package io.jongyun.graphinstagram.entity.post

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PostCommentRepository : JpaRepository<PostComment, Long> {
}