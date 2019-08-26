package pl.valueadd.restcountries.domain.model.helper

abstract class Filter<T>(val isAscending: Boolean) {

    protected open var filterComparator: Comparator<T>? = null

    val comparator: Comparator<T>
        get() {
            return filterComparator
                ?: throw UnsupportedOperationException("Comparator is not provided for ${this::class.java.simpleName}")
        }

    val hasComparator: Boolean
        get() = filterComparator != null

    val isDescending: Boolean
        get() = !isAscending

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Filter<*>

        if (isAscending != other.isAscending) return false

        return true
    }

    override fun hashCode(): Int {
        return isAscending.hashCode()
    }
}