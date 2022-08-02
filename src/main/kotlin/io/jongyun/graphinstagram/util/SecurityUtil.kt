package io.jongyun.graphinstagram.util

import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.exception.ErrorCode
import org.springframework.security.core.context.SecurityContextHolder

fun getMemberByContext(memberRepository: MemberRepository): Member {
    val name = SecurityContextHolder.getContext().authentication.name
    return memberRepository.findByName(name)
        ?: throw BusinessException(
            ErrorCode.MEMBER_DOES_NOT_EXISTS,
            "계정을 찾을수 없습니다. name = $"
        )
}