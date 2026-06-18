package com.blank.fakeapp.data.mapper

import com.blank.fakeapp.data.remote.dto.ProductDto
import com.blank.fakeapp.domain.model.Product

fun ProductDto.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        isFavorite = false
    )
}
