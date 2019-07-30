package pl.valueadd.restcountries.persistence.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Single

abstract class BaseDao<E> {

    /**
     * Insert entity to database.
     * @return id of inserted entity.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: E): Single<Long>

    /**
     * Insert entities to database.
     * @return [Completable]
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entities: Iterable<E>): Completable

    /**
     * Insert entities to database.
     * @return [Single] which contains [List] of [ids][Long].
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertEntities(entities: Collection<E>): Single<List<Long>>

    /**
     * Update the entity.
     * @return count of updated rows.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(entity: E): Single<Int>

    /**
     * Update the entities.
     * @return count of updated rows.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract fun update(entities: Iterable<E>): Completable

    /**
     * Delete the entity.
     * @return count of deleted rows.
     */
    @Delete
    abstract fun remove(entity: E): Single<Int>

    /**
     * Delete the entities.
     * @return count of deleted rows.
     */
    @Delete
    abstract fun remove(entities: Iterable<E>): Completable
}