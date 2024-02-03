package com.simple.mart

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.restdocs.operation.preprocess.Preprocessors
import javax.management.Attribute

@TestConfiguration
class RestDocsConfiguration {
  @Bean
  fun write(): RestDocumentationResultHandler {
    return MockMvcRestDocumentation.document(
      "{class-name}/{method-name}",
      Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
      Preprocessors.preprocessResponse(Preprocessors.prettyPrint())
    )
  }

  companion object {
    fun field(
      key: String?,
      value: String?
    ): Attribute {
      return Attribute(key, value)
    }
  }

}