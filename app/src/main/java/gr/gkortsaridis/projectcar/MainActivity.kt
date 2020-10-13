package gr.gkortsaridis.projectcar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import gr.gkortsaridis.dvla_ves_api.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        DvlaVehiclesApi.setupWithApiToken( apiToken = "3KqBAOt2I66r5TbLC0ebB8XJdPlW5pvv7brxSnCx", useProductionEnvironment = true)
        Log.i("DVLA API SETUP WITH TOKEN", DvlaVehiclesApi.apiToken)

        loading.visibility = View.GONE

        search_btn.setOnClickListener {
            loading.visibility = View.VISIBLE
            val registrationNumber = registration_number_et.text.toString()
            DvlaVehiclesApi.getVehicleDetails(registrationNumber, object : VehicleDetailsListener{

                override fun onVehicleDetailsLoaded(vehicleDetails: VehicleDetails) {
                    loading.visibility = View.GONE
                    response_tv.text = vehicleDetails.toString()
                }

                override fun onVehicleDetailsError(error: ArrayList<VehicleDetailsError>) {
                    loading.visibility = View.GONE
                    response_tv.text = error.toString()
                }
            })

        }



    }
}