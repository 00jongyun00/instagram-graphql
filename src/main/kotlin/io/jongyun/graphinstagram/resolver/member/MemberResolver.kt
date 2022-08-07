package io.jongyun.graphinstagram.resolver.member

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.service.member.MemberService
import io.jongyun.graphinstagram.types.Member
import io.jongyun.graphinstagram.util.getAuthName

@DgsComponent
class MemberResolver(
    private val memberService: MemberService
) {

    @DgsData(parentType = DgsConstants.QUERY.TYPE_NAME, field = DgsConstants.QUERY.Me)
    fun getMyInfo(memberId: Long): Member {
        return memberService.findMyInfo(getAuthName())
    }

}