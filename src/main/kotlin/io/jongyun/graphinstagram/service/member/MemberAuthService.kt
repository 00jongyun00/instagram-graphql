package io.jongyun.graphinstagram.service.member

import io.jongyun.graphinstagram.configuration.security.JwtTokenProvider
import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.exception.ErrorCode
import io.jongyun.graphinstagram.types.MemberLoginInput
import io.jongyun.graphinstagram.util.getMemberByContext
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import io.jongyun.graphinstagram.types.Member as TypesMember

@Service
class MemberAuthService(
    private val memberRepository: MemberRepository,
    private val authenticationManagerBuilder: AuthenticationManagerBuilder,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {

    @Transactional(readOnly = true)
    fun login(memberLoginInput: MemberLoginInput): String {
        try {
            val authenticationToken =
                UsernamePasswordAuthenticationToken(memberLoginInput.name, memberLoginInput.password)

            val authentication: Authentication = authenticationManagerBuilder.getObject()
                .authenticate(authenticationToken)
            return jwtTokenProvider.createToken(authentication)
        } catch (e: BadCredentialsException) {
            throw BusinessException(ErrorCode.CHECK_YOUR_ACCOUNT, "비밀번호가 틀렸습니다.")
        }
    }


    @Transactional(readOnly = true)
    fun findMyInfo(): TypesMember {
        val member = getMemberByContext(memberRepository)
        return TypesMember(member.id.toString(), member.name, member.createdAt, member.updatedAt)
    }


}