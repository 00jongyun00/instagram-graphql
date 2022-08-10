package io.jongyun.graphinstagram.service.member

import io.jongyun.graphinstagram.entity.follow.FollowCustomRepository
import io.jongyun.graphinstagram.entity.follow.FollowRepository
import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.exception.ErrorCode
import io.jongyun.graphinstagram.service.post.generateMember
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import java.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FollowServiceTest : BehaviorSpec({
    val memberRepository = mockk<MemberRepository>()
    val followRepository = mockk<FollowRepository>()
    val followCustomRepository = mockk<FollowCustomRepository>()
    val followService = FollowService(memberRepository, followRepository, followCustomRepository)

    lateinit var follower: Member
    lateinit var followee: Member

    beforeTest {
        follower = generateMember()
        followee = generateMember()
    }

    afterTest {
        clearAllMocks()
    }

    Given("[follow] follower 와 followee set up 한다.") {
        val followerId = 1L
        val followeeId = 2L
        When("팔로워를 찾지 못한다.") {
            every { memberRepository.findById(followerId) } returns Optional.empty()
            Then("예외가 발생한다.") {
                val exception = shouldThrow<BusinessException> { followService.follow(followerId, followeeId) }
                exception.errorCode shouldBe ErrorCode.MEMBER_DOES_NOT_EXISTS
            }
        }
        When("followee 를 찾지 못한다.") {
            every { memberRepository.findById(followerId) } returns Optional.of(follower)
            every { memberRepository.findById(followeeId) } returns Optional.empty()
            Then("예외가 발생한다.") {
                val exception = shouldThrow<BusinessException> { followService.follow(followerId, followeeId) }
                exception.errorCode shouldBe ErrorCode.MEMBER_DOES_NOT_EXISTS
            }
        }
        When("이미 팔로우 한상태여서") {
            every { memberRepository.findById(followerId) } returns Optional.of(follower)
            every { memberRepository.findById(followeeId) } returns Optional.of(followee)
            every { followRepository.existsByFollowerAndFollowee(follower, followee) } returns true
            Then("예외가 발생한다.") {
                val exception = shouldThrow<BusinessException> { followService.follow(followerId, followeeId) }
                exception.errorCode shouldBe ErrorCode.ALREADY_FOLLOW
            }
        }
        When("모든 조건을 통과해서") {
            every { memberRepository.findById(followerId) } returns Optional.of(follower)
            every { memberRepository.findById(followeeId) } returns Optional.of(followee)
            every { followRepository.existsByFollowerAndFollowee(follower, followee) } returns false
            Then("통과한다.") {
                val result = withContext(Dispatchers.IO) {
                    followService.follow(followerId, followeeId)
                }
                result shouldBe true
            }
        }
    }
})