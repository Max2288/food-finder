import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.foodfinder.model.Product
import com.example.foodfinder.model.ScannedProduct
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val Context.dataStore by preferencesDataStore("product_history")

class DataStoreManager(private val context: Context) {
    private val productHistoryKey = stringPreferencesKey("product_history")

    val productHistory: Flow<List<ScannedProduct>> =
        context.dataStore.data
            .map { preferences ->
                val jsonString = preferences[productHistoryKey] ?: "[]"
                try {
                    Json.decodeFromString<List<ScannedProduct>>(jsonString)
                } catch (e: Exception) {
                    emptyList<ScannedProduct>()
                }
            }

    suspend fun saveProductToHistory(product: Product) {
        context.dataStore.edit { preferences ->
            val currentHistory =
                try {
                    Json.decodeFromString<List<ScannedProduct>>(preferences[productHistoryKey] ?: "[]")
                } catch (e: Exception) {
                    emptyList()
                }
            val updatedHistory =
                (
                    listOf(
                        ScannedProduct(
                            product,
                            System.currentTimeMillis(),
                        ),
                    ) + currentHistory
                ).distinct().take(10)
            val jsonString = Json.encodeToString(updatedHistory)
            preferences[productHistoryKey] = jsonString
        }
    }
}
