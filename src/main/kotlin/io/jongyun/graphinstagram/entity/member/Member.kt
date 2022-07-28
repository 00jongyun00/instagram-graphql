package io.jongyun.graphinstagram.entity.member

import io.jongyun.graphinstagram.entity.common.BaseTimeEntity
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "member")
class Member(
    var username: String,
    var password: String
): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null
        protected set
}