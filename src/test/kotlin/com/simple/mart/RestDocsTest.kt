package com.simple.mart

import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.context.annotation.Import
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultHandlers
import org.springframework.test.web.servlet.setup.DefaultMockMvcBuilder
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext
import org.springframework.web.filter.CharacterEncodingFilter


@AutoConfigureRestDocs
@ExtendWith(RestDocumentationExtension::class)
@Import(RestDocsConfiguration::class)
abstract class RestDocsTest{

  lateinit var mockMvc: MockMvc

  @Autowired
  lateinit var restDocumentationResultHandler: RestDocumentationResultHandler

  @Autowired
  lateinit var objectMapper: ObjectMapper

  @BeforeEach
  fun setUp(
    webApplicationContext: WebApplicationContext,
    restDocumentationContextProvider: RestDocumentationContextProvider
  ) {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
      .addFilter<DefaultMockMvcBuilder>(CharacterEncodingFilter("UTF-8", true))
      .alwaysDo<DefaultMockMvcBuilder>(restDocumentationResultHandler)
      .apply<DefaultMockMvcBuilder>(
        documentationConfiguration(
          restDocumentationContextProvider
        ).operationPreprocessors()
          .withRequestDefaults(prettyPrint())
          .withResponseDefaults(prettyPrint())
      )
      .build()
  }
}
