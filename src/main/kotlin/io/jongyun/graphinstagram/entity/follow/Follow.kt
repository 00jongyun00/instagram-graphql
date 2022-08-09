package io.jongyun.graphinstagram.entity.follow

import io.jongyun.graphinstagram.entity.common.BaseTimeEntity
import io.jongyun.graphinstagram.entity.member.Member
import javax.persistence.CascadeType
import javax.persistence.Entity
import javax.persistence.FetchType.LAZY
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "follow")
class Follow(
    @ManyToOne(fetch = LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "follower_id")
    val follower: Member,
    @ManyToOne(fetch = LAZY, cascade = [CascadeType.REMOVE])
    @JoinColumn(name = "followee_id")
    val followee: Member
) : BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

}