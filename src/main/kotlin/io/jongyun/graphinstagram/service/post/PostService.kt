package io.jongyun.graphinstagram.service.post

import io.jongyun.graphinstagram.entity.member.MemberRepository
import io.jongyun.graphinstagram.entity.post.Post
import io.jongyun.graphinstagram.entity.post.PostRepository
import io.jongyun.graphinstagram.exception.BusinessException
import io.jongyun.graphinstagram.exception.ErrorCode
import io.jongyun.graphinstagram.types.CreatePostInput
import io.jongyun.graphinstagram.types.UpdatePostInput
import io.jongyun.graphinstagram.util.getMemberByContext
import io.jongyun.graphinstagram.util.mapToGraphql
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.StringUtils
import io.jongyun.graphinstagram.types.Post as TypesPost

@Transactional
@Service
class PostService(
    private val postRepository: PostRepository,
    private val memberRepository: MemberRepository
) {

    fun createPost(createPostInput: CreatePostInput): Boolean {
        contentValidation(createPostInput.content)
        val post = Post(
            createdBy = getMemberByContext(memberRepository),
            content = createPostInput.content
        )
        postRepository.save(post)
        return true
    }


    @Transactional(readOnly = true)
    fun getPost(postId: Long): TypesPost {
        val post = postRepository.findById(postId).orElseThrow {
            BusinessException(
                ErrorCode.POST_DOES_NOT_EXISTS,
                "게시물을 찾을 수 없습니다. ID: $postId"
            )
        }
        return mapToGraphql(post)
    }

    @Transactional(readOnly = true)
    fun getAll(): List<TypesPost> {
        return postRepository.findAll().map { mapToGraphql(it) }
    }

    @Transactional(readOnly = true)
    fun getMyPosts(): List<TypesPost> {
        return postRepository.findByCreatedBy(getMemberByContext(memberRepository))
            .map { mapToGraphql(it) }
    }

    fun updatePost(updatePostInput: UpdatePostInput): Boolean {
        val post =
            postRepository.findByCreatedByAndId(getMemberByContext(memberRepository), updatePostInput.postId.toLong())
                ?: throw BusinessException(
                    ErrorCode.POST_DOES_NOT_EXISTS,
                    "게시물을 찾을 수 없습니다. ID: ${updatePostInput.postId}"
                )
        contentValidation(updatePostInput.content)
        post.content = updatePostInput.content
        postRepository.save(post)
        return true
    }

    private fun contentValidation(content: String) {
        when {
            !StringUtils.hasText(content) ->
                throw BusinessException(ErrorCode.POST_CONTENT_IS_REQUIRED, "게시물의 컨텐츠 내용은 필수입니다.")
            content.length > 100 ->
                throw BusinessException(ErrorCode.CONTENT_MUST_BE_100_LENGTH_OR_LESS, "컨텐츠 내용은 100자 이하여야 합니다.")
        }
    }

}
