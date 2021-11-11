package com.example.test_eklavya.mentee

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test_eklavya.R


class MyMentorsRVAdapter(private val listener: MyMentorClicked): RecyclerView.Adapter<MyMentorsViewHolder>(){
    private val items: ArrayList<MyMentorsInfo> = ArrayList()
    private lateinit var imgViewProfile: ImageView
    private lateinit var imgChat:ImageView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMentorsViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_mentors, parent, false)
        val viewHolder = MyMentorsViewHolder(view)
        imgViewProfile = view.findViewById(R.id.imgProfile)
        imgChat = view.findViewById(R.id.imgchat)

        imgViewProfile.setOnClickListener {
            listener.onViewProfileClicked(items[viewHolder.adapterPosition])
        }
        imgChat.setOnClickListener {
            listener.onChatClicked(items[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MyMentorsViewHolder, position: Int) {
        val currentItem = items[position]
        holder.nameView.text = "Name:${currentItem.name}"
        holder.companyView.text = "Company:${currentItem.companyName}"
        holder.designationView.text = "Designation:${currentItem.designation}"
        holder.fieldView.text = "Field:${currentItem.field}"
    }
    fun updateMyMentors(updatedMyMentors: ArrayList<MyMentorsInfo>) {
        items.clear()
        items.addAll(updatedMyMentors)
        notifyDataSetChanged()
    }

}
class MyMentorsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameView:TextView = itemView.findViewById(R.id.tvName)
    val companyView:TextView = itemView.findViewById(R.id.tvCompany)
    val designationView:TextView = itemView.findViewById(R.id.tvDesignation)
    val fieldView:TextView = itemView.findViewById(R.id.tvField)
}

interface MyMentorClicked {
    fun onViewProfileClicked(item:MyMentorsInfo)
    fun onChatClicked(item:MyMentorsInfo)
}