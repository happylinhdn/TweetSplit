package zalora.twitsplit.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

import zalora.twitsplit.data.room.dao.MessageDao
import zalora.twitsplit.data.room.entity.MessageModel
import zalora.twitsplit.utils.AppConstants

@Database(entities = arrayOf(MessageModel::class), version = 1, exportSchema = false)
abstract class MyDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao

    companion object {

        private var INSTANCE: MyDatabase? = null


        fun getDatabase(context: Context): MyDatabase {
            if (INSTANCE == null) {
                synchronized(MyDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                MyDatabase::class.java, AppConstants.DB_NAME)
                                .fallbackToDestructiveMigration()
                                .build()

                    }
                }
            }
            return INSTANCE!!
        }
    }
}

