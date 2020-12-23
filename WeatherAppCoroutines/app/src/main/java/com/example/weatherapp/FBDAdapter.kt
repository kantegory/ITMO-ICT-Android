package com.example.weatherapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import com.squareup.picasso.Picasso

class FBDAdapter (private val FBDNumber: Int, private val FBDList: List<FBD>)
    : RecyclerView.Adapter<FBDAdapter.FBDViewHolder>()  {
    class FBDViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView)  {
        // получаем доступ ко всем элементам внутри нашей карточки
        private val FBDTime : TextView = itemView.findViewById(R.id.forecastBDTime)
        private val FBDTemp : TextView = itemView.findViewById(R.id.forecastBDTemp)
        private val FBDImg : ImageView = itemView.findViewById(R.id.forecastBDImg)


        fun bind(index: Int, list: List<FBD>) {
            // FBDImg.setImageResource(list[index].img)
            FBDTemp.text = list[index].temp
            FBDTime.text = list[index].day

            val currIcon = Picasso.get()
                .load("https://openweathermap.org/img/wn/${list[index].img}@2x.png")
                .fit()
                .centerCrop()
                .into(FBDImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FBDViewHolder {
        val context : Context = parent.context

        val layoutIdForFBDItem : Int = R.layout.forecast_by_day_item

        val inflater : LayoutInflater = LayoutInflater.from(context)

        val view : View = inflater.inflate(layoutIdForFBDItem, parent, false)

        return FBDViewHolder(view)
    }

    override fun getItemCount(): Int {
        return FBDNumber
    }

    override fun onBindViewHolder(holder: FBDViewHolder, position: Int) {
        holder.bind(position, FBDList)
    }
}
