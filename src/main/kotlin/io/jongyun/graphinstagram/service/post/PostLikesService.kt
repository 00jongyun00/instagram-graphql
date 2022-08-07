package io.jongyun.graphinstagram.service.post

import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.entity.post.PostLikesRepository
import io.jongyun.graphinstagram.entity.post.PostRepository
import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.exception.ErrorCode
import io.jongyun.graphinstagram.types.LikePostInput
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class PostLikesService(
    private val postLikesRepository: PostLikesRepository,
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {

    fun addLike(memberId: Long, likePostInput: LikePostInput): Boolean {
        val member = findMemberById(memberId)
        val post = postRepository.findById(likePostInput.postId.toLong()).orElseThrow {
            BusinessException(
                ErrorCode.POST_DOES_NOT_EXISTS,
                "게시물을 찾을 수 없습니다. ID: ${likePostInput.postId}"
            )
        }
        if (postLikesRepository.existsByLikedByAndPost(member, post)) {
            throw BusinessException(ErrorCode.THIS_POST_HAS_ALREADY_BEEN_LIKED, "이미 좋아요누른 게시글 입니다. POST ID: ${post.id}")
        }
        post.addLike(member)
        postRepository.save(post)
        return true
    }

    private fun findMemberById(memberId: Long): Member {
        val member = memberRepository.findById(memberId).orElseThrow {
            BusinessException(ErrorCode.MEMBER_DOES_NOT_EXISTS, "계정을 찾을 수 없습니다.")
        }
        return member
    }
}