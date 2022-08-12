package io.jongyun.graphinstagram.entity.post

import io.jongyun.graphinstagram.entity.common.BaseTimeEntity
import io.jongyun.graphinstagram.entity.member.Member
import javax.persistence.CascadeType
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.EAGER
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

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

    @ManyToOne(fetch = EAGER)
    @JoinColumn(name = "created_by_id")
    var createdBy: Member? = null
}