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

package io.jongyun.graphinstagram.dataloader.example

import com.netflix.graphql.dgs.DgsDataLoader
import io.jongyun.graphinstagram.service.example.ReviewsService
import io.jongyun.graphinstagram.types.Review
import org.dataloader.MappedBatchLoader
import java.util.concurrent.CompletableFuture
import java.util.concurrent.CompletionStage
import kotlin.streams.toList

@DgsDataLoader(name = "reviews")
class ReviewsDataLoader(val reviewsService: ReviewsService) : MappedBatchLoader<Int, List<Review>> {
    /**
     * 이 메서드는 여러 datafetcher 가 DataLoader 에서 load() 메서드를 사용하는 경우에도 한 번 호출됩니다.
     * 이렇게 하면 개별 쇼가 아닌 한 번의 호출로 모든 쇼에 대한 리뷰를 로드할 수 있습니다.
     */
    override fun load(keys: MutableSet<Int>): CompletionStage<Map<Int, List<Review>>> {
        return CompletableFuture.supplyAsync { reviewsService.reviewsForShows(keys.stream().toList()) }
    }

}