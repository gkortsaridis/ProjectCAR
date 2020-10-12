package gr.gkortsaridis.dvla_ves_api

data class VehicleDetailsBody( val registrationNumber: String )

data class VehicleDetailsResponse(
    val errors: ArrayList<VehicleDetailsError>? = null,

    val registrationNumber: String,
    val taxStatus: String = "",
    val taxDueDate: String = "",
    val artEndDate: String = "",
    val motStatus: String = "",
    val motExpiryDate: String = "",
    val make: String = "",
    val monthOfFirstDvlaRegistration: String = "",
    val monthOfFirstRegistration: String = "",
    val yearOfManufacture: Int = -1,
    val engineCapacity: Int = -1,
    val co2Emissions: Int = -1,
    val fuelType: String = "",
    val markedForExport: Boolean = false,
    val colour: String = "",
    val typeApproval: String = "",
    val wheelplan: String = "",
    val revenueWeight: Int = -1,
    val realDrivingEmissions: String = "",
    val dateOfLastV5CIssued: String = "",
    val euroStatus: String = ""
)

data class VehicleDetailsError(
    val status: String,
    val code: String,
    val title: String,
    val detail: String
)

data class VehicleDetails(
    val registrationNumber: String,
    val taxStatus: String = "",
    val taxDueDate: String = "",
    val artEndDate: String = "",
    val motStatus: String = "",
    val motExpiryDate: String = "",
    val make: String = "",
    val monthOfFirstDvlaRegistration: String = "",
    val monthOfFirstRegistration: String = "",
    val yearOfManufacture: Int = -1,
    val engineCapacity: Int = -1,
    val co2Emissions: Int = -1,
    val fuelType: String = "",
    val markedForExport: Boolean = false,
    val colour: String = "",
    val typeApproval: String = "",
    val wheelplan: String = "",
    val revenueWeight: Int = -1,
    val realDrivingEmissions: String = "",
    val dateOfLastV5CIssued: String = "",
    val euroStatus: String = ""
)

interface VehicleDetailsListener {
    fun onVehicleDetailsLoaded(vehicleDetails: VehicleDetails)
    fun onVehicleDetailsError(error: ArrayList<VehicleDetailsError>)
}