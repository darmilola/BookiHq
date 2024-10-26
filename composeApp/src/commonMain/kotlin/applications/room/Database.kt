package applications.room

import androidx.room.ConstructedBy
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import domain.Models.FavoriteProduct
import domain.Models.PaymentCard
import domain.Models.User
import domain.Models.Vendor

@Database(entities = [PaymentCard::class, User::class, Vendor::class, FavoriteProduct::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getPaymentCardDao(): PaymentCardDao
    abstract fun getUserDao(): UserDao
    abstract fun getVendorDao(): VendorDao
    abstract fun getFavoriteProductDao(): FavoriteProductDao
}
@Suppress("NO_ACTUAL_FOR_EXPECT")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase>

@Dao
interface PaymentCardDao {
    @Insert
    suspend fun insert(paymentCard: PaymentCard)
    @Query("SELECT count(*) FROM PaymentCard")
    suspend fun count(): Int
    @Query("SELECT * FROM PaymentCard")
    suspend fun getAllPaymentCards(): List<PaymentCard>
}


@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)
    @Query("SELECT count(*) FROM User")
    suspend fun count(): Int
    @Query("SELECT * FROM User")
    suspend fun getUser(): User
}

@Dao
interface FavoriteProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(product: FavoriteProduct)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(productList: List<FavoriteProduct>)
    @Query("SELECT * FROM FavoriteProduct")
    suspend fun getAllFavoriteProduct(): List<FavoriteProduct>
}


@Dao
interface VendorDao {
    @Insert
    suspend fun insert(vendor: Vendor)
    @Query("SELECT count(*) FROM Vendor")
    suspend fun count(): Int
    @Query("SELECT * FROM Vendor")
    suspend fun getVendor(): Vendor
}
