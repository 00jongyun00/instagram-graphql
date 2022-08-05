package io.jongyun.graphinstagram.service.post

import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.entity.post.Post
import io.jongyun.graphinstagram.entity.post.PostRepository
import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.types.CreatePostInput
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import java.util.*


internal class PostServiceTest : BehaviorSpec({
    val postRepository: PostRepository = mockk()
    val memberRepository: MemberRepository = mockk()
    val postService = PostService(postRepository, memberRepository)
    lateinit var member: Member
    lateinit var post: Post

    beforeTest {
        member = Member(
            name = "jongyun", password = "123456"
        )
        post = Post(
            content = "테스트 입니다.", createdBy = member
        )
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
})