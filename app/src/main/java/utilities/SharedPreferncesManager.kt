package utilities
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.gson.Gson
import model.RecordLeaderboard

class SharedPreferncesManager private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        Constants.SP_keys.RECORD_KEY,
        Context.MODE_PRIVATE
    )

    companion object {
        @Volatile
        private var instance: SharedPreferncesManager? = null

        fun init(context: Context): SharedPreferncesManager {
            return instance ?: synchronized(this) {
                instance ?: SharedPreferncesManager(context).also { instance = it }
            }
        }

        fun getInstance(): SharedPreferncesManager                                       {
            return instance ?: throw IllegalStateException(
                "SharedPreferencesManager must be initialized by calling init(context) before use."
            )
        }
    }


    fun putString(key: String, value: String) {
        with(sharedPreferences.edit()) {
            putString(key, value)
            apply()
        }
    }

    fun getString(key: String, defaultValue: String): String {
        return sharedPreferences.getString(
            key, defaultValue
        ) ?: defaultValue
    }
    fun loadSharedPreferences(): RecordLeaderboard {
        val gson = Gson()
        val recordAsJsonFromSP = SharedPreferncesManager.getInstance().getString(
            Constants.SP_keys.RECORD_KEY, ""
        )
        Log.d("recordAsJsonFromSP", "recordAsJsonFromSP: $recordAsJsonFromSP")
        val recordFromSP = gson.fromJson(recordAsJsonFromSP, RecordLeaderboard::class.java)
        Log.d("recordFromSP", "recordFromSP: $recordFromSP")
        return recordFromSP

    }
    fun saveSharedPreferences(record: RecordLeaderboard) {
        val gson = Gson()

        val recordAsJson: String = gson.toJson(record )
        SharedPreferncesManager.getInstance().putString(Constants.SP_keys.RECORD_KEY, recordAsJson)

    }
}