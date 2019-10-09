package com.stanic.pda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stanic.pda.R
import com.stanic.pda.bean.CancelRelationCaseBean

class CancelCaseRelationAdapter(
    private val context: Context,
    private var list: ArrayList<CancelRelationCaseBean>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    companion object {
        private const val HEAD_COUNT = 1
        private const val TYPE_HEAD = 0
        private const val TYPE_CONTENT = 1
    }


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(context)
        if (p1 == TYPE_HEAD) {
            val headView = inflater.inflate(R.layout.item_code_status_head, p0, false)
            return HeaderHolder(headView)
        } else {
            val contentView = inflater.inflate(R.layout.item_code_status, p0, false)
            return NormalHolder(contentView)
        }

    }

    override fun getItemCount(): Int {
        return list.size + HEAD_COUNT
    }

    override fun onBindViewHolder(p0: RecyclerView.ViewHolder, p1: Int) {
        when (p0) {
            is NormalHolder -> {
                p0.tvCode.text = list[p1-1].getsCode()
                p0.tvStatus.text = list[p1-1].msg
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (HEAD_COUNT != 0 && position == 0) {
            return TYPE_HEAD
        }
        return TYPE_CONTENT
    }

    inner class NormalHolder(view: View): RecyclerView.ViewHolder(view) {
        val tvCode = view.findViewById<TextView>(R.id.tv_1)
        val tvStatus = view.findViewById<TextView>(R.id.tv_2)
    }

    inner class HeaderHolder(view: View) : RecyclerView.ViewHolder(view) {

    }
}