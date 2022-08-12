package io.jongyun.graphinstagram.entity.hashtag

import io.jongyun.graphinstagram.entity.post.Post
import java.time.OffsetDateTime
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "post_hashtag")
class PostHashtag(
    @ManyToOne(fetch = LAZY, cascade = [CascadeType.REMOVE])
    val post: Post,
    @ManyToOne(fetch = LAZY, cascade = [CascadeType.REMOVE])
    val hashtag: Hashtag
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    val createdAt: OffsetDateTime = OffsetDateTime.now()
}