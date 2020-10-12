package gr.gkortsaridis.dvla_ves_api

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

object DvlaVehiclesApi {

    var IS_PRODUCTION_ENABLED = true
    var apiToken = ""

    fun getVehicleDetails(registrationNumber: String, listener: VehicleDetailsListener) {

        Log.i("TOKEN", apiToken)

        val disposable = DvlaInternetApi.api.getVehicleDetails(body = VehicleDetailsBody(registrationNumber = registrationNumber), apiKey = apiToken)
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe (
                {
                    if(it.errors == null) {
                        val vehicleDetails = VehicleDetails(
                            registrationNumber = it.registrationNumber,
                            taxStatus = it.taxStatus,
                            taxDueDate = it.taxDueDate,
                            artEndDate = it.artEndDate,
                            motStatus = it.motStatus,
                            motExpiryDate = it.motExpiryDate,
                            make = it.make,
                            monthOfFirstDvlaRegistration = it.monthOfFirstDvlaRegistration,
                            monthOfFirstRegistration = it.monthOfFirstRegistration,
                            yearOfManufacture = it.yearOfManufacture,
                            engineCapacity = it.engineCapacity,
                            co2Emissions = it.co2Emissions,
                            fuelType = it.fuelType,
                            markedForExport = it.markedForExport,
                            colour = it.colour,
                            typeApproval = it.typeApproval,
                            wheelplan = it.wheelplan,
                            revenueWeight = it.revenueWeight,
                            realDrivingEmissions = it.realDrivingEmissions,
                            dateOfLastV5CIssued = it.dateOfLastV5CIssued,
                            euroStatus = it.euroStatus
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