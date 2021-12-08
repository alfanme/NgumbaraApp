package com.app.ngumbara.Common

import com.app.ngumbara.Remote.IGoogleAPIService
import com.app.ngumbara.Remote.RetrofitClient

object Common {
    private val GOOGLE_API_URL="https://maps.googleapis.com/"

    val googleApiService:IGoogleAPIService
        get()=RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleAPIService::class.java)
}