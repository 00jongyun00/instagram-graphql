package io.jongyun.graphinstagram.entity.hashtag

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HashtagRepository : JpaRepository<Hashtag, Long> {

    fun findAllByTagNameIn(tagNames: List<String>): List<Hashtag>

    fun findByTagName(tagName: String): Hashtag?
}