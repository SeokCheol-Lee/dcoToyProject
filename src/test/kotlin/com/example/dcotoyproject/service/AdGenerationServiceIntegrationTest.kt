package com.example.dcotoyproject.service

import com.example.dcotoyproject.domain.AdCreativeDetails
import com.example.dcotoyproject.repository.AdCreativeDetailsRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.RequestResponseSpec
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean

@SpringBootTest
class AdGenerationServiceIntegrationTest {

    @Autowired
    private lateinit var adGenerationService: AdGenerationService

    @Autowired
    private lateinit var adCreativeDetailsRepository: AdCreativeDetailsRepository

    @MockBean
    private lateinit var chatClient: ChatClient

    @MockBean
    private lateinit var requestResponseSpec: RequestResponseSpec


    @Test
    fun `testGenerateAdHtml_success`() {
        val testPrompt = "test prompt for a new amazing product"
        val mockHtmlResponse = "<div><p>Amazing Product Ad!</p></div>"

        // Mock the fluent API chain for ChatClient
        Mockito.`when`(chatClient.prompt(Mockito.any(Prompt::class.java))).thenReturn(requestResponseSpec)
        Mockito.`when`(requestResponseSpec.call()).thenReturn(requestResponseSpec)
        Mockito.`when`(requestResponseSpec.content()).thenReturn(mockHtmlResponse)

        val generatedHtml = adGenerationService.generateAdHtml(testPrompt)

        assertThat(generatedHtml).isEqualTo(mockHtmlResponse)

        val savedEntities = adCreativeDetailsRepository.findAll()
        assertThat(savedEntities).hasSize(1)
        val savedEntity = savedEntities.first()

        assertThat(savedEntity.promptText).isEqualTo(testPrompt)
        assertThat(savedEntity.generatedHtml).isEqualTo(mockHtmlResponse)
    }
}
