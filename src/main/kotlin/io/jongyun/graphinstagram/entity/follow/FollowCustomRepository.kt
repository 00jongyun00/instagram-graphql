package io.jongyun.graphinstagram.entity.follow

import com.querydsl.jpa.impl.JPAQueryFactory
import io.jongyun.graphinstagram.entity.follow.QFollow.follow
import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.member.QMember.member
import org.springframework.stereotype.Repository

@Repository
class FollowCustomRepository(
    private val queryFactory: JPAQueryFactory
) {

    fun findAllByMember(findMember: Member): List<Member> {
        return queryFactory.select(member)
            .from(follow)
            .innerJoin(follow).on(follow.followee.eq(findMember))
            .innerJoin(member).on(follow.follower.eq(member))
            .fetch()
    }
}