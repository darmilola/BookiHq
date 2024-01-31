package domain.base


abstract class AggregateClient {
    abstract fun get(id: Long)

    abstract fun create()

    abstract fun update(id: Long)

    abstract fun delete(id: Long)

    abstract fun cacheKey(): String
}
