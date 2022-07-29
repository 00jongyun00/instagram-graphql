package io.jongyun.graphinstagram.service.member

import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.exception.ErrorCode
import io.jongyun.graphinstagram.types.MemberRegisterInput
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository
) {

    @Transactional
    fun register(memberRegisterInput: MemberRegisterInput): Boolean {
        if (memberRepository.existsByName(memberRegisterInput.name)) {
            throw BusinessException(
                ErrorCode.ID_IS_DUPLICATE,
                "[register] member name is already exists: ${memberRegisterInput.name}"
            )
        }
        val registryMember = mapToEntity(memberRegisterInput)
        memberRepository.save(registryMember)
        return true
    }
}

private fun mapToEntity(memberRegisterInput: MemberRegisterInput): Member {
    return Member(name = memberRegisterInput.name, password = memberRegisterInput.password)
}