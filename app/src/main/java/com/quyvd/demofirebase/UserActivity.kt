package com.quyvd.demofirebase

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserActivity : AppCompatActivity() {
    var lvMain: ListView? = null
    var listCarModel: MutableList<CarModel>? = null
    var carAdapter: CarAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(
            findViewById(R.id.main)
        ) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lvMain = findViewById(R.id.listviewMain)

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(APIService.DOMAIN)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService: APIService = retrofit.create(APIService::class.java)

        val call: Call<List<CarModel>> = apiService.getCars()

        call.enqueue(object : Callback<List<CarModel>> {
            override fun onResponse(
                call: Call<List<CarModel>>,
                response: Response<List<CarModel>>
            ) {
                if (response.isSuccessful) {
                    listCarModel = response.body()?.toMutableList()

                    carAdapter = CarAdapter(applicationContext, listCarModel!!)

                    lvMain?.adapter = carAdapter
                }
            }

            override fun onFailure(call: Call<List<CarModel>>, t: Throwable) {
                Log.e("Main", t.message!!)
            }
        })

        findViewById<View>(R.id.btn_add).setOnClickListener {
            val xe = CarModel("Xe 1411", 2023, "Toyota", 1200.0)
            val callAddXe: Call<List<CarModel>> = apiService.addCar(xe)
            callAddXe.enqueue(object : Callback<List<CarModel>> {
                override fun onResponse(
                    call: Call<List<CarModel>>,
                    response: Response<List<CarModel>>
                ) {
                    if (response.isSuccessful) {
                        listCarModel!!.clear()
                        listCarModel!!.addAll(response.body()!!)
                        carAdapter!!.notifyDataSetChanged()
                    }
                }

                override fun onFailure(call: Call<List<CarModel>>, t: Throwable) {
                    Log.e("Main", t.message!!)
                }
            })
        }
    }
}