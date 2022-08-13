package io.jongyun.graphinstagram.entity.post

import io.jongyun.graphinstagram.entity.common.BaseTimeEntity
import io.jongyun.graphinstagram.entity.hashtag.Hashtag
import io.jongyun.graphinstagram.entity.hashtag.PostHashtag
import io.jongyun.graphinstagram.entity.member.Member
import javax.persistence.CascadeType.ALL
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.OneToMany
import javax.persistence.Table

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

    @OneToMany(mappedBy = "post", cascade = [ALL], orphanRemoval = true)
    val postLikeList: MutableList<PostLikes> = mutableListOf()

    @OneToMany(mappedBy = "post", cascade = [ALL], orphanRemoval = true)
    val postHashtagList: MutableSet<PostHashtag> = mutableSetOf()

    fun addLike(member: Member): PostLikes {
        val postLikes = PostLikes(this, member)
        postLikeList.add(postLikes)
        return postLikes
    }

    fun likeCancel(postLikes: PostLikes) {
        postLikeList.remove(postLikes)
    }

    fun addAllHashTag(hashtagList: List<Hashtag>) {
        hashtagList.forEach { this.addHashTag(it) }
    }

    fun addHashTag(hashtag: Hashtag) {
        postHashtagList.add(PostHashtag(post = this, hashtag = hashtag))
    }
}