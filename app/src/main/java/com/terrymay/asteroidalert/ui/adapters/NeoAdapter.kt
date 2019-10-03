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

    override fun getView(position: Int, p1: View?, parent: ViewGroup?): View {
        var convertView = p1
        val viewHolder: ViewHolder
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.view_near_earth_object_data, parent, false)
            viewHolder = ViewHolder(convertView)
            viewHolder.setData(getItem(position), context)
            convertView.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
        }
        return convertView!!
    }
}

private class ViewHolder(view: View) {
    val neoName: TextView = view.findViewById(R.id.txt_name)
    val closeApproach: TextView = view.findViewById(R.id.txt_close_approach_date)
    val missDistance: TextView = view.findViewById(R.id.txt_miss_distance)
    val maxDiameter: TextView = view.findViewById(R.id.txt_max_diameter)
    val isDangerous: TextView = view.findViewById(R.id.txt_is_dangerous)

    fun setData(data: NearEarthObject, context: Context) {
        val dangerous = data.is_potentially_hazardous_asteroid.toString()
        val meters = data.estimated_diameter.meters.estimated_diameter_max.toString()
        val approachData = if (data.close_approach_data.isNotEmpty()) {
            data.close_approach_data[0].close_approach_date_full to data.close_approach_data[0].miss_distance.kilometers
        } else {
            context.getString(R.string.not_avail) to context.getString(R.string.not_avail)
        }
        neoName.text = data.name
        closeApproach.text = String.format(context.getString(R.string.close_date), approachData.first)
        missDistance.text = String.format(context.getString(R.string.miss_dist), approachData.second)
        maxDiameter.text = String.format(context.getString(R.string.max_diameter), meters)
        isDangerous.text = String.format(context.getString(R.string.is_dangerous), dangerous)
    }
}