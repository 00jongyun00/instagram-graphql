package io.jongyun.graphinstagram.service.member

import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.types.MemberCreateInput
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun createMember(memberCreateInput: MemberCreateInput): Boolean {
        val createMember = mapToEntity(memberCreateInput)
        memberRepository.save(createMember)
        return true
    }
}

private fun mapToEntity(memberCreateInput: MemberCreateInput): Member {
    return Member(name = memberCreateInput.name, password = memberCreateInput.password)
}