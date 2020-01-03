package com.stanic.pda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stanic.pda.R
import com.stanic.pda.bean.AgencyBean
import com.stanic.pda.bean.ChildOrderBean

class ChildOrderAdapter(
    private val context: Context,
    private val list: ArrayList<ChildOrderBean.DataBean>
) : RecyclerView.Adapter<ChildOrderAdapter.ChildOrderHolder>() {

    var selectListener: OnSelectedListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ChildOrderHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_product, p0, false)
        return ChildOrderHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ChildOrderHolder, p1: Int) {
        p0.textView.text = "产品名称：" + list[p1].pdtname + "\r\n" +  "计划数量：" + list[p1].plannum + "\r\n"
        p0.textView.setOnClickListener {
            selectListener?.setSelected(list[p1])
        }
    }

    fun setSelectedListener(selectListener: OnSelectedListener) {
        this.selectListener = selectListener
    }

    inner class ChildOrderHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.tv_product_name)
    }


    interface OnSelectedListener {
        fun setSelected(childeOrderBean: ChildOrderBean.DataBean)
    }

}