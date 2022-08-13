package io.jongyun.graphinstagram.entity.post

import com.querydsl.jpa.impl.JPAQueryFactory
import io.jongyun.graphinstagram.entity.hashtag.Hashtag
import io.jongyun.graphinstagram.entity.hashtag.QHashtag.hashtag
import io.jongyun.graphinstagram.entity.hashtag.QPostHashtag.postHashtag
import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.post.QPost.post
import io.jongyun.graphinstagram.entity.post.QPostLikes.postLikes
import io.jongyun.graphinstagram.types.PostPageInput
import org.springframework.stereotype.Repository

@Repository
class PostCustomRepository(
    private val queryFactory: JPAQueryFactory
) {

    fun findAllMyLikedPostByMember(findMember: Member): List<Post> {
        return queryFactory.selectFrom(post)
            .innerJoin(postLikes).on(postLikes.post.eq(post), postLikes.likedBy.eq(findMember)).fetchJoin()
            .fetch()
    }

    fun findAllByHashtag(findHashtag: Hashtag, postPageInput: PostPageInput): List<Post> {
        return queryFactory.selectFrom(post)
            .innerJoin(postHashtag).on(postHashtag.post.eq(post)).fetchJoin()
            .innerJoin(hashtag).on(postHashtag.hashtag.eq(hashtag)).fetchJoin()
            .where(hashtag.eq(findHashtag))
            .limit(postPageInput.page_size.toLong())
            .offset(postPageInput.page.toLong())
            .fetch()
    }
}