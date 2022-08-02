/*
 * Copyright 2021 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.jongyun.graphinstagram.service.example

import com.github.javafaker.Faker
import io.jongyun.graphinstagram.types.Review
import org.reactivestreams.Publisher
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import reactor.core.publisher.ConnectableFlux
import reactor.core.publisher.Flux
import reactor.core.publisher.FluxSink
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit
import java.util.stream.IntStream
import javax.annotation.PostConstruct
import kotlin.streams.toList

interface ReviewsService {
    fun reviewsForShow(showId: Int): List<Review>?
    fun reviewsForShows(showIds: List<Int>): Map<Int, List<Review>>
    fun getReviewsPublisher(): Publisher<Review>
}

/**
 * 이 서비스는 데이터 저장소를 에뮬레이트합니다.
 * 데모의 편의를 위해 메모리에 리뷰를 생성하지만 예를 들어 데이터베이스가 이를 뒷받침한다고 상상해 보십시오.
 * 이것이 실제로 데이터베이스에 의해 지원된다면 N 1 문제를 피하는 것이 매우 중요할 것입니다. 즉, 이 클래스를 호출하기 위해 DataLoader 를 사용해야 함을 의미합니다.
 */
@Service
class DefaultReviewsService(private val showsService: ShowsService) : ReviewsService {
    private val logger = LoggerFactory.getLogger(ReviewsService::class.java)

    private val reviews = mutableMapOf<Int, MutableList<Review>>()
    private lateinit var reviewsStream: FluxSink<Review>
    private lateinit var reviewsPublisher: ConnectableFlux<Review>

    @PostConstruct
    fun createReviews() {
        val faker = Faker()

        //For each show we generate a random set of reviews.
        showsService.shows().forEach { show ->
            val generatedReviews = IntStream.range(0, faker.number().numberBetween(1, 20)).mapToObj {
                val date =
                    faker.date().past(300, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()
                Review(
                    username = faker.name().username(),
                    starScore = faker.number().numberBetween(0, 6),
                    submittedDate = OffsetDateTime.of(date, ZoneOffset.UTC)
                )
            }.toList().toMutableList()

            reviews[show.id] = generatedReviews
        }

        val publisher = Flux.create<Review> { emitter ->
            reviewsStream = emitter
        }


        reviewsPublisher = publisher.publish()
        reviewsPublisher.connect()

    }


    /**
     * Hopefully nobody calls this for multiple shows within a single query, that would indicate the N+1 problem!
     */
    override fun reviewsForShow(showId: Int): List<Review>? {
        return reviews[showId]
    }

    /**
     * 이것은 여러 쇼에 대한 리뷰를 로드할 때 호출하려는 메서드입니다.
     * 이 코드가 관계형 데이터베이스에서 지원되는 경우 단일 SQL 쿼리에서 요청된 모든 쇼에 대한 리뷰를 선택합니다.
     */
    override fun reviewsForShows(showIds: List<Int>): Map<Int, List<Review>> {
        logger.info("Loading reviews for shows ${showIds.joinToString()}")

        return reviews.filter { showIds.contains(it.key) }
    }


    override fun getReviewsPublisher(): Publisher<Review> {
        return reviewsPublisher
    }
}