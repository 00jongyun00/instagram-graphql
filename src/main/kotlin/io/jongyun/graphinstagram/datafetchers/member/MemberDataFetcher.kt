package io.jongyun.graphinstagram.datafetchers.member

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import com.netflix.graphql.dgs.InputArgument
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.dataloader.member.MembersDataLoader
import io.jongyun.graphinstagram.service.member.MemberAuthService
import io.jongyun.graphinstagram.service.member.MemberService
import io.jongyun.graphinstagram.types.*
import org.dataloader.DataLoader
import java.util.concurrent.CompletableFuture

@DgsComponent
class MemberDataFetcher(
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

    @DgsData(parentType = DgsConstants.QUERY.TYPE_NAME, field = DgsConstants.QUERY.LikedMembersToPost)
    fun getAllLikedMembersToPost(@InputArgument postId: Long): List<Member> {
        return memberService.findAllLikedMemberToPost(postId)
    }

    @DgsData(parentType = DgsConstants.POST.TYPE_NAME, field = DgsConstants.POST.CreatedBy)
    fun members(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<Member>> {
        // DataLoader 를 이름으로 로드하는 대신 DgsDataFetchingEnvironment 를 사용하고 DataLoader 클래스 이름을 전달할 수 있습니다.
        val membersDataLoader: DataLoader<Long, List<Member>> = dfe.getDataLoader(MembersDataLoader::class.java)

        // 리뷰 필드가 Show 에 있기 때문에 getSource() 메서드는 Show 인스턴스를 반환합니다.
        val post: Post = dfe.getSource()

        // DataLoader 에서 리뷰를 로드합니다. 이 호출은 비동기식이며 DataLoader 메커니즘에 의해 일괄 처리됩니다.
        return membersDataLoader.load(post.id.toLong())
    }
}