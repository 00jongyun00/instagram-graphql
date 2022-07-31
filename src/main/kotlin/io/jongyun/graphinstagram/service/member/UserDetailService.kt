package io.jongyun.graphinstagram.service.member

import io.jongyun.graphinstagram.entity.member.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailService(private val memberRepository: MemberRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val member = memberRepository.findByName(username) ?: throw RuntimeException()
        return UserDetailsImpl(member)
    }
}