package io.jongyun.graphinstagram.entity.member

import io.jongyun.graphinstagram.entity.common.BaseTimeEntity
import io.jongyun.graphinstagram.entity.follow.Follow
import io.jongyun.graphinstagram.entity.post.Post
import javax.persistence.CascadeType.ALL
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

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

    @OneToMany(mappedBy = "followee", cascade = [ALL], orphanRemoval = true)
    val followers: MutableList<Follow> = mutableListOf()

    fun follow(follower: Member) {
        followers.add(Follow(follower, this))
    }
}