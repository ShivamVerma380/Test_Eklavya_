package com.example.test_eklavya.mentor

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.test_eklavya.Login
import com.example.test_eklavya.MainActivity
import com.example.test_eklavya.OkHttpRequestAuth
import com.example.test_eklavya.R
import com.example.test_eklavya.chat.ChatActivity
import com.example.test_eklavya.mentee.*
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import org.json.JSONArray
import org.json.JSONException
import java.io.IOException

class MyMentees : AppCompatActivity(), MyMenteeClicked {
    private lateinit var btnViewReq: ImageButton
    var token: String? = null
    var userId: String? = null

    private lateinit var mAdapter: MyMenteesRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_mentees)
        btnViewReq = findViewById(R.id.ViewRequests)
        val extras = intent.extras
        token = extras?.getString("token")
        userId = extras?.getString("_id")
        btnViewReq.setOnClickListener {
            val MyMentors = Intent(this@MyMentees, MentorMainActivity::class.java)
            MyMentors.putExtra("token", "$token")
            MyMentors.putExtra("userId", "$userId")
            startActivity(MyMentors)
        }
        val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.myMenteesRV)
        recyclerView.layoutManager = LinearLayoutManager(this)
        fetchData()
        mAdapter = MyMenteesRVAdapter(this)
        recyclerView.adapter = mAdapter

    }

    private fun fetchData() {
        val url = "https://eklavya1.herokuapp.com/api/myMentees"
        Log.d("fetchDataCalled", "fetchDataCalled")
        var client = OkHttpClient()
        var request = OkHttpRequestAuth(client)
        request.GET(url, token!!, object : Callback {
            override fun onResponse(call: Call?, response: okhttp3.Response) {
                val responseData = response.body()?.string()
                Log.d("responseData", "$responseData")
                runOnUiThread {
                    try {
                        Log.d("In try block", "In try block")
                        //var json = JSONObject(responseData)
                        //Log.d("Successfull_Request_Mentee", "$json")
                        //var mentorsJsonArray = JSONArray(json)
                        var menteesJsonArray = JSONArray(responseData)
                        val menteesArray = ArrayList<MyMenteesInfo>()
                        for (i in 0 until menteesJsonArray.length()) {
                            val jsonObject = menteesJsonArray.getJSONObject(i)
                            val mentee = MyMenteesInfo(
                                jsonObject.getString("_id"),
                                jsonObject.getString("name"),
                                jsonObject.getString("email"),
                                jsonObject.getString("role"),
                                jsonObject.getString("collegeName"),
                                "Comp Eng",
                                jsonObject.getString("field")
                            )
                            menteesArray.add(mentee)
                            Log.d("mentor", "$mentee")
                        }
                        mAdapter.updateMyMentees(menteesArray)
                        Log.d("mentorsArray", "$menteesArray")
                    } catch (e: JSONException) {
                        Log.d("In Catch block", "$e")
                        e.printStackTrace()
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onViewProfileClicked(item: MyMenteesInfo) {
        Toast.makeText(applicationContext, "View Profile clicked", Toast.LENGTH_SHORT).show()
        val viewProfileIntent = Intent(this@MyMentees, MenteeProfile::class.java)
        viewProfileIntent.putExtra("token", "$token")
        viewProfileIntent.putExtra("_id", "$userId")
        viewProfileIntent.putExtra("name", "${item.name}")
        viewProfileIntent.putExtra("role", "${item.role}")
        viewProfileIntent.putExtra("course", "${item.course}")
        viewProfileIntent.putExtra("college", "${item.collegeName}")
        viewProfileIntent.putExtra("email", "${item.email}")
        viewProfileIntent.putExtra("field", "${item.field}")
        startActivity(viewProfileIntent)
    }

    override fun onChatClicked(item: MyMenteesInfo) {
        Toast.makeText(applicationContext, "Chat was  clicked", Toast.LENGTH_SHORT).show()
        Toast.makeText(applicationContext, "Chat item clicked", Toast.LENGTH_SHORT).show()
        val chatIntent = Intent(this@MyMentees, ChatActivity::class.java)
        chatIntent.putExtra("token", "$token")
        chatIntent.putExtra("_id", "$userId")
        chatIntent.putExtra("to", "${item.userId}")
        startActivity(chatIntent)
    }

    fun logoutFunc(view: android.view.View) {
        val sharedPref = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString("email_key", null)
        editor.putString("password_key", null)
        editor.putString("token_key", null)
        editor.putString("user_id", null)
        editor.putString("user_role", null)
        editor.apply()

        val intent = Intent(applicationContext, MainActivity::class.java)
        startActivity(intent)
    }


}