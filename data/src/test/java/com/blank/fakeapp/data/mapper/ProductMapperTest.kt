package com.blank.fakeapp.data.mapper

import com.blank.fakeapp.data.remote.dto.ProductDto
import com.blank.fakeapp.data.remote.dto.RatingDto
import org.junit.Assert.assertEquals
import org.junit.Test

class ProductMapperTest {

    @Test
    fun `ProductDto toDomain maps correctly`() {
        // Given
        val dto = ProductDto(
            id = 1,
            title = "Test Product",
            price = 9.99,
            description = "Description",
            category = "Category",
            image = "image_url",
            rating = RatingDto(rate = 4.5, count = 10)
        )

        // When
        val domain = dto.toDomain()

        // Then
        assertEquals(dto.id, domain.id)
        assertEquals(dto.title, domain.title)
        assertEquals(dto.price, domain.price, 0.0)
        assertEquals(dto.description, domain.description)
        assertEquals(dto.category, domain.category)
        assertEquals(dto.image, domain.image)
        assertEquals(false, domain.isFavorite)
    }
}
