package com.example.workoutapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_store_view.view.*

class ItemAdapter(val context: Context, val items: ArrayList<dateholder>) :
    RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Holds the TextView that will add each item to

        val id = view.tvrvid
        val date = view.tvrvDATE
        val llrv = view.llrvitems
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_store_view,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
       return  items.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items.get(position)

        holder.id.text = item.id.toString()
        holder.date.text = item.date


        if (position % 2 == 0) {
          holder.llrv.setBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
        }
    }


}