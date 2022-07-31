package io.jongyun.graphinstagram.service.member

import io.jongyun.graphinstagram.entity.member.MemberRepository
import org.springframework.stereotype.Service

@Service
class MemberAuthService(
    private val memberRepository: MemberRepository
) {
}