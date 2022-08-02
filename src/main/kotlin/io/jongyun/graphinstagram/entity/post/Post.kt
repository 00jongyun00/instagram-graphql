package io.jongyun.graphinstagram.entity.post

import io.jongyun.graphinstagram.entity.common.BaseTimeEntity
import io.jongyun.graphinstagram.entity.member.Member
import javax.persistence.*
import javax.persistence.FetchType.LAZY

@Entity
@Table(name = "post")
class Post(
    @Column(name = "content", length = 100)
    var content: String,
    @ManyToOne(fetch = LAZY) @JoinColumn(name = "created_by_id")
    val createdBy: Member
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
        protected set
}