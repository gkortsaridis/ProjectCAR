package gr.gkortsaridis.dvla_ves_api

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DvlaVehiclesApi {

    var apiToken = ""

    fun getVehicleDetails(registrationNumber: String, listener: VehicleDetailsListener) {

        val disposable = DvlaInternetApi.api.getVehicleDetails(VehicleDetailsBody(registrationNumber = registrationNumber), apiKey = apiToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                { listener.onVehicleDetailsLoaded(it) },
                { listener.onVehicleDetailsLoaded(VehicleDetailsResponse(registrationNumber = registrationNumber)) }
            )
    }
}