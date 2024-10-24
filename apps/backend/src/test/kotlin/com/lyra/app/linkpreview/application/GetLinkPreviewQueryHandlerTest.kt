package com.lyra.app.linkpreview.application

import com.lyra.UnitTest
import com.lyra.app.linkpreview.domain.LinkPreview
import com.lyra.app.linkpreview.domain.LinkPreviewRepository
import io.mockk.coEvery
import io.mockk.mockk
import java.net.URI
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

@UnitTest
internal class GetLinkPreviewQueryHandlerTest {

    private val repository: LinkPreviewRepository = mockk()
    private val service = LinkPreviewService(repository)
    private val handler = GetLinkPreviewQueryHandler(service)
    private val url = URI("https://example.com").toURL()

    @Test
    fun fetchesLinkPreviewSuccessfully() = runBlocking {
        val query = GetLinkPreviewQuery("https://example.com")
        val linkPreview = LinkPreview(
            "Example Title",
            "Example Description",
            "https://example.com/image.jpg",
            "https://example.com",
        )
        coEvery { repository.getPreview(url) } returns linkPreview

        val result = handler.handle(query)

        assertEquals("Example Title", result.title)
        assertEquals("Example Description", result.description)
        assertEquals("https://example.com/image.jpg", result.imageUrl)
        assertEquals("https://example.com", result.url)
    }

    @Test
    fun `fetches Link Preview With Invalid Url`(): Unit = runBlocking {
        val query = GetLinkPreviewQuery("invalid-url")

        try {
            handler.handle(query)
        } catch (e: IllegalArgumentException) {
            assertEquals("URI is not absolute", e.message)
        }
    }

    @Test
    fun fetchesLinkPreviewWithEmptyResponse() = runBlocking {
        val query = GetLinkPreviewQuery("https://example.com")
        val linkPreview = LinkPreview("", "", "", "https://example.com")
        coEvery { repository.getPreview(url) } returns linkPreview

        val result = handler.handle(query)

        assertEquals("", result.title)
        assertEquals("", result.description)
        assertEquals("", result.imageUrl)
        assertEquals("https://example.com", result.url)
    }
}
