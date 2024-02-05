package com.simple.mart.product

import com.simple.mart.RestDocsTest
import com.simple.mart.product.presentation.ProductController
import com.simple.mart.product.presentation.request.CreateProductRequest
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(ProductController::class)
class ProductControllerTest : RestDocsTest() {

    @Test
    fun `상품을 등록한다`() {
        mockMvc
            .post("") {
                contentType = MediaType.APPLICATION_JSON
                content = objectMapper.writeValueAsString(CreateProductRequest("title"))
            }
            .andExpect {
                status().isCreated
            }
    }
}
