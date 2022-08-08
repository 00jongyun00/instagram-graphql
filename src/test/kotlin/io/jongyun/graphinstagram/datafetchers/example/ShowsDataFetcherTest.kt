package io.jongyun.graphinstagram.datafetchers.example

import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import io.jongyun.graphinstagram.GraphInstagramApplication
import io.jongyun.graphinstagram.datafetchers.post.PostLikeMutationFetcherTest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeExactly
import io.kotest.spring.SpringListener
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [DgsAutoConfiguration::class, GraphInstagramApplication::class])
@ActiveProfiles("local")
internal class ShowsDataFetcherTest : DescribeSpec() {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    override fun listeners() = listOf(SpringListener)

    init {
        val logger = LoggerFactory.logger(PostLikeMutationFetcherTest::class.java)
        describe("Shows") {
            it("Should return specific show") {
                val showList: List<String> = dgsQueryExecutor.executeAndExtractJsonPath(
                    """
                    {
                        shows {
                            title
                            releaseYear
                        }
                    }
                    """.trimIndent(),
                    "data.shows[*].title",
                )
                logger.info(showList)
                showList.size shouldBeExactly 5
            }
        }
    }
}