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

package io.jongyun.graphinstagram.datafetchers.example

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import com.netflix.graphql.dgs.InputArgument
import io.jongyun.graphinstagram.service.example.ShowsService
import io.jongyun.graphinstagram.types.Show
import kotlinx.coroutines.coroutineScope

@DgsComponent
class ShowsDataFetcher(private val showsService: ShowsService) {

    /**
     * 이 datafetcher는 쿼리의 'shows' 필드를 확인합니다.
     * @InputArgument를 사용하여 정의된 경우 쿼리에서 titleFilter 를 가져옵니다.
     * 구현 세부 사항으로 Kotlin Coroutine을 출력 유형으로 활용합니다.
     *
     */
    @DgsQuery
    suspend fun shows(@InputArgument titleFilter: String?): List<Show> = coroutineScope {
        if (titleFilter != null) {
            showsService.shows().filter { it.title.contains(titleFilter) }
        } else {
            showsService.shows() // 1
        }
    }
}