package io.jongyun.graphinstagram.service.post

import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.entity.post.Post
import io.jongyun.graphinstagram.entity.post.PostRepository
import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.exception.ErrorCode
import io.jongyun.graphinstagram.types.CreatePostInput
import io.jongyun.graphinstagram.types.UpdatePostInput
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import java.util.*


internal class PostServiceTest : BehaviorSpec({
    val postRepository: PostRepository = mockk()
    val memberRepository: MemberRepository = mockk()
    val postService = PostService(postRepository, memberRepository)
    lateinit var member: Member
    lateinit var post: Post
    lateinit var updatePostInput: UpdatePostInput

    beforeTest {
        member = Member(
            name = "jongyun", password = "123456"
        )
        post = Post(
            content = "테스트 입니다.", createdBy = member
        )
        updatePostInput = UpdatePostInput(
            "1",
            "업데이트 테스트"
        )
    }

    afterTest {
        clearAllMocks()
    }

    given("post content 가 비어있어서") {
        val createPostInput = CreatePostInput(content = "")
        Then("예외를 반환한다.") {
            shouldThrow<BusinessException> { postService.createPost(1L, createPostInput) }
        }
    }

    given("post content 가 있어서") {
        val createPostInput = CreatePostInput(content = "테스트 컨텐츠")
        Then("성공한다.") {
            every { memberRepository.findById(1L) } returns Optional.of(member)
            every { postRepository.save(any()) } returns post
            postService.createPost(1L, createPostInput) shouldBe true
        }
    }

    given("post 의 content 업데이트 시 member 의 ID 를 1로 설정한다.") {
        val memberId = 1L
        `when`("member id 1에 대한 member 를 찾을 수 없다.") {
            every { memberRepository.findById(memberId) } returns Optional.empty()
            Then("member 를 찾지 못해 예외가 발생한다.") {
                shouldThrow<BusinessException> { postService.updatePost(memberId, updatePostInput) }
            }
        }
        `when`("post id 에 대한 post 를 찾을 수 없다.") {
            every { memberRepository.findById(memberId) } returns Optional.of(member)
            every { postRepository.findByCreatedByAndId(member, updatePostInput.postId.toLong()) } returns null
            Then("post 를 찾지 못해 예외가 발생한다.") {
                shouldThrow<BusinessException> { postService.updatePost(memberId, updatePostInput) }
            }
        }
        `when`("member 와 post 모두 정상적으로 조회") {
            every { memberRepository.findById(memberId) } returns Optional.of(member)
            every { postRepository.findByCreatedByAndId(member, updatePostInput.postId.toLong()) } returns post
            every { postRepository.save(post) } returns post
            Then("정상적으로 업데이트 된다.") {
                postService.updatePost(memberId, updatePostInput) shouldBe true
            }
        }
    }

    given("좋아요 누른 모든 회원 조회시 post id 를 1로 설정한다.") {
        val postId = 1L
        When("post id 에 대한 post 를 찾을 수 없다") {
            every { postRepository.findById(postId) } returns Optional.empty()
            Then("post 를 찾지 못해 예외가 발생한다.") {
                val exception = shouldThrow<BusinessException> { postService.getAllLikedMemberToPost(postId) }
                exception.errorCode shouldBe ErrorCode.POST_DOES_NOT_EXISTS
            }
        }
    }
})