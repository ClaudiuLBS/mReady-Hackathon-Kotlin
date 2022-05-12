package com.mready.dice.ui.history

import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.mready.dice.R

class HistoryAdapter(private val historyList: List<Array<String?>>) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val sumText: TextView = view.findViewById(R.id.tv_sum)
        private val scoreText: TextView = view.findViewById(R.id.tv_score)
        private val doubleText: TextView = view.findViewById(R.id.tv_double)
        private val mainLayout: LinearLayout = view.findViewById(R.id.ll_history)

        private val primaryColor = ContextCompat.getColor(view.context, R.color.colorPrimary)
        fun bind(score: String?, sum:String?) {
            sumText.text = sum
            scoreText.text = score
            mainLayout.visibility = View.VISIBLE

            if (score!!.reversed() == score) {
                doubleText.visibility = View.VISIBLE
                sumText.setTextColor(primaryColor)
                scoreText.setTextColor(primaryColor)
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_history, parent, false)

        return HistoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(historyList[position][1], historyList[position][0])
    }

    override fun getItemCount(): Int = historyList.size


}