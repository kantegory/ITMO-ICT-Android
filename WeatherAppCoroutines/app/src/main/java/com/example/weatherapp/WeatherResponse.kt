package com.example.weatherapp

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "weather_response")
class WeatherResponseRoom(

    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "response") val response: String
)

@Dao
interface WeatherResponseDAO {
    @Query("SELECT * FROM weather_response")
    fun getAll(): Flow<WeatherResponseRoom>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(weatherResponse: WeatherResponseRoom)

    @Query("DELETE FROM weather_response")
    suspend fun deleteAll()
}

@Database(entities = [WeatherResponseRoom::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherResponseDAO(): WeatherResponseDAO
}
