package io.jongyun.graphinstagram.entity.follow

import io.jongyun.graphinstagram.entity.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FollowRepository : JpaRepository<Follow, Long> {

    fun existsByFollowerAndFollowee(follower: Member, followee: Member): Boolean

    fun findByFollowerAndFollowee(follower: Member, followee: Member): Follow?
}