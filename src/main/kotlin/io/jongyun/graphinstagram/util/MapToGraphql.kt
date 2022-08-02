package io.jongyun.graphinstagram.util

import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.post.Post
import java.time.ZoneOffset
import io.jongyun.graphinstagram.types.Member as TypesMember

val ZONE_OFFSET = ZoneOffset.ofHours(9)
fun mapToGraphql(post: Post): io.jongyun.graphinstagram.types.Post {
    return io.jongyun.graphinstagram.types.Post(
        id = post.id.toString(),
        createdBy = mapToGraphql(post.createdBy),
        content = post.content,
        createdAt = post.createdAt.atOffset(ZONE_OFFSET),
        updatedAt = post.updatedAt.atOffset(ZONE_OFFSET)
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

