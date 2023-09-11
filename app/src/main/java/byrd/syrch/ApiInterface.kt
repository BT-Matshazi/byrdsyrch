package byrd.syrch

import byrd.syrch.mapData.hotspots
import byrd.syrch.mapData.hotspotsItem
import retrofit2.Call
import retrofit2.http.GET

interface ApiInterface {
    @GET("geo?lat=-26.072851&lng=27.962002&fmt=json")
    fun getHotspots(): Call<List<hotspotsItem>>
}