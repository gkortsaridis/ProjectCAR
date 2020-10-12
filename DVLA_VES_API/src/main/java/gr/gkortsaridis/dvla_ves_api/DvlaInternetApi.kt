package gr.gkortsaridis.dvla_ves_api

import gr.gkortsaridis.dvla_ves_api.DvlaVehiclesApi.IS_PRODUCTION_ENABLED
import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Url
import java.util.concurrent.TimeUnit

object DvlaInternetApi {

    private const val NETWORK_TIMEOUT_SECONDS = 90L

    private const val DVLA_PROD_BASE_URL = "https://driver-vehicle-licensing.api.gov.uk/vehicle-enquiry/"
    private const val DVLA_TEST_BASE_URL = "https://uat.driver-vehicle-licensing.api.gov.uk/vehicle-enquiry"

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
        @POST
        fun getVehicleDetails(@Url url: String = if(IS_PRODUCTION_ENABLED) DVLA_PROD_BASE_URL else DVLA_TEST_BASE_URL, @Body body: VehicleDetailsBody, @Header("x-api-key") apiKey: String): Observable<VehicleDetailsResponse>
    }

}