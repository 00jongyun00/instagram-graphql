package io.jongyun.graphinstagram.fetcher.member

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.service.member.MemberAuthService
import io.jongyun.graphinstagram.service.member.MemberService
import io.jongyun.graphinstagram.types.MemberLoginInput
import io.jongyun.graphinstagram.types.MemberLoginResponse
import io.jongyun.graphinstagram.types.MemberRegisterInput

@DgsComponent
class MemberMutationFetcher(
    private val memberService: MemberService,
    private val memberAuthService: MemberAuthService
) {

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.MemberRegister)
    fun createMember(@InputArgument memberRegisterInput: MemberRegisterInput): Boolean {
        return memberService.register(memberRegisterInput)
    }

    @DgsData(parentType = DgsConstants.Mutation_TYPE, field = DgsConstants.MUTATION.MemberLogin)
    fun login(@InputArgument memberLoginInput: MemberLoginInput): MemberLoginResponse {
        val jwtToken = memberAuthService.login(memberLoginInput)
        return MemberLoginResponse(jwtToken)
    }
}