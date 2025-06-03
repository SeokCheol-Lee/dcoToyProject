package com.example.dcotoyproject.service

import com.example.dcotoyproject.domain.AdCreativeDetails
import com.example.dcotoyproject.repository.AdCreativeDetailsRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.ai.chat.ChatResponse
import org.springframework.ai.chat.Generation
import org.springframework.ai.chat.model.ChatModel
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
    private lateinit var chatModel: ChatModel

    @AfterEach
    fun tearDown() {
        // Clean up database after each test
        adCreativeDetailsRepository.deleteAll()
    }

    @Test
    fun `testGenerateAdHtml_success`() {
        val testPrompt = "test prompt for a new amazing product"
        val mockOutputText = "<div><p>Amazing Product Ad!</p></div>"

        val mockGeneration = Generation(mockOutputText)
        val mockChatResponse = ChatResponse(listOf(mockGeneration))

        Mockito.`when`(chatModel.call(Mockito.any(Prompt::class.java)))
            .thenReturn(mockChatResponse)

        val generatedHtml = adGenerationService.generateAdHtml(testPrompt)

        assertThat(generatedHtml).isEqualTo(mockOutputText)

        val savedEntities = adCreativeDetailsRepository.findAll()
        assertThat(savedEntities).hasSize(1)
        val savedEntity = savedEntities.first()

        assertThat(savedEntity.promptText).isEqualTo(testPrompt)
        assertThat(savedEntity.generatedHtml).isEqualTo(mockOutputText)
    }
}
