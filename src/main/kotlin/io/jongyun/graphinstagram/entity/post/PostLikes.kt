package io.jongyun.graphinstagram.entity.post

import io.jongyun.graphinstagram.entity.common.BaseTimeEntity
import io.jongyun.graphinstagram.entity.member.Member
import javax.persistence.*

@Entity
@Table(name = "post_likes")
class PostLikes(
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "post_id")
    val post: Post,
    @ManyToOne(fetch = FetchType.LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "liked_by_id")
    val likedBy: Member
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set
}