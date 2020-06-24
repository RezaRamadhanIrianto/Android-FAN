package com.example.recallwithfan

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONArrayRequestListener
import kotlinx.android.synthetic.main.activity_show.*
import org.json.JSONArray
import org.json.JSONException


class ShowActivity : AppCompatActivity() {
    private val lists = ArrayList<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show)

        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        AndroidNetworking.initialize(getApplicationContext()); //inisialisasi FAN
        getData(); // pemanggilan fungsi get data

    }

    private fun getData() {
        //koneksi ke file read_all.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
        //koneksi ke file read_all.php, jika menggunakan localhost gunakan ip sesuai dengan ip kamu
        AndroidNetworking.post("http://192.168.57.1/android_api/public/api/show")
            .setPriority(Priority.LOW)
            .build()
            .getAsJSONArray(object : JSONArrayRequestListener {
                override fun onResponse(response: JSONArray) {
//                    Log.d(
//                        "Show Activity",
//                        "onResponse: $response"
//                    ) //untuk log pada onresponse
                    // do anything with response
                    run {
                        //mengambil data dari JSON array pada read_all.php
                        try {
                            for (i in 0 until response.length()) {
                                val data = response.getJSONObject(i)
                                //adding the product to product list
                                lists.add(
                                    Note(
                                        data.getInt("id"),  //"name:/String" diisi sesuai dengan yang di JSON pada read_all.php
                                        data.getString("title"),  //"name:/String" diisi sesuai dengan yang di JSON pada read_all.php
                                        data.getString("description")  //"name:/String" diisi sesuai dengan yang di JSON pada read_all.php
                                    )
                                )
                            }
                            Log.d("show", lists.toString())
                            //men inisialisasi adapter RecyclerView yang sudah kita buat sebelumnya
                            val adapter =
                                ListNoteAdapter(lists)
                            list.setAdapter(adapter) //menset adapter yang akan digunakan pada recyclerView


                            adapter.setOnItemClickCallback(object: ListNoteAdapter.OnItemClickCallback{
                                override fun onItemClicked(data: Note) {
                                    showSelected(data)
                                }

                            })
                        } catch (e: JSONException) {
                            e.printStackTrace()
                        }
                    }
                }

                override fun onError(error: ANError) {
                    Log.d("Show Activity", "onError: $error") //untuk log pada onerror
                    // handle error
                }
            })
    }

    private fun showSelected(data: Note) {
        Toast.makeText(applicationContext, "Anda Mengklik ${data.title}", Toast.LENGTH_SHORT).show()
    }

}
