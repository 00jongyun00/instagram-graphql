package io.jongyun.graphinstagram.entity.post

import com.querydsl.jpa.impl.JPAQueryFactory
import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.post.QPost.post
import io.jongyun.graphinstagram.entity.post.QPostLikes.postLikes
import org.springframework.stereotype.Repository

@Repository
class PostCustomRepository(
    private val queryFactory: JPAQueryFactory
) {

    fun findAllMyLikedPostByMember(findMember: Member): List<Post> {
        return queryFactory.selectFrom(post)
            .innerJoin(postLikes).on(postLikes.post.eq(post), postLikes.likedBy.eq(findMember)).fetchJoin()
            .fetch()
    }
}