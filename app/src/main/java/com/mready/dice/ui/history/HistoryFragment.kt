package com.mready.dice.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mready.dice.R
import com.mready.dice.ui.MainActivity

class HistoryFragment : Fragment() {

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    private lateinit var toolbar: Toolbar
    private lateinit var rvHistory: RecyclerView
    private lateinit var historyAdapter: HistoryAdapter
    private lateinit var emptyHistoryTitle: TextView
    private lateinit var emptyHistoryParagraph: TextView
    private lateinit var emptyHistoryDice: ImageView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getViewsReferences(view)

        toolbar.setNavigationOnClickListener {
            (requireActivity() as MainActivity).navigateBack()
        }

        val historyData = (requireActivity() as MainActivity).getDiceValues()
        if (historyData.size == 0) {
            emptyHistoryParagraph.visibility = View.VISIBLE
            emptyHistoryTitle.visibility = View.VISIBLE
            emptyHistoryDice.visibility = View.VISIBLE
        }

        historyAdapter = HistoryAdapter(historyData)
        rvHistory.adapter = historyAdapter
    }

    private fun getViewsReferences(view: View) {
        toolbar = view.findViewById(R.id.toolbar)
        rvHistory = view.findViewById(R.id.rv_history)
        emptyHistoryTitle = view.findViewById(R.id.tv_empty_history_title)
        emptyHistoryParagraph = view.findViewById(R.id.tv_empty_history_paragraph)
        emptyHistoryDice = view.findViewById(R.id.iv_empty_history_dice)
    }

}