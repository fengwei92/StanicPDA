package com.stanic.pda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stanic.pda.R
import com.stanic.pda.bean.CaseRelationBean

class CaseRelationAdapter(
    private val context: Context,
    private var list: ArrayList<CaseRelationBean>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val HEAD_COUNT = 1
        private const val TYPE_HEAD = 0
        private const val TYPE_CONTENT = 1
    }

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        if (p1 == TYPE_HEAD) {
            val headView = inflater.inflate(R.layout.item_store_case_count_head, p0, false)
            return CaseRelationHeaderHolder(headView)
        } else {
            val contentView = inflater.inflate(R.layout.item_case_box_count, p0, false)
            return CaseRelationHolder(contentView)
        }

    }

    override fun getItemCount(): Int {
        return list.size + HEAD_COUNT
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        when (p0) {
            is CaseRelationHolder -> {
                p0.tvStackCode.text = list[p1-1].stackCode
                p0.tvCount.text = "${list[p1-1].data}"
                p0.tvCaseCode.text = list[p1-1].caseCode
            }
        }

    }

    private fun isHead(position: Int): Boolean {
        return HEAD_COUNT != 0 && position == 0
    }

    override fun getItemViewType(position: Int): Int {
        if (HEAD_COUNT != 0 && position == 0) {
            return TYPE_HEAD
        }
        return TYPE_CONTENT
    }

    inner class CaseRelationHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvStackCode = view.findViewById<TextView>(R.id.tv_1)
        val tvCount = view.findViewById<TextView>(R.id.tv_3)
        val tvCaseCode = view.findViewById<TextView>(R.id.tv_2)
    }

    inner class CaseRelationHeaderHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

}