package com.example.mostaql.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.mostaql.R
import com.example.mostaql.chat.ChatActivity
import com.example.mostaql.chat.RatingActivity
import com.example.mostaql.data.models.Service

class ServiceAdapter(
    private var list: List<Service>
) : RecyclerView.Adapter<ServiceAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.title)
        val desc: TextView = view.findViewById(R.id.description)
        val price: TextView = view.findViewById(R.id.price)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val chatBtn: Button = view.findViewById(R.id.chatBtn)
        val rateBtn: Button = view.findViewById(R.id.rateBtn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_service, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.title.text = item.title
        holder.desc.text = item.description
        holder.price.text = item.price

        // زر المحادثة
        holder.chatBtn.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("receiver_id", item.user_id)
            context.startActivity(intent)
        }

        // زر التقييم
        holder.rateBtn.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, RatingActivity::class.java)
            intent.putExtra("service_id", item.id)
            context.startActivity(intent)
        }

        // تقييم مبدئي (تقدر تربطه من Supabase لاحقًا)
        holder.ratingBar.rating = 3.5f
    }

    // تحديث البيانات
    fun update(newList: List<Service>) {
        list = newList
        notifyDataSetChanged()
    }
}