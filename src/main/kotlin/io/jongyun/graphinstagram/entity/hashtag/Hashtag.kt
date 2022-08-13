package io.jongyun.graphinstagram.entity.hashtag

import java.time.OffsetDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Index
import javax.persistence.Table

@Entity
@Table(name = "hashtag", indexes = [Index(name = "i_hashtag", columnList = "tag_name", unique = true)])
class Hashtag(
    @Column(length = 10, unique = true, name = "tag_name", nullable = false)
    var tagName: String
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    val createdAt: OffsetDateTime = OffsetDateTime.now()
}