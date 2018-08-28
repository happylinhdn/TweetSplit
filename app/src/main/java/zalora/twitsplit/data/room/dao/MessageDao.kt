package zalora.twitsplit.data.room.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import io.reactivex.Flowable
import io.reactivex.Single
import zalora.twitsplit.data.room.entity.MessageModel

@Dao
interface MessageDao {

    @get:Query("SELECT * FROM message_model ORDER BY _group_key ASC, _message ASC")
    val allMessage: Flowable<List<MessageModel>>

    @get:Query("SELECT * FROM message_model ORDER BY _created DESC LIMIT 1")
    val lastMessage: Single<MessageModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMessage(vararg messageModels: MessageModel)
}
