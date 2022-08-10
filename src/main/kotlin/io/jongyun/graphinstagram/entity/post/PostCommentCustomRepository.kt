package io.jongyun.graphinstagram.entity.post

import com.querydsl.jpa.impl.JPAQueryFactory
import io.jongyun.graphinstagram.entity.post.QPostComment.postComment
import org.springframework.stereotype.Repository

@Repository
class PostCommentCustomRepository(
    private val queryFactory: JPAQueryFactory
) {

    fun findAllByPostIds(postIds: List<Long>): List<PostComment> {
        return queryFactory.selectFrom(postComment)
            .where(postComment.post.id.`in`(postIds))
            .fetch()
    }
}