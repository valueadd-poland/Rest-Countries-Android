package pl.valueadd.restcountries.persistence.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

abstract class BaseDao<E> {

    /**
     * Insert entity to database.
     * @return id of inserted entity.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: E): Long

    /**
     * Insert entities to database.
     * @return [Completable]
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entities: Iterable<E>)

    /**
     * Insert entities to database.
     * @return [Single] which contains [List] of [ids][Long].
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertEntities(entities: Collection<E>): List<Long>

    /**
     * Update the entity.
     * @return count of updated rows.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: E): Int

    /**
     * Update the entities.
     * @return count of updated rows.
     */
    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entities: Iterable<E>)

    /**
     * Delete the entity.
     * @return count of deleted rows.
     */
    @Delete
    abstract suspend fun remove(entity: E): Int

    /**
     * Delete the entities.
     * @return count of deleted rows.
     */
    @Delete
    abstract suspend fun remove(entities: Iterable<E>)
}