package io.jongyun.graphinstagram.util

import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.post.Post
import io.jongyun.graphinstagram.entity.post.PostComment
import java.time.ZoneOffset
import io.jongyun.graphinstagram.types.Member as TypesMember
import io.jongyun.graphinstagram.types.Post as TypesPost
import io.jongyun.graphinstagram.types.PostComment as TypesPostComment

val ZONE_OFFSET = ZoneOffset.ofHours(9)
fun mapToGraphql(post: Post): TypesPost {
    return TypesPost(
        id = post.id.toString(),
        createdBy = mapToGraphql(post.createdBy),
        content = post.content,
        createdAt = post.createdAt.atOffset(ZONE_OFFSET),
        updatedAt = post.updatedAt.atOffset(ZONE_OFFSET),
    )
}

fun mapToGraphql(member: Member): TypesMember {
    return TypesMember(
        id = member.id.toString(),
        name = member.name,
        createdAt = member.createdAt.atOffset(ZONE_OFFSET),
        updatedAt = member.updatedAt.atOffset(ZONE_OFFSET)
    )
}

fun mapToGraphql(postComment: PostComment): TypesPostComment {
    return TypesPostComment(
        id = postComment.id.toString(),
        createdBy = mapToGraphql(postComment.createdBy!!),
        content = postComment.content,
        createdAt = postComment.createdAt.atOffset(ZONE_OFFSET),
        updatedAt = postComment.updatedAt.atOffset(ZONE_OFFSET)
    )
}

