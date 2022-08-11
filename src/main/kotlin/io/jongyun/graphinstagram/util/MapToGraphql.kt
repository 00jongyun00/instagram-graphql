package io.jongyun.graphinstagram.util

import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.post.Post
import io.jongyun.graphinstagram.entity.post.PostComment
import io.jongyun.graphinstagram.types.Member as TypesMember
import io.jongyun.graphinstagram.types.Post as TypesPost
import io.jongyun.graphinstagram.types.PostComment as TypesPostComment

fun mapToGraphql(post: Post): TypesPost {
    return TypesPost(
        id = post.id.toString(),
        createdBy = mapToGraphql(post.createdBy),
        content = post.content,
        createdAt = post.createdAt,
        updatedAt = post.updatedAt,
    )
}

fun mapToGraphql(member: Member): TypesMember {
    return TypesMember(
        id = member.id.toString(),
        name = member.name,
        createdAt = member.createdAt,
        updatedAt = member.updatedAt,
    )
}

fun mapToGraphql(postComment: PostComment): TypesPostComment {
    return TypesPostComment(
        id = postComment.id.toString(),
        createdBy = mapToGraphql(postComment.createdBy!!),
        content = postComment.content,
        createdAt = postComment.createdAt,
        updatedAt = postComment.updatedAt,
    )
}

