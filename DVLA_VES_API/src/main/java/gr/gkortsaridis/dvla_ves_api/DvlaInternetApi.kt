package gr.gkortsaridis.dvla_ves_api

import android.util.Log
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import java.util.concurrent.TimeUnit

object DvlaInternetApi {

    private const val NETWORK_TIMEOUT_SECONDS = 90L
    private const val DVLA_PROD_BASE_URL = "https://driver-vehicle-licensing.api.gov.uk/vehicle-enquiry/"

    private val dvlaClient = OkHttpClient.Builder()
            .connectTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(NETWORK_TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .build()

    private val retrofitDvlaProd = Retrofit.Builder()
        .client(dvlaClient)
        .baseUrl(DVLA_PROD_BASE_URL)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofitDvlaProd.create(DVLAInterface::class.java)

    interface DVLAInterface {
        @POST("v1/vehicles")
        fun getVehicleDetails(@Body body: VehicleDetailsBody, @Header("x-api-key") apiKey: String): Observable<VehicleDetailsResponse>
    }

}