package com.app.ngumbara

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.ngumbara.Model.Results
import com.bumptech.glide.Glide
import java.lang.StringBuilder

class NearbyAdapter(private  val nearbyList: ArrayList<Results>) :
    RecyclerView.Adapter<NearbyAdapter.NearbyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NearbyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.template_nearby_item,
            parent,
            false)
        return NearbyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: NearbyViewHolder, position: Int) {
        val currentItem = nearbyList[position]

        val photoReference = currentItem.photos?.get(0)?.photo_reference
        val photoUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/photo")
        photoUrl.append("?maxwidth=400")
        photoUrl.append("&photo_reference=$photoReference")
        photoUrl.append("&key=${holder.itemView.context.getString(R.string.browser_key)}")

        Glide.with(holder.itemView.context).load(photoUrl.toString()).into(holder.thumbnailImage)
        holder.placeName.text = currentItem.name
    }

    override fun getItemCount(): Int {
        return nearbyList.size
    }

    class NearbyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val thumbnailImage: ImageView = itemView.findViewById(R.id.thumbnail_image)
        val placeName: TextView = itemView.findViewById(R.id.place_name)

    }

}