package com.simple.mart.study

import com.simple.mart.RestDocsTest
import com.simple.mart.TestController
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import org.springframework.restdocs.request.RequestDocumentation.pathParameters
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
        document("member-get", // 문서 조각 디렉토리 명
          pathParameters( // path 파라미터 정보 입력
            parameterWithName("id").description("Member ID")
          ),
          responseFields( // response 필드 정보 입력
            fieldWithPath("id").description("ID"),
            fieldWithPath("name").description("name"),
            fieldWithPath("email").description("email")
          )
        )
      }
  }
}
