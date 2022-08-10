package io.jongyun.graphinstagram.datafetchers.member

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.InputArgument
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.DgsConstants.QUERY
import io.jongyun.graphinstagram.service.member.FollowService
import io.jongyun.graphinstagram.service.member.MemberAuthService
import io.jongyun.graphinstagram.service.member.MemberService
import io.jongyun.graphinstagram.types.Member
import io.jongyun.graphinstagram.types.MemberLoginInput
import io.jongyun.graphinstagram.types.MemberLoginResponse
import io.jongyun.graphinstagram.types.MemberRegisterInput
import io.jongyun.graphinstagram.util.getAuthName
import io.jongyun.graphinstagram.util.mapToGraphql

@DgsComponent
class MemberDataFetcher(
    private val memberService: MemberService,
    private val memberAuthService: MemberAuthService,
    private val followService: FollowService
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

    @DgsData(parentType = QUERY.TYPE_NAME, field = QUERY.LikedMembersToPost)
    fun getAllLikedMembersToPost(@InputArgument postId: Long): List<Member> {
        return memberService.findAllLikedMemberToPost(postId)
    }

    @DgsData(parentType = QUERY.TYPE_NAME, field = QUERY.MyFollowers)
    fun getAllMyFollowers(): List<Member> {
        return followService.getFollower(getAuthName()).map { mapToGraphql(it) }
    }
}