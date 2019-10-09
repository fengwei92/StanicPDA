package com.stanic.pda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stanic.pda.R
import com.stanic.pda.bean.AgencyBean

class AgencyAdapter(
    private val context: Context,
    private val list: ArrayList<AgencyBean.DataBean>
) : RecyclerView.Adapter<AgencyAdapter.AgencyHolder>() {

    var selectListener: OnSelectedListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): AgencyHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_product, p0, false)
        return AgencyHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: AgencyHolder, p1: Int) {
        p0.textView.text = list[p1].name
        p0.textView.setOnClickListener {
            selectListener?.setSelected(list[p1])
        }
    }

    fun setSelectedListener(selectListener: OnSelectedListener) {
        this.selectListener = selectListener
    }

    inner class AgencyHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.tv_product_name)
    }


    interface OnSelectedListener {
        fun setSelected(agencyBean: AgencyBean.DataBean)
    }

}