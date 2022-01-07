package com.kyhgroupd.ponggroupd.highscorefragments

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

class GolfFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_golf, container, false)
        val rv = view.findViewById<RecyclerView>(R.id.rvScoreListBreakout)

        this.updateRecyclerView(rv)
        return view
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun updateRecyclerView(rv: RecyclerView) {
        val scoreList = DataManager.loadScoreList("golf")

        rv.layoutManager = LinearLayoutManager(activity)
        rv.itemAnimator = DefaultItemAnimator()

        val adapter = CustomAdapter(scoreList)
        rv.adapter = adapter
        adapter.notifyDataSetChanged()
    }
}