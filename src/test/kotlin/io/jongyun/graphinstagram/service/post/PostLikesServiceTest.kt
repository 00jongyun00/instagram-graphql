package io.jongyun.graphinstagram.service.post

import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.entity.post.Post
import io.jongyun.graphinstagram.entity.post.PostLikesRepository
import io.jongyun.graphinstagram.entity.post.PostRepository
import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.exception.ErrorCode
import io.jongyun.graphinstagram.types.LikePostInput
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*

internal class PostLikesServiceTest : BehaviorSpec({
    val postLikeRepository: PostLikesRepository = mockk()
    val memberRepository: MemberRepository = mockk()
    val postRepository: PostRepository = mockk()
    val postLikeService: PostLikesService = PostLikesService(postLikeRepository, postRepository, memberRepository)

    Given("LikePostInput") {
        val likePostInput = LikePostInput("1")
        val member = generateMember(1L)
        val post = generatePost(member, 1L)
        When("Member 를 찾을 수 없어서") {
            every { memberRepository.findById(1L) } returns Optional.empty()
            Then("예외가 발생한다.") {
                val exception = shouldThrow<BusinessException> { postLikeService.addLike(1L, likePostInput) }
                exception.errorCode shouldBe ErrorCode.MEMBER_DOES_NOT_EXISTS
            }
        }
        When("Post 를 찾을 수 없어서") {
            every { memberRepository.findById(1L) } returns Optional.of(member)
            every { postRepository.findById(1L) } returns Optional.empty()
            Then("예외가 발생한다.") {
                val exception = shouldThrow<BusinessException> { postLikeService.addLike(1L, likePostInput) }
                exception.errorCode shouldBe ErrorCode.POST_DOES_NOT_EXISTS
            }
        }
        When("이미 좋아요를 누른 게시글 이여서") {
            every { memberRepository.findById(1L) } returns Optional.of(member)
            every { postRepository.findById(1L) } returns Optional.of(post)
            every { postLikeRepository.existsByLikedByAndPost(member, post) } returns true
            Then("예외가 발생한다.") {
                val exception = shouldThrow<BusinessException> { postLikeService.addLike(1L, likePostInput) }
                exception.errorCode shouldBe ErrorCode.THIS_POST_HAS_ALREADY_BEEN_LIKED
            }
        }
        When("모두 통과해서") {
            every { memberRepository.findById(1L) } returns Optional.of(member)
            every { postRepository.findById(1L) } returns Optional.of(post)
            every { postLikeRepository.existsByLikedByAndPost(member, post) } returns false
            every { postRepository.save(post) } returns post
            Then("성공한다.") {
                val result = withContext(Dispatchers.IO) {
                    postLikeService.addLike(1L, likePostInput)
                }
                result shouldBe true
                post.postLikeList.size shouldBe 1
            }
        }
    }
})

fun generateMember(memberId: Long) = Member(name = "jongyun", password = "123456").apply { id = memberId }

fun generatePost(member: Member, postId: Long) = Post(content = "테스트 입니다.", createdBy = member).apply { id = postId }