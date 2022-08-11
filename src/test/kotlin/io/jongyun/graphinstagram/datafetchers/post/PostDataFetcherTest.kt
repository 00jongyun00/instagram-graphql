package io.jongyun.graphinstagram.datafetchers.post

import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import io.jongyun.graphinstagram.GraphInstagramApplication
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.spring.SpringListener
import org.hibernate.annotations.common.util.impl.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = [DgsAutoConfiguration::class, GraphInstagramApplication::class])
class PostDataFetcherTest : DescribeSpec() {

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    override fun listeners() = listOf(SpringListener)

    init {
        val logger = LoggerFactory.logger(PostDataFetcherTest::class.java)
        describe("posts") {
            it("get all posts") {
                val postLists: List<String> = dgsQueryExecutor.executeAndExtractJsonPath(
                    """
                    {
                        posts {
                            id
                            content
                            createdAt
                            updatedAt
                        }
                    }
                    """.trimIndent(), "data.posts"
                )
                postLists.size shouldBeGreaterThan 0
            }
        }
    }
}