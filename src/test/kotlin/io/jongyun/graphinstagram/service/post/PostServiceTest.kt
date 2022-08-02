package io.jongyun.graphinstagram.service.post

import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.entity.post.PostRepository
import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.types.CreatePostInput
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.mockk


internal class PostServiceTest : BehaviorSpec({
    val postRepository: PostRepository = mockk()
    val memberRepository: MemberRepository = mockk()
    val postService = PostService(postRepository, memberRepository)

    given("post content 가 비어있어서") {

        val createPostInput = CreatePostInput(content = "")
        Then("예외를 반환한다.") {
            shouldThrow<BusinessException> { postService.createPost(createPostInput) }
        }
    }
})