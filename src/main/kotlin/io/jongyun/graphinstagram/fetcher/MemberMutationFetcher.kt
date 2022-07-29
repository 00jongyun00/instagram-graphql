package io.jongyun.graphinstagram.fetcher

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.service.member.MemberService
import io.jongyun.graphinstagram.types.MemberRegisterInput

@DgsComponent
class MemberMutationFetcher(
    private val memberService: MemberService
) {

    @DgsData(parentType = DgsConstants.QUERY_TYPE, field = DgsConstants.QUERY.MemberRegister)
    fun createMember(@InputArgument memberRegisterInput: MemberRegisterInput): Boolean {
        return memberService.register(memberRegisterInput)
    }
}