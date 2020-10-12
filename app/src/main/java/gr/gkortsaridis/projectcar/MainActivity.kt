package gr.gkortsaridis.projectcar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import gr.gkortsaridis.dvla_ves_api.DvlaVehiclesApi
import gr.gkortsaridis.dvla_ves_api.VehicleDetailsListener
import gr.gkortsaridis.dvla_ves_api.VehicleDetailsResponse

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        DvlaVehiclesApi.apiToken = "3KqBAOt2I66r5TbLC0ebB8XJdPlW5pvv7brxSnCx"
        DvlaVehiclesApi.getVehicleDetails("YB67HYZ", object : VehicleDetailsListener{
            override fun onVehicleDetailsLoaded(response: VehicleDetailsResponse) {
                Log.i("VEHICLE", response.toString())
            }
        })

    }
}