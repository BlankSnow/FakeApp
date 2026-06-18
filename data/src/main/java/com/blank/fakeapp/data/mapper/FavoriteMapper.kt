package com.blank.fakeapp.data.mapper

import com.blank.fakeapp.data.local.entity.FavoriteProductEntity
import com.blank.fakeapp.domain.model.Product

fun FavoriteProductEntity.toDomain(): Product {
    return Product(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image,
        isFavorite = true
    )
}

fun Product.toEntity(): FavoriteProductEntity {
    return FavoriteProductEntity(
        id = id,
        title = title,
        price = price,
        description = description,
        category = category,
        image = image
    )
}
