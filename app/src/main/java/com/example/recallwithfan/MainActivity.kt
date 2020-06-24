package com.example.recallwithfan

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity" //untuk melihat log


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d(TAG, "Oncreate : Init")

        AndroidNetworking.initialize(applicationContext)
        aksiButton()

    }

    private fun aksiButton() {
        btnSave.setOnClickListener(View.OnClickListener {
           var title = Etitle.text.toString()
            var desc = Edesc.text.toString()
            if(title != "" && desc != ""){
                tambahData(title, desc)
                Etitle.setText("")
                Edesc.setText("")
            }else{
                Toast.makeText(applicationContext, "Semua data harus diisi !", Toast.LENGTH_SHORT).show()
            }

        })
        btnShow.setOnClickListener{
            val intent = Intent(this@MainActivity, ShowActivity::class.java)
            startActivity(intent)
        }
    }

    private fun tambahData(title: String, desc: String) {
        AndroidNetworking.post("http://192.168.57.1/android_api/public/api/add")
            .addBodyParameter("title", title)
            .addBodyParameter("description", desc)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener{
                override fun onResponse(response: JSONObject?) {
                    if(response!!["code"].toString() == "1") {
                        Toast.makeText(
                            this@MainActivity,
                            response!!["message"].toString(),
                            Toast.LENGTH_SHORT
                        ).show();
                    }else{
                        Toast.makeText(this@MainActivity,"Data gagal ditambahkan", Toast.LENGTH_SHORT).show();
                    }
                }
                override fun onError(anError: ANError?) {
                    Log.d("error", anError.toString())
                    Toast.makeText(applicationContext, "errornya : " + anError.toString(), Toast.LENGTH_SHORT).show()
                }

            })
    }

}
