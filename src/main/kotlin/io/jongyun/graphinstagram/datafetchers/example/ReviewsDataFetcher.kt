package io.jongyun.graphinstagram.datafetchers.example

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsData
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment
import io.jongyun.graphinstagram.DgsConstants
import io.jongyun.graphinstagram.dataloader.example.ReviewsDataLoader
import io.jongyun.graphinstagram.types.Review
import io.jongyun.graphinstagram.types.Show
import org.dataloader.DataLoader
import java.util.concurrent.CompletableFuture

@DgsComponent
class ReviewsDataFetcher {

    /**
     * 이 데이터 페처는 쇼의 "리뷰" 필드를 해결하기 위해 호출됩니다.
     * 각 개별 쇼에 대해 호출되므로 10개의 쇼를 로드하면 이 메서드는 10번 호출됩니다.
     * N 1 문제를 피하기 위해 이 datafetcher 는 DataLoader 를 사용합니다.
     * 각각의 개별 쇼 ID에 대해 DataLoader 가 호출되지만, ReviewsDataLoader 의 "load" 메소드에 대한 단일 메소드 호출로 실제 로드를 일괄 처리합니다.
     * 이것이 제대로 작동하려면 datafetcher 가 CompletableFuture 를 반환해야 합니다.
     */
    @DgsData(parentType = DgsConstants.SHOW.TYPE_NAME, field = DgsConstants.SHOW.Reviews)
    fun reviews(dfe: DgsDataFetchingEnvironment): CompletableFuture<List<Review>> {
        // DataLoader 를 이름으로 로드하는 대신 DgsDataFetchingEnvironment 를 사용하고 DataLoader 클래스 이름을 전달할 수 있습니다.
        val reviewsDataLoader: DataLoader<Int, List<Review>> = dfe.getDataLoader(ReviewsDataLoader::class.java)

        // 리뷰 필드가 Show 에 있기 때문에 getSource() 메서드는 Show 인스턴스를 반환합니다.
        val show: Show = dfe.getSource()

        // DataLoader 에서 리뷰를 로드합니다. 이 호출은 비동기식이며 DataLoader 메커니즘에 의해 일괄 처리됩니다.
        return reviewsDataLoader.load(show.id)

    }
}