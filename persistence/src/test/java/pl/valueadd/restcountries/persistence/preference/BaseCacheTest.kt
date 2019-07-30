package pl.valueadd.restcountries.persistence.preference

import android.content.SharedPreferences
import pl.valueadd.restcountries.persistence.AbstractUnitTest
import pl.valueadd.restcountries.persistence.preferences.BaseCache
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock

@RunWith(JUnit4::class)
class BaseCacheTest : AbstractUnitTest() {

    companion object {

        private const val TEST_PREF_KEY = "TEST_PREF_KEY"
        private const val TEST_VALUE = "TEST_VALUE"
    }

    @Mock
    lateinit var mockSharedPrefs: SharedPreferences

    @Mock
    lateinit var mockEditor: SharedPreferences.Editor

    lateinit var baseCache: BaseCache

    @Before
    fun setup() {
        baseCache = toothPickRule.getInstance(BaseCache::class.java)
    }

    @Test
    fun `Should save String value`() {
        // Given
        whenever(mockSharedPrefs.edit()).thenReturn(mockEditor)

        // When
        baseCache.savePreference(TEST_PREF_KEY, TEST_VALUE)

        // Then
        verify(mockEditor).putString(TEST_PREF_KEY, TEST_VALUE)
        verify(mockEditor).apply()
        verify(mockSharedPrefs).edit()
        verifyNoMoreInteractions(mockEditor)
        verifyNoMoreInteractions(mockSharedPrefs)
    }
}