package gr.gkortsaridis.dvla_ves_api

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DvlaVehiclesApi {

    fun setupWithApiToken(apiToken: String, useProductionEnvironment: Boolean = true) {
        this.apiTokenPrivate = apiToken
        this.IS_PRODUCTION_ENABLED_PRIVATE = useProductionEnvironment
    }

    val IS_PRODUCTION_ENABLED: Boolean get() { return IS_PRODUCTION_ENABLED_PRIVATE }
    val apiToken: String get() { return apiTokenPrivate }

    private var IS_PRODUCTION_ENABLED_PRIVATE = true
    private var apiTokenPrivate = ""

    fun getVehicleDetails(registrationNumber: String, listener: VehicleDetailsListener) {
        val disposable = DvlaInternetApi.api.getVehicleDetails(body = VehicleDetailsBody(registrationNumber = registrationNumber), apiKey = apiTokenPrivate)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                {
                    if(it.errors == null) {
                        val vehicleDetails = VehicleDetails(
                            registrationNumber = it.registrationNumber,
                            taxStatus = it.taxStatus ?: "",
                            taxDueDate = it.taxDueDate ?: "",
                            artEndDate = it.artEndDate ?: "",
                            motStatus = it.motStatus ?: "",
                            motExpiryDate = it.motExpiryDate ?: "",
                            make = it.make ?: "",
                            monthOfFirstDvlaRegistration = it.monthOfFirstDvlaRegistration ?: "",
                            monthOfFirstRegistration = it.monthOfFirstRegistration ?: "",
                            yearOfManufacture = it.yearOfManufacture ?: -1,
                            engineCapacity = it.engineCapacity ?: -1,
                            co2Emissions = it.co2Emissions ?: -1,
                            fuelType = it.fuelType ?: "",
                            markedForExport = it.markedForExport ?: false,
                            colour = it.colour ?: "",
                            typeApproval = it.typeApproval?: "",
                            wheelplan = it.wheelplan ?: "",
                            revenueWeight = it.revenueWeight ?: -1,
                            realDrivingEmissions = it.realDrivingEmissions ?: "",
                            dateOfLastV5CIssued = it.dateOfLastV5CIssued ?: "",
                            euroStatus = it.euroStatus ?: ""
                        )
                        listener.onVehicleDetailsLoaded(vehicleDetails)
                    } else {
                        listener.onVehicleDetailsError(it.errors)
                    }
                },
                {
                    it.printStackTrace()
                    val error = VehicleDetailsError(
                        status = "ERROR",
                        code = "ERROR",
                        title = it.cause.toString(),
                        detail = it.localizedMessage
                    )
                    val errors = ArrayList<VehicleDetailsError>()
                    errors.add(error)
                    listener.onVehicleDetailsError(errors)
                }
            )
    }
}