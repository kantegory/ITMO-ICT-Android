package com.example.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.squareup.picasso.Picasso

class FBTAdapter (private val FBTNumber: Int, private val FBTList: List<FBT>)
    : RecyclerView.Adapter<FBTAdapter.FBTViewHolder>()  {
        class FBTViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)  {
            // получаем доступ ко всем элементам внутри нашей карточки
            private val FBTTime : TextView = itemView.findViewById(R.id.forecastBTTime)
            private val FBTTemp : TextView = itemView.findViewById(R.id.forecastBTTemp)
            private val FBTImg : ImageView = itemView.findViewById(R.id.forecastBTImg)

            fun bind(index: Int, list: List<FBT>) {
                // FBTImg.setImageResource(list[index].img)
                FBTTemp.text = list[index].temp
                FBTTime.text = list[index].time

                val currIcon = Picasso.get()
                    .load("https://openweathermap.org/img/wn/${list[index].img}@2x.png")
                    .fit()
                    .centerCrop()
                    .into(FBTImg)
            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FBTViewHolder {
        val context : Context = parent.context

        val layoutIdForFBTItem : Int = R.layout.forecast_by_time_item

        val inflater : LayoutInflater = LayoutInflater.from(context)

        val view : View = inflater.inflate(layoutIdForFBTItem, parent, false)

        return FBTViewHolder(view)
    }

    override fun getItemCount(): Int {
        return FBTNumber
    }

    override fun onBindViewHolder(holder: FBTViewHolder, position: Int) {
        holder.bind(position, FBTList)
    }
}
