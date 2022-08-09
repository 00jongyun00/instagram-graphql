package io.jongyun.graphinstagram.datafetchers.member

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsMutation
import com.netflix.graphql.dgs.InputArgument
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.service.member.FollowService
import io.jongyun.graphinstagram.util.getAuthName

@DgsComponent
class FollowMutationFetcher(
    private val followService: FollowService
) {

    @DgsMutation(field = DgsConstants.MUTATION.Follow)
    fun follow(@InputArgument followeeId: Long): Boolean {
        return followService.follow(getAuthName(), followeeId)
    }

    @DgsMutation(field = DgsConstants.MUTATION.UnFollow)
    fun unfollow(@InputArgument followeeId: Long): Boolean {
        return followService.unfollow(getAuthName(), followeeId)
    }
}