package com.example.foodfinder.model

import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Parcelize
@Serializable
data class Product(
    val id: Int,
    val name: String,
    val quantity: Int,
    val image: String,
    val is_hot: Boolean,
    val shop: Int,
) : Parcelable {
    companion object {
        fun fromJson(json: String): Product {
            return Json.decodeFromString(json)
        }

        fun toJson(product: Product?): String {
            return Json.encodeToString(product)
        }
    }
}

class InfoParamType : NavType<Product>(isNullableAllowed = false) {
    override fun get(
        bundle: Bundle,
        key: String,
    ): Product? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Product {
        return Json.decodeFromString(value)
    }

    override fun put(
        bundle: Bundle,
        key: String,
        value: Product,
    ) {
        bundle.putParcelable(key, value)
    }
}

@Serializable
data class ScannedProduct(
    val product: Product,
    val time: Long,
)
