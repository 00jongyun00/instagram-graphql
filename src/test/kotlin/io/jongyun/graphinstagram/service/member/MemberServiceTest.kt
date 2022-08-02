package io.jongyun.graphinstagram.service.member

import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.types.MemberRegisterInput
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FeatureSpec
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

internal class MemberServiceTest : FeatureSpec({
    val memberRepository = mockk<MemberRepository>()
    val encoder = mockk<BCryptPasswordEncoder>()
    val memberService = MemberService(memberRepository, encoder)

    feature("registry member account") {
        val input = MemberRegisterInput("jongyun", "password")
        val member = Member("jongyun2", "password")

        scenario("registry member account fail") {
            every { memberRepository.existsByName(any()) } returns true
            shouldThrow<BusinessException> {
                memberService.register(input)
            }
        }

        scenario("registry member account success") {
            every { memberRepository.existsByName(any()) } returns false
            every { memberRepository.save(any()) } returns member

            val result = withContext(Dispatchers.IO) {
                memberService.register(input)
            }
            result shouldBe true
        }
    }
})