package com.example.test_eklavya.mentee

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_eklavya.OkHttpRequestAuth
import com.example.test_eklavya.R
import com.example.test_eklavya.chat.ChatActivity
import com.example.test_eklavya.mentor.MyMentees
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

class MyMentors : AppCompatActivity(), MyMentorClicked {
    private lateinit var btnSearchMentors: Button
    var token:String?=null
    var userId:String?=null
    private lateinit var mAdapter:MyMentorsRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_mentors)
        btnSearchMentors = findViewById(R.id.SearchMentors)
        val extras = intent.extras
        token = extras?.getString("token")
        userId = extras?.getString("_id")
        btnSearchMentors.setOnClickListener {
            val MyMentees = Intent(this@MyMentors,MenteeMainActivity::class.java)
            MyMentees.putExtra("token","$token")
            MyMentees.putExtra("userId","$userId")
            startActivity(MyMentees)
        }

        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.myMentorsRV)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = MyMentorsRVAdapter(this)
        recyclerView.adapter = mAdapter

    }

    private fun fetchData() {
        val url = "https://eklavya1.herokuapp.com/api/myMentors"
        Log.d("fetchDataCalled","fetchDataCalled")
        var client = OkHttpClient()
        var request = OkHttpRequestAuth(client)
        request.GET(url,token!!,object : Callback {
            override fun onResponse(call: Call?, response:okhttp3.Response) {
                val responseData = response.body()?.string()
                Log.d("responseData","$responseData")
                runOnUiThread {
                    try {
                        Log.d("In try block","In try block")
                        //var json = JSONObject(responseData)
                        //Log.d("Successfull_Request_Mentee", "$json")
                        //var mentorsJsonArray = JSONArray(json)
                        var mentorsJsonArray = JSONArray(responseData)
                        val mentorsArray = ArrayList<MyMentorsInfo>()
                        for (i in 0 until mentorsJsonArray.length()){
                            val jsonObject = mentorsJsonArray.getJSONObject(i)
                            val mentor = MyMentorsInfo(
                                jsonObject.getString("_id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("email"),
                                jsonObject.getString("role"),
                                jsonObject.getString("companyName"),
                                "SDE-1",
                                jsonObject.getString("field")
                            )
                            mentorsArray.add(mentor)
                            Log.d("mentor","$mentor")
                        }
                        mAdapter.updateMyMentors(mentorsArray)
                        Log.d("mentorsArray","$mentorsArray")
                    } catch (e: JSONException) {
                        Log.d("In Catch block","$e")
                        e.printStackTrace()
                    }
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onViewProfileClicked(item: MyMentorsInfo) {
        Toast.makeText(applicationContext,"View Profile clicked",Toast.LENGTH_SHORT).show()
        val viewProfileIntent = Intent(this@MyMentors,MentorProfile::class.java)
        viewProfileIntent.putExtra("token","$token")
        viewProfileIntent.putExtra("_id","$userId")
        viewProfileIntent.putExtra("name","${item.name}")
        viewProfileIntent.putExtra("role","${item.role}")
        viewProfileIntent.putExtra("designation","${item.designation}")
        viewProfileIntent.putExtra("company","${item.companyName}")
        viewProfileIntent.putExtra("email","${item.email}")
        viewProfileIntent.putExtra("field","${item.field}")
        startActivity(viewProfileIntent)
    }

    override fun onChatClicked(item: MyMentorsInfo) {
        Toast.makeText(applicationContext,"Chat item clicked",Toast.LENGTH_SHORT).show()
        val chatIntent = Intent(this@MyMentors, ChatActivity::class.java)
        chatIntent.putExtra("token","$token")
        chatIntent.putExtra("_id","$userId")
        chatIntent.putExtra("to","${item.userId}")
        startActivity(chatIntent)
    }
}