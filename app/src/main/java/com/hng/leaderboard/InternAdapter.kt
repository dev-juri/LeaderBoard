package com.hng.leaderboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_interns.view.*

class InternAdapter(
    private val interns: ArrayList<InternModel> = ArrayList()):RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {
    var internFilterList = ArrayList<InternModel>()
    init {
        internFilterList = interns
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return InternViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_interns, parent, false)
        )
    }

    override fun getItemCount(): Int = internFilterList.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is InternViewHolder ->{
                holder.bind(internFilterList[position])
            }
        }
    }


    class InternViewHolder(item: View): RecyclerView.ViewHolder(item){
        val name: TextView = item.intern_name
        val slackUserName = item.slack_name
        val track = item.intern_track
        val score = item.intern_point

        fun bind(intern: InternModel){
            name.text = intern.name
            slackUserName.text = intern.slackUsername
            track.text = intern.track
            score.text = intern.point
        }
    }

    override fun getFilter(): Filter {
        return object: Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val internSearch = p0.toString()
                if(internSearch.isEmpty()){
                    internFilterList = interns
                } else{
                    val result = ArrayList<InternModel>()
                    for (row in interns){
                        result.add(row)
                    }
                    internFilterList = result
                }
                val filterResult = FilterResults()
                filterResult.values = internFilterList
                return filterResult

            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {

                internFilterList = p1?.values as ArrayList<InternModel>
                notifyDataSetChanged()
            }

        }
    }
}