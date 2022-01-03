package com.kyhgroupd.ponggroupd.activitys

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kyhgroupd.ponggroupd.CustomAdapter
import com.kyhgroupd.ponggroupd.DataManager
import com.kyhgroupd.ponggroupd.R

class BreakoutFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_breakout, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.rvScoreList)

        this.updateRecyclerView(rv)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecyclerView(rv: RecyclerView) {
        val scoreList = DataManager.loadScoreList()

        rv.layoutManager = LinearLayoutManager(activity)
        rv.itemAnimator = DefaultItemAnimator()

        val adapter = CustomAdapter(scoreList)
        rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}