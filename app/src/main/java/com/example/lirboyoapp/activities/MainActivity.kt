package com.example.lirboyoapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lirboyoapp.ModelDataItem
import com.example.lirboyoapp.adapter.AdapterData
import com.example.lirboyoapp.databinding.ActivityMainBinding
import com.example.lirboyoapp.networking.BaseServiceAPI
import com.example.lirboyoapp.networking.NetworkConfig
import com.example.lirboyoapp.networking.UtilsAPI
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import org.json.JSONArray
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.create

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var data = ArrayList<ModelDataItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setData()

        binding.swipeRefresh.setOnRefreshListener {
            binding.swipeRefresh.isRefreshing = true
            setData()
        }
    }

    private fun setData() {
        binding.swipeRefresh.isRefreshing = false
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        val baseServiceAPI =
            NetworkConfig().getClient(applicationContext, UtilsAPI.baseUrlAPI)!!.create(
                BaseServiceAPI::class.java
            )
        val call: Call<JsonArray> = baseServiceAPI.getItemData()

        call.enqueue(object : Callback<JsonArray> {
            override fun onResponse(call: Call<JsonArray>, response: Response<JsonArray>) {
                try {
                    if (response.isSuccessful) {
                        val jsonArray = JSONArray(response.body().toString())

                        for (a in 0 until jsonArray.length()) {
                            val getResult = jsonArray.getJSONObject(a)

                            val modelDataItem = ModelDataItem()

                            val objectTitle = getResult.getJSONObject("title")
                            modelDataItem.title = objectTitle.getString("rendered")

                            val objectContent = getResult.getJSONObject("content")
                            modelDataItem.content = objectContent.getString("rendered")

                            modelDataItem.date = getResult.getString("modified")

                            val objectYoastHeadJson = getResult.getJSONObject("yoast_head_json")
                            val ogimage = objectYoastHeadJson.getJSONArray("og_image")
                            var image: String? = null

                            for (b in 0 until ogimage.length()) {
                                val getUrl = ogimage.getJSONObject(b)
                                image = getUrl.getString("url")
                            }

                            modelDataItem.image = image

                            data.add(modelDataItem)
                            val adapter = AdapterData(data)
                            binding.recyclerView.adapter = adapter

                        }
                    } else {
                        Toast.makeText(
                            applicationContext,
                            "Terjadi kesalahan. Silahkan coba lagi",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: Exception) {
                    Log.e("Error : ", e.message!!)
                }
            }

            override fun onFailure(call: Call<JsonArray>, t: Throwable) {
                Toast.makeText(applicationContext, "Tidak ada koneksi internet", Toast.LENGTH_SHORT)
                    .show()
            }
        })


    }
}