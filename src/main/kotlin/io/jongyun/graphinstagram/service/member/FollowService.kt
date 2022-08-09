package io.jongyun.graphinstagram.service.member

import io.jongyun.graphinstagram.entity.follow.FollowRepository
import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.exception.ErrorCode
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class FollowService(
    private val memberRepository: MemberRepository,
    private val followRepository: FollowRepository
) {

    fun follow(followerId: Long, followeeId: Long): Boolean {
        val follower = findMemberById(followerId)
        val followee = findMemberById(followeeId)
        val alreadyFollow = followRepository.existsByFollowerAndFollowee(follower, followee)
        if (alreadyFollow) {
            throw BusinessException(ErrorCode.ALREADY_FOLLOW, "이미 팔로우된 상태 입니다.")
        }
        followee.follow(follower)
        return true
    }

    fun unfollow(followerId: Long, followeeId: Long): Boolean {
        val follower = findMemberById(followerId)
        val followee = findMemberById(followeeId)
        val follow = followRepository.findByFollowerAndFollowee(follower, followee)
            ?: throw BusinessException(ErrorCode.DID_NOT_FOLLOW, "팔로우 하지 않은 회원입니다.")
        followee.unfollow(follow)
        return true
    }

    private fun findMemberById(memberId: Long): Member {
        val member = memberRepository.findById(memberId).orElseThrow {
            BusinessException(ErrorCode.MEMBER_DOES_NOT_EXISTS, "계정을 찾을 수 없습니다.")
        }
        return member
    }
}