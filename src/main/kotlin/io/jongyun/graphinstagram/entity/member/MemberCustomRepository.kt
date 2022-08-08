package io.jongyun.graphinstagram.entity.member

import com.querydsl.jpa.impl.JPAQueryFactory
import io.jongyun.graphinstagram.entity.member.QMember.member
import io.jongyun.graphinstagram.entity.post.Post
import io.jongyun.graphinstagram.entity.post.QPost.post
import io.jongyun.graphinstagram.entity.post.QPostLikes.postLikes
import org.springframework.stereotype.Repository

@Repository
class MemberCustomRepository(
    private val queryFactory: JPAQueryFactory
) {

    fun findAllLikedMemberToPost(findPost: Post): MutableList<Member> {
        return queryFactory.select(member)
            .from(postLikes)
            .innerJoin(postLikes.post, post)
            .innerJoin(postLikes.likedBy, member)
            .where(post.eq(findPost))
            .fetch()
    }
}