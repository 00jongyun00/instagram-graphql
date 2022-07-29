package io.jongyun.graphinstagram.entity.post

import io.jongyun.graphinstagram.entity.common.BaseTimeEntity
import io.jongyun.graphinstagram.entity.member.Member
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
@Table(name = "post_comment")
class PostComment(
    @Column(name = "content", length = 50)
    var content: String,
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set

    @ManyToOne(fetch = LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "post_id")
    var post: Post? = null

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "created_by_id")
    var createdBy: Member? = null
}