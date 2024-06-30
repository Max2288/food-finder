package com.example.foodfinder.model
import android.os.Bundle
import android.os.Parcelable
import androidx.navigation.NavType
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
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
            return Gson().fromJson(json, Product::class.java)
        }

        fun toJson(product: Product?): String {
            return Gson().toJson(product)
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
        return Gson().fromJson(value, Product::class.java)
    }

    override fun put(
        bundle: Bundle,
        key: String,
        value: Product,
    ) {
        bundle.putParcelable(key, value)
    }
}
