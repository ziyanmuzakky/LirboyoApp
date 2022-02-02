package com.example.lirboyoapp.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.lirboyoapp.ModelDataItem
import com.example.lirboyoapp.activities.DetailActivity
import com.example.lirboyoapp.databinding.ActivityMainBinding
import com.example.lirboyoapp.databinding.ItemLayoutBinding
import com.google.android.material.shape.RoundedCornerTreatment

class AdapterData(private val data: List<ModelDataItem>) :
    RecyclerView.Adapter<AdapterData.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterData.ViewHolder {
        val v = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = data?.size ?: 0
    override fun onBindViewHolder(holder: AdapterData.ViewHolder, position: Int) {
        holder.bind(data?.get(position))
    }

    class ViewHolder(private val itemBinding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(get: ModelDataItem?) {


            Glide.with(itemView.context)
                .load(get?.image)
                .apply(
                    RequestOptions()
                        .transform(RoundedCorners(16))
                )
                .into(itemBinding.image)
            itemBinding.textTitle.text = get?.title!!
            itemBinding.textDate.text = get?.date!!
            itemBinding.container.setOnClickListener {
                val intent = Intent(itemView.context, DetailActivity::class.java)
                intent.putExtra("title", get.title)
                intent.putExtra("content", get.content)
                intent.putExtra("date", get.date)
                intent.putExtra("image", get.image)
                itemView.context.startActivity(intent)
            }

        }
    }
}



