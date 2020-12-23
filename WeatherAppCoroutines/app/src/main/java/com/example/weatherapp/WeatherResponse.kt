package com.example.weatherapp

import androidx.room.*
import io.reactivex.Maybe

@Entity(tableName = "weather_response")
class WeatherResponseRoom(

    @PrimaryKey(autoGenerate = true) val id: Int,

    @ColumnInfo(name = "response") val response: String
)

@Dao
interface WeatherResponseDAO {
    @Query("SELECT * FROM weather_response")
    fun getAll(): Maybe<WeatherResponseRoom>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(weatherResponse: WeatherResponseRoom)

    @Query("DELETE FROM weather_response")
    fun deleteAll()
}

@Database(entities = [WeatherResponseRoom::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun weatherResponseDAO(): WeatherResponseDAO
}
