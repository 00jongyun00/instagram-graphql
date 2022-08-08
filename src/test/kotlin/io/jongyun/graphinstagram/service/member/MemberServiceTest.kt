package io.jongyun.graphinstagram.service.member

import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.member.MemberCustomRepository
import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.entity.post.Post
import io.jongyun.graphinstagram.entity.post.PostRepository
import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.exception.ErrorCode
import io.jongyun.graphinstagram.service.post.generateMember
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.matchers.shouldBe
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import java.util.*


const val REPEAT_COUNT = 10

class MemberServiceTest : BehaviorSpec({
    val memberRepository = mockk<MemberRepository>()
    val encoder = mockk<BCryptPasswordEncoder>()
    val postRepository: PostRepository = mockk()
    val memberCustomRepository: MemberCustomRepository = mockk()
    val memberService = MemberService(memberRepository, encoder, memberCustomRepository, postRepository)

    lateinit var member: Member
    lateinit var post: Post
    var likedMembersToPost: MutableList<Member> = mutableListOf()

    beforeTest {
        member = generateMember()
        post = Post(content = "테스트 입니다.", createdBy = member)
    }

    afterTest {
        clearAllMocks()
    }

    given("좋아요 누른 모든 회원 조회시 post id 를 1로 설정한다.") {
        val postId = 1L
        repeat(REPEAT_COUNT) {
            likedMembersToPost.add(generateMember())
        }
        When("post id 에 대한 post 를 찾을 수 없다") {
            every { postRepository.findById(postId) } returns Optional.empty()
            Then("post 를 찾지 못해 예외가 발생한다.") {
                val exception = shouldThrow<BusinessException> { memberService.findAllLikedMemberToPost(postId) }
                exception.errorCode shouldBe ErrorCode.POST_DOES_NOT_EXISTS
            }
        }
        When("모든 조건이 통과되어") {
            every { postRepository.findById(postId) } returns Optional.of(post)
            every { memberCustomRepository.findAllLikedMemberToPost(post) } returns likedMembersToPost
            Then("성공한다.") {
                val result = withContext(Dispatchers.IO) {
                    memberService.findAllLikedMemberToPost(postId)
                }
                result.count() shouldBeExactly REPEAT_COUNT
            }
        }
    }
})