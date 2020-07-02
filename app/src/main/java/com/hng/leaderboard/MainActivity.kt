package com.hng.leaderboard

import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONArray
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var internList: ArrayList<InternModel>
    private lateinit var adapterClass: InternAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        internList = ArrayList()
        populate()
        adapterClass = InternAdapter(internList)


        board_recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = adapterClass
            setHasFixedSize(true)
        }

        search_box.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapterClass.filter.filter(p0)
                return false
            }

        })
    }

    private fun populate() {
        val json: String?
        try {
            val inputStream = assets.open("interns.json")
            json = inputStream.bufferedReader().use { it.readText() }

            val jsonarr = JSONArray(json)

            for (i in 0 until jsonarr.length()){

                val jsonObj = jsonarr.getJSONObject(i)

                val name = jsonObj.getString("name")
                val track = jsonObj.getString("track")
                val point = jsonObj.getString("point")
                val slackUsername = jsonObj.getString("slackUsername")

                val internDetails = InternModel(name, track, point, slackUsername)
                internList.add(internDetails)
                Log.i("INFO", internList.toString())
            }

        }
        catch (e: IOException){
            Log.e("JSON", e.toString())
        }
    }

}



