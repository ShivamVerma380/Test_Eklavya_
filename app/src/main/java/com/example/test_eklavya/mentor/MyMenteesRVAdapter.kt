package com.example.test_eklavya.mentor


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test_eklavya.R
import com.example.test_eklavya.mentee.MyMentorsInfo


class MyMenteesRVAdapter(private val listener: MyMenteeClicked): RecyclerView.Adapter<MyMenteesViewHolder>(){
    private val items: ArrayList<MyMenteesInfo> = ArrayList()
    private lateinit var imgViewProfile: ImageView
    private lateinit var imgChat:ImageView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyMenteesViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_my_mentees, parent, false)
        val viewHolder = MyMenteesViewHolder(view)
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

    override fun onBindViewHolder(holder: MyMenteesViewHolder, position: Int) {
        val currentItem = items[position]
        holder.nameView.text = "Name:${currentItem.name}"
        holder.collegeView.text = "Company:${currentItem.collegeName}"
        holder.courseView.text = "Designation:${currentItem.course}"
        holder.fieldView.text = "Field:${currentItem.field}"
    }
    fun updateMyMentees(updatedMyMentees: ArrayList<MyMenteesInfo>) {
        items.clear()
        items.addAll(updatedMyMentees)
        notifyDataSetChanged()
    }

}
class MyMenteesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameView:TextView = itemView.findViewById(R.id.tvName)
    val collegeView:TextView = itemView.findViewById(R.id.tvCollege)
    val courseView:TextView = itemView.findViewById(R.id.tvCourse)
    val fieldView:TextView = itemView.findViewById(R.id.tvField)
}

interface MyMenteeClicked {
    fun onViewProfileClicked(item: MyMenteesInfo)
    fun onChatClicked(item: MyMenteesInfo)
}