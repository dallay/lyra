package com.lyra.app.linkpreview.infrastructure.parser

import com.lyra.UnitTest
import io.mockk.coEvery
import io.mockk.mockkStatic
import java.net.URI
import java.net.URL
import kotlinx.coroutines.runBlocking
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@UnitTest
internal class JsoupLinkPreviewRepositoryTest {

    private lateinit var repository: JsoupLinkPreviewRepository
    private lateinit var url: URL
    private lateinit var document: Document

    @BeforeEach
    fun setUp() {
        repository = JsoupLinkPreviewRepository()
        url = URI("https://example.com").toURL()
        document = Jsoup.parse(
            """
                <html>
                    <head>
                        <title>Example Title</title>
                        <meta name="description" content="Example Description">
                        <meta property="og:image" content="https://example.com/image.jpg">
                    </head>
                    <body></body>
                </html>
            """.trimIndent(),
        )
        document.setBaseUri("https://example.com")
    }

    @Test
    fun fetchesLinkPreviewSuccessfully() = runBlocking {
        mockkStatic(Jsoup::class)
        coEvery { Jsoup.connect(url.toString()).get() } returns document

        val result = repository.getPreview(url)

        assertEquals("Example Title", result.title)
        assertEquals("Example Description", result.description)
        assertEquals("https://example.com/image.jpg", result.imageUrl)
        assertEquals("https://example.com", result.url)
    }

    @Test
    fun fetchesLinkPreviewWithMissingDescription() = runBlocking {
        document =
            Jsoup.parse(
                """
                    <html>
                        <head>
                            <title>Example Title</title>
                            <meta property="og:image" content="https://example.com/image.jpg">
                        </head>
                        <body></body>
                    </html>
                """.trimIndent(),
            )
        document.setBaseUri("https://example.com")
        mockkStatic(Jsoup::class)
        coEvery { Jsoup.connect(url.toString()).get() } returns document

        val result = repository.getPreview(url)

        assertEquals("Example Title", result.title)
        assertEquals("No description available", result.description)
        assertEquals("https://example.com/image.jpg", result.imageUrl)
        assertEquals("https://example.com", result.url)
    }

    @Test
    fun fetchesLinkPreviewWithMissingImage() = runBlocking {
        document =
            Jsoup.parse(
                """
                    <html>
                        <head>
                            <title>Example Title</title>
                            <meta name="description" content="Example Description">
                        </head>
                        <body></body>
                    </html>
                """.trimIndent(),
            )
        document.setBaseUri("https://example.com")
        mockkStatic(Jsoup::class)
        coEvery { Jsoup.connect(url.toString()).get() } returns document

        val result = repository.getPreview(url)

        assertEquals("Example Title", result.title)
        assertEquals("Example Description", result.description)
        assertEquals("", result.imageUrl)
        assertEquals("https://example.com", result.url)
    }

    @Test
    fun fetchesLinkPreviewWithEmptyTitle() = runBlocking {
        document =
            Jsoup.parse(
                """
                    <html>
                        <head>
                            <title></title>
                            <meta name="description" content="Example Description">
                            <meta property="og:image" content="https://example.com/image.jpg">
                        </head>
                        <body></body>
                    </html>
                """.trimIndent(),
            )
        document.setBaseUri("https://example.com")
        mockkStatic(Jsoup::class)
        coEvery { Jsoup.connect(url.toString()).get() } returns document

        val result = repository.getPreview(url)

        assertEquals("", result.title)
        assertEquals("Example Description", result.description)
        assertEquals("https://example.com/image.jpg", result.imageUrl)
        assertEquals("https://example.com", result.url)
    }
}
