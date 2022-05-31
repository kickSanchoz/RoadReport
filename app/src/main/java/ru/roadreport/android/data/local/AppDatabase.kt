package ru.roadreport.android.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.roadreport.android.data.local.dao.DraftDAO
import ru.roadreport.android.data.local.models.DraftModelRoom
import ru.roadreport.android.utils.DATASTORE_NAME


@Database(
    entities = [DraftModelRoom::class],
    version = 3,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun draftDAO(): DraftDAO

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also {
                    instance = it
                }
            }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, DATASTORE_NAME)
                .fallbackToDestructiveMigration()
                .build()
    }
}