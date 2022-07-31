package io.jongyun.graphinstagram.resolver.member

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.service.member.MemberAuthService
import io.jongyun.graphinstagram.types.Member

@DgsComponent
class MemberResolver(
    private val memberAuthService: MemberAuthService
) {

    @DgsData(parentType = DgsConstants.QUERY.TYPE_NAME, field = DgsConstants.QUERY.Me)
    fun getMyInfo(): Member {
        return memberAuthService.findMyInfo()
    }

}