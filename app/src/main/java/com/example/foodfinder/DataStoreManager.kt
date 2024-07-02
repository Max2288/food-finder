import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodfinder.model.Product
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val Context.dataStore by preferencesDataStore("product_history")

class DataStoreManager(private val context: Context) {
    private val productHistoryKey = stringPreferencesKey("product_history")

    val productHistory: Flow<List<Product>> =
        context.dataStore.data
            .map { preferences ->
                val jsonString = preferences[productHistoryKey] ?: "[]"
                println("Retrieved JSON string: $jsonString")
                try {
                    Json.decodeFromString<List<Product>>(jsonString).also {
                        println("Decoded product list: $it")
                    }
                } catch (e: Exception) {
                    println("Error decoding product list: $e")
                    emptyList<Product>()
                }
            }

    suspend fun saveProductToHistory(product: Product) {
        context.dataStore.edit { preferences ->
            val currentHistory = try {
                Json.decodeFromString<List<Product>>(preferences[productHistoryKey] ?: "[]")
            } catch (e: Exception) {
                emptyList()
            }
            val updatedHistory = (listOf(product) + currentHistory).distinct().take(10)
            val jsonString = Json.encodeToString(updatedHistory)
            println("Saving updated history JSON string: $jsonString")
            preferences[productHistoryKey] = jsonString
        }
    }
}
