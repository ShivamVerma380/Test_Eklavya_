package com.example.test_eklavya.chat

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.test_eklavya.R

class ChatRVAdapter(var userId:String): RecyclerView.Adapter<ChatViewHolder>() {
    private val items:ArrayList<ChatInfo> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_text_message, parent, false)
        val viewHolder = ChatViewHolder(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val currentItem = items[position]

        holder.messageView.text = currentItem.message


        if(currentItem.from==userId){
            holder.messageView.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            //holder.messageView.textColors = Color.parseColor("#00FF00")
            holder.messageView.setTextColor(Color.parseColor("#00FF00"))
        }else{
            holder.messageView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            holder.messageView.setTextColor(Color.parseColor("#FF6600"))
        }
        if(currentItem.to == userId){
            holder.messageView.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            holder.messageView.setTextColor(Color.parseColor("#FF6600"))
        }else{
            holder.messageView.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            holder.messageView.setTextColor(Color.parseColor("#00FF00"))
        }

    }

    override fun getItemCount(): Int{
        return items.size
    }
    fun updateChat(updatedChats: ArrayList<ChatInfo>) {
        items.clear()
        items.addAll(updatedChats)
        notifyDataSetChanged()
    }
    fun addChat(addChat:ChatInfo) {
        items.add(addChat)
        notifyDataSetChanged()
    }
}
class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val messageView:TextView = itemView.findViewById(R.id.tvMessage)
}
