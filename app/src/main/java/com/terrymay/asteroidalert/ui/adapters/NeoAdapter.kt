package com.terrymay.asteroidalert.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.terrymay.asteroidalert.R
import com.terrymay.asteroidalert.models.feed.AsteroidFeedModel
import com.terrymay.asteroidalert.models.feed.NearEarthObject
import com.terrymay.asteroidalert.models.feed.NearEarthObjects

class NeoAdapter(private val context: Context, private val data: List<NearEarthObject>): BaseAdapter() {
    private val inflater: LayoutInflater
        = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getItemId(p0: Int): Long {
       return p0.toLong()
    }

    override fun getCount(): Int {
        return data.size
    }

    override fun getItem(p0: Int): NearEarthObject {
        return data[p0]
    }

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View {
        val dataItem = getItem(position)
        return view?.let {
            (it.tag as ViewHolder).apply {
                setData(dataItem)
            }.view
        } ?: run {
            inflater.inflate(R.layout.view_near_earth_object_data,parent, false).apply {
                tag = ViewHolder(this, dataItem)
            }
        }
    }
}

private class ViewHolder(val view: View, data: NearEarthObject) {
    val neoName: TextView = view.findViewById(R.id.txt_name)
    val closeApproach: TextView = view.findViewById(R.id.txt_close_approach_date)
    val missDistance: TextView = view.findViewById(R.id.txt_miss_distance)
    val maxDiameter: TextView = view.findViewById(R.id.txt_max_diameter)
    val isDangerous: TextView = view.findViewById(R.id.txt_is_dangerous)

    init {
        setData(data)
    }

    fun setData(data: NearEarthObject) {
        val context = view.context
        val dangerous = data.is_potentially_hazardous_asteroid.toString()
        val meters = data.estimated_diameter.meters.estimated_diameter_max.toString()
        // Pair<date of approach, distance of miss>
        val approachData = if (data.close_approach_data.isNotEmpty()) {
            data.close_approach_data[0].close_approach_date_full to data.close_approach_data[0].miss_distance.kilometers
        } else {
            // backup message if there is no data
            context.getString(R.string.not_avail) to context.getString(R.string.not_avail)
        }
        neoName.text = data.name
        val (approachDate, missDistanceKm) = approachData
        closeApproach.text = String.format(context.getString(R.string.close_date), approachDate)
        missDistance.text = String.format(context.getString(R.string.miss_dist), missDistanceKm)
        maxDiameter.text = String.format(context.getString(R.string.max_diameter), meters)
        isDangerous.text = String.format(context.getString(R.string.is_dangerous), dangerous)
    }
}