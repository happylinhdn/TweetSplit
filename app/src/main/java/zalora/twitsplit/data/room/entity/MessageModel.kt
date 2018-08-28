package zalora.twitsplit.data.room.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

import java.util.Date

import zalora.twitsplit.utils.CommonUtils

@Entity(tableName = "message_model")
class MessageModel(@field:ColumnInfo(name = "_message")
                   var message: String?, @field:ColumnInfo(name = "_created")
                   var created: Long, @field:ColumnInfo(name = "_group_key")
                   var groupKey: String?) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    var id: Int = 0

    val longStringCreated: String
        get() {
            val d = Date(created)
            return CommonUtils.getLongStringFrom(d)
        }

    val shortStringCreated: String
        get() {
            val d = Date(created)
            return CommonUtils.getShortStringFrom(d)
        }
}
