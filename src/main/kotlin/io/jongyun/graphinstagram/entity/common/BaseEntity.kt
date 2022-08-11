package io.jongyun.graphinstagram.entity.common

import java.time.OffsetDateTime
import javax.persistence.Column
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseTimeEntity {
    @CreatedDate
    @Column(nullable = false, updatable = false)
    var createdAt: OffsetDateTime = OffsetDateTime.now()
        protected set

    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: OffsetDateTime = OffsetDateTime.now()
        protected set
}