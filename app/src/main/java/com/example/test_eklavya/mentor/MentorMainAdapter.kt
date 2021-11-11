package com.example.test_eklavya.mentor


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.test_eklavya.R
import com.example.test_eklavya.mentee.MentorsInfo


class MentorMainAdapter(private val listener: MenteeClicked): RecyclerView.Adapter<MenteesViewHolder>(){
    private val items: ArrayList<MenteesInfo> = ArrayList()
//    private lateinit var imgApprove:ImageView
//    private lateinit var imgDecline:ImageView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenteesViewHolder{
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_mentee, parent, false)
        val viewHolder = MenteesViewHolder(view)
//        imgApprove = view.findViewById(R.id.approve)
//        imgDecline = view.findViewById(R.id.decline)
//        imgApprove.setOnClickListener{
//            listener.onItemApprove(items[viewHolder.adapterPosition])
//        }
//        imgDecline.setOnClickListener {
//            listener.onItemDecline(items[viewHolder.adapterPosition])
//        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: MenteesViewHolder, position: Int) {
        val currentItem = items[position]
        holder.nameView.text = "Name:${currentItem.name}"
        holder.collegeView.text = "College:${currentItem.collegeName}"
        holder.courseView.text = "Course:${currentItem.course}"
        holder.fieldView.text = "Field:${currentItem.field}"
        holder.statusView.text = "Status:${currentItem.status}"
        holder.imgApprove.setOnClickListener {
            listener.onItemApprove(currentItem)
            holder.imgApprove.visibility = View.GONE
            holder.imgDecline.visibility = View.GONE
            holder.statusView.text = "Status:Approved"
        }
        holder.imgDecline.setOnClickListener {
            listener.onItemDecline(currentItem)
            holder.imgDecline.visibility = View.GONE
            holder.imgApprove.visibility = View.GONE
            holder.statusView.text = "Status:Declined"
        }
    }
    fun updateMentees(updatedMentees: ArrayList<MenteesInfo>) {
        items.clear()
        items.addAll(updatedMentees)
        notifyDataSetChanged()
    }

}
class MenteesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameView:TextView = itemView.findViewById(R.id.tvName)
    val collegeView:TextView = itemView.findViewById(R.id.tvCollege)
    val courseView:TextView = itemView.findViewById(R.id.tvCourse)
    val fieldView:TextView = itemView.findViewById(R.id.tvField)
    val statusView:TextView = itemView.findViewById(R.id.tvStatus)
    val imgApprove:ImageView = itemView.findViewById(R.id.approve)
    val imgDecline:ImageView = itemView.findViewById(R.id.decline)
}

interface MenteeClicked {
    fun onItemApprove(item: MenteesInfo)
    fun onItemDecline(item:MenteesInfo)
}