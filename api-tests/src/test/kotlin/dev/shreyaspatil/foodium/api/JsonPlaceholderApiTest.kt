package dev.shreyaspatil.foodium.api

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.qameta.allure.kotlin.Description
import io.qameta.allure.kotlin.Epic
import io.qameta.allure.kotlin.Feature
import io.qameta.allure.kotlin.Story
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@Serializable
data class JsonPost(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

@Serializable
data class NewPost(
    val userId: Int,
    val title: String,
    val body: String
)

@Serializable
data class CreatedPost(
    val userId: Int,
    val id: Int,
    val title: String,
    val body: String
)

@Epic("API Tests")
@Feature("JSONPlaceholder API")
@RunWith(JUnit4::class)
class JsonPlaceholderApiTest {

    private lateinit var client: HttpClient
    private val baseUrl = "https://jsonplaceholder.typicode.com"

    @Before
    fun setup() {
        val json = Json(Json.Default) {
            ignoreUnknownKeys = true
            isLenient = true
        }
        client = HttpClient(CIO) {
            expectSuccess = false
            install(JsonFeature) {
                serializer = KotlinxSerializer(json)
            }
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.INFO
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 60000
                connectTimeoutMillis = 60000
            }
        }
    }

    @After
    fun tearDown() {
        client.close()
    }

    @Story("GET Posts")
    @Description("Проверка статуса 200 OK для GET /posts")
    @Test
    fun getAllPosts_returnsOkStatus() = runBlocking {
        val response: HttpResponse = client.get("$baseUrl/posts/1")
        assertEquals(HttpStatusCode.OK, response.status)
    }

    @Story("GET Posts")
    @Description("Проверка получения поста по ID")
    @Test
    fun getPostById_existingId_returnsPost() = runBlocking {
        val response: HttpResponse = client.get("$baseUrl/posts/1")
        assertEquals(HttpStatusCode.OK, response.status)

        val post: JsonPost = client.get("$baseUrl/posts/1")
        assertEquals(1, post.id)
        assertTrue(post.title.isNotEmpty())
        assertTrue(post.body.isNotEmpty())
    }

    @Story("GET Posts")
    @Description("Проверка 404 для несуществующего поста")
    @Test
    fun getPostById_nonExistingId_returns404() = runBlocking {
        val response: HttpResponse = client.get("$baseUrl/posts/99999")
        assertEquals(HttpStatusCode.NotFound, response.status)
    }

    @Story("POST Posts")
    @Description("Проверка создания поста (201 Created)")
    @Test
    fun createPost_withValidData_returns201() = runBlocking {
        val newPost = NewPost(
            userId = 1,
            title = "Test Post Title",
            body = "Test post body content"
        )

        val response: HttpResponse = client.post("$baseUrl/posts") {
            contentType(ContentType.Application.Json)
            body = newPost
        }
        assertEquals(HttpStatusCode.Created, response.status)

        val createdPost: CreatedPost = client.post("$baseUrl/posts") {
            contentType(ContentType.Application.Json)
            body = newPost
        }
        assertTrue(createdPost.id > 0)
        assertEquals(newPost.title, createdPost.title)
        assertEquals(newPost.body, createdPost.body)
    }

    @Story("GET Posts")
    @Description("Проверка фильтрации постов по userId")
    @Test
    fun getPostsByUserId_returnsFilteredList() = runBlocking {
        val response: HttpResponse = client.get("$baseUrl/posts?userId=1")
        assertEquals(HttpStatusCode.OK, response.status)

        val posts: List<JsonPost> = client.get("$baseUrl/posts?userId=1")
        assertTrue(posts.isNotEmpty())
        assertTrue(posts.all { it.userId == 1 })
    }

    @Story("DELETE Posts")
    @Description("Проверка удаления поста (200 OK)")
    @Test
    fun deletePost_existingId_returns200() = runBlocking {
        val response: HttpResponse = client.delete("$baseUrl/posts/1")
        assertEquals(HttpStatusCode.OK, response.status)
    }
}
