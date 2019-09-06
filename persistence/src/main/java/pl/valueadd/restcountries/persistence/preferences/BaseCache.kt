package pl.valueadd.restcountries.persistence.preferences

import android.content.SharedPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class BaseCache @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        const val DEFAULT_STRING_VALUE = ""
        const val DEFAULT_BOOLEAN_VALUE = false
        const val DEFAULT_LONG_VALUE: Long = -1
        const val DEFAULT_FLOAT_VALUE: Float = -1f
        const val DEFAULT_INT_VALUE = -1
    }

    internal fun savePreference(key: String, value: String?) =
        executeTransaction { it.putString(key, value) }

    internal fun savePreference(key: String, valuesList: Set<String>) =
        executeTransaction { it.putStringSet(key, valuesList) }

    internal fun savePreference(key: String, value: Int) =
        executeTransaction { it.putInt(key, value) }

    internal fun savePreference(key: String, value: Float) =
        executeTransaction { it.putFloat(key, value) }

    internal fun savePreference(key: String, value: Long) =
        executeTransaction { it.putLong(key, value) }

    internal fun savePreference(key: String, value: Boolean) =
        executeTransaction { it.putBoolean(key, value) }

    internal fun loadString(key: String, defaultValue: String = DEFAULT_STRING_VALUE): String =
        sharedPreferences.getString(key, defaultValue)
            ?: defaultValue

    internal fun loadStringSet(key: String, defaultValue: Set<String> = emptySet()): Set<String> =
        sharedPreferences.getStringSet(key, defaultValue)
            ?: defaultValue

    internal fun loadInt(key: String, defaultValue: Int = DEFAULT_INT_VALUE): Int =
        sharedPreferences.getInt(key, defaultValue)

    internal fun loadFloat(key: String, defaultValue: Float = DEFAULT_FLOAT_VALUE): Float =
        sharedPreferences.getFloat(key, defaultValue)

    internal fun loadLong(key: String, defaultValue: Long = DEFAULT_LONG_VALUE): Long =
        sharedPreferences.getLong(key, defaultValue)

    internal fun loadBoolean(key: String, defaultValue: Boolean): Boolean =
        sharedPreferences.getBoolean(key, defaultValue)

    internal fun clear() =
        executeTransaction { it.clear() }

    internal fun observeStringSet(
        key: String,
        defaultValue: Set<String> = emptySet()
    ): Flow<Set<String>> =
        observePreference(key) {
            it.getStringSet(key, defaultValue) ?: defaultValue
        }

    internal fun observeBoolean(
        key: String,
        defaultValue: Boolean = DEFAULT_BOOLEAN_VALUE
    ): Flow<Boolean> =
        observePreference(key) {
            it.getBoolean(key, defaultValue)
        }

    internal fun observeString(
        key: String,
        defaultValue: String = DEFAULT_STRING_VALUE
    ): Flow<String> =
        observePreference(key) {
            it.getString(key, defaultValue) ?: defaultValue
        }

    internal fun observeInt(key: String, defaultValue: Int = DEFAULT_INT_VALUE): Flow<Int> =
        observePreference(key) {
            it.getInt(key, defaultValue)
        }

    internal fun observeLong(
        key: String,
        defaultValue: Long = DEFAULT_LONG_VALUE
    ): Flow<Long> =
        observePreference(key) {
            it.getLong(key, defaultValue)
        }

    internal fun observeFloat(
        key: String,
        defaultValue: Float = DEFAULT_FLOAT_VALUE
    ): Flow<Float> =
        observePreference(key) {
            it.getFloat(key, defaultValue)
        }

    private fun <T> observePreference(
        preferenceKey: String,
        function: (SharedPreferences) -> T
    ): Flow<T> = channelFlow {
        val sharedPreferencesListener =
                SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->

                    if (key == preferenceKey) {
                        runBlocking {
                            channel.send(function(sharedPreferences))
                        }
                    }
                }

            channel.invokeOnClose {
                sharedPreferences.unregisterOnSharedPreferenceChangeListener(
                    sharedPreferencesListener
                )
            }
            channel.send(function(sharedPreferences))

            sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)
    }

    private inline fun executeTransaction(function: (SharedPreferences.Editor) -> Unit) {
        sharedPreferences.edit().apply {
            function(this)
            apply()
        }
    }
}