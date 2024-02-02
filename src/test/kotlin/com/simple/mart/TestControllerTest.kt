package com.simple.mart

import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(TestController::class)
class TestControllerTest : RestDocsTest() {

  @Test
  fun testRestDoc() {
    mockMvc
      .perform(get("/test"))
      .andExpect {
        status().isOk
      }
      .andDo {
        document("test-rest-doc-test-get")
      }
  }
}
