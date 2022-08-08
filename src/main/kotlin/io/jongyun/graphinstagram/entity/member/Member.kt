package io.jongyun.graphinstagram.entity.member

import io.jongyun.graphinstagram.entity.common.BaseTimeEntity
import io.jongyun.graphinstagram.entity.post.Post
import javax.persistence.*
import javax.persistence.CascadeType.ALL

@Entity
@Table(name = "member")
class Member(
    var name: String,
    var password: String
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @OneToMany(mappedBy = "createdBy", cascade = [ALL], orphanRemoval = true)
    val posts: MutableList<Post> = mutableListOf()
}