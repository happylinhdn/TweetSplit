package zalora.twitsplit.ui.message

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import java.util.ArrayList

import zalora.twitsplit.R
import zalora.twitsplit.data.room.entity.MessageModel

class HistoryMessageAdapter(private val context: Context) : RecyclerView.Adapter<HistoryMessageAdapter.MyViewHolder>() {
    private val listData: MutableList<MessageModel>

    init {
        this.listData = ArrayList()
    }

    fun initData(listData: List<MessageModel>) {
        this.listData.clear()
        this.listData.addAll(listData)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.item_message, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = listData[position]
        holder.bindView(item)
    }

    override fun getItemCount(): Int {
        return listData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtMessage: TextView
        var txtTime: TextView

        init {
            txtMessage = itemView.findViewById(R.id.txt_message)
            txtTime = itemView.findViewById(R.id.tv_time)
        }

        fun bindView(model: MessageModel) {
            txtMessage.text = model.message
            if (DateUtils.isToday(model.created)) {
                txtTime.text = model.shortStringCreated
            } else {
                txtTime.text = model.longStringCreated
            }
        }
    }
}
