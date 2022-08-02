package io.jongyun.graphinstagram.util

import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.post.Post
import io.jongyun.graphinstagram.types.Member as TypesMember

fun mapToGraphql(post: Post): io.jongyun.graphinstagram.types.Post {
    return io.jongyun.graphinstagram.types.Post(
        id = post.id.toString(),
        createdBy = mapToGraphql(post.createdBy),
        content = post.content,
        createdAt = post.createdAt,
        updatedAt = post.updatedAt
    )
}

fun mapToGraphql(member: Member): TypesMember {
    return TypesMember(
        id = member.id.toString(),
        name = member.name,
        createdAt = member.createdAt,
        updatedAt = member.updatedAt
    )
}

