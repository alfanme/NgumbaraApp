package com.app.ngumbara

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.ngumbara.Common.Common
import com.app.ngumbara.Model.MyPlaces
import com.app.ngumbara.Model.Results
import com.app.ngumbara.Remote.IGoogleAPIService
import com.app.ngumbara.databinding.ActivityNearbyBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.StringBuilder

class NearbyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNearbyBinding
    private lateinit var nearbyList: ArrayList<Results>
    private lateinit var mService: IGoogleAPIService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNearbyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mService = Common.googleApiService
        val latitude = this.intent.getDoubleExtra("latitude", 0.0)
        val longitude = this.intent.getDoubleExtra("longitude", 0.0)
        val typePlace = this.intent.getStringExtra("typePlace")

        binding.nearbyRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.nearbyRecyclerView.setHasFixedSize(true)
        nearbyList = arrayListOf<Results>()
        getNearByPlaceData(latitude, longitude, typePlace!!)
    }

    private fun getNearByPlaceData(latitude: Double, longitude: Double, typePlace: String) {

        val url = getUrl(latitude,longitude, typePlace)

        mService.getNearbyPlaces(url)
            .enqueue(object : Callback<MyPlaces> {
                override fun onResponse(
                    call: Call<MyPlaces>,
                    response: Response<MyPlaces>
                ) {
                    if (response.isSuccessful) {
                        val results = response.body()!!.results!!
                        for (i in results.indices) {
                            val nearbyPlaces = results[i]
                            nearbyList.add(nearbyPlaces)
                        }

                        binding.nearbyRecyclerView.adapter = NearbyAdapter(nearbyList)
                    }
                }

                override fun onFailure(call: Call<MyPlaces>, t: Throwable) {
                    Toast.makeText(baseContext, "" + t.message, Toast.LENGTH_SHORT).show()
                }
            }
        )
    }

    private fun getUrl(latitude: Double, longitude: Double, typePlace: String): String {
        val googlePlaceUrl = StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json")
        googlePlaceUrl.append("?location=$latitude,$longitude")
        googlePlaceUrl.append("&radius=10000") //10 km
        googlePlaceUrl.append("&keyword=$typePlace")
        googlePlaceUrl.append("&key=${getString(R.string.browser_key)}")
        return  googlePlaceUrl.toString()
    }
}