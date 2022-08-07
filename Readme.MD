<div align="center">
  <a href="https://github.com/Instagram-Clone-Coding">
    <img src="https://avatars.githubusercontent.com/u/90607105?s=200&v=4" alt="Logo" width="80" height="80">
  </a>
<h3 align="center">Instagram-Clone</h3>
</div>

## Using Skill

- Dgs framework
- Spring Security
- Spring data JPA
- Mysql
- docker
- Kotest

# TEST Convention

[BDD] - 비즈니스 로직 작성시 테스트 코드를 먼저 작성하고 비즈니스 로직을 작성하는 방향으로 불필요한 Dependency 를 제거하고 코드를 깔끔하게 유지한다.

```kotlin
Given("Add like post") {
    val likePostInput = LikePostInput("1")
    val member = generateMember(1L)
    val post = generatePost(member, 1L)
    When("Member 를 찾을 수 없어서") {
        every { memberRepository.findById(1L) } returns Optional.empty()
        Then("예외가 발생한다.") {
            val exception = shouldThrow<BusinessException> { postLikeService.addLike(1L, likePostInput) }
            exception.errorCode shouldBe ErrorCode.MEMBER_DOES_NOT_EXISTS
        }
    }
```