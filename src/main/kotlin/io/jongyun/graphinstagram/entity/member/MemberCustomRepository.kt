package io.jongyun.graphinstagram.entity.member

import com.querydsl.jpa.JPAExpressions.select
import io.jongyun.graphinstagram.entity.member.QMember.member
import io.jongyun.graphinstagram.entity.post.Post
import io.jongyun.graphinstagram.entity.post.QPost.post
import io.jongyun.graphinstagram.entity.post.QPostLikes.postLikes
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport
import org.springframework.stereotype.Repository

@Repository
class MemberCustomRepository : QuerydslRepositorySupport(Member::class.java) {

    fun findAllLikedMemberToPost(findPost: Post): List<Member> {
        return select(member)
            .from(post)
            .innerJoin(postLikes.post, post).fetchJoin()
            .innerJoin(postLikes.likedBy, member).fetchJoin()
            .fetch()
    }
}