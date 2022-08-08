package io.jongyun.graphinstagram.datafetchers.member

import com.netflix.graphql.dgs.DgsQueryExecutor
import com.netflix.graphql.dgs.autoconfig.DgsAutoConfiguration
import io.jongyun.graphinstagram.GraphInstagramApplication
import io.jongyun.graphinstagram.entity.member.Member
import io.jongyun.graphinstagram.service.post.generateMember
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.spring.SpringListener
import io.mockk.clearAllMocks
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@SpringBootTest(classes = [DgsAutoConfiguration::class, GraphInstagramApplication::class])
@ActiveProfiles("local")
internal class MemberDataFetcherTest : DescribeSpec() {

    override fun listeners() = listOf(SpringListener)

    @Autowired
    lateinit var dgsQueryExecutor: DgsQueryExecutor

    init {
        var mockedMembers: List<Member> = listOf()
        beforeEach() {
            repeat(10) {
                mockedMembers + generateMember()
            }
        }
        afterEach {
            clearAllMocks()
        }
        describe("POST") {
            it("에 대해서 좋아요 누른 회원을 조회한다.") {
                val likedMembersToPost: List<String> = dgsQueryExecutor.executeAndExtractJsonPath(
                    """
                        query {
                            likedMembersToPost(postId: "1") {
                                name
                                id
                            }
                        }
                    """.trimIndent(),
                    "data.likedMembersToPost[*].name"
                )
                likedMembersToPost.count() shouldBeGreaterThan 0
            }
        }
    }

}