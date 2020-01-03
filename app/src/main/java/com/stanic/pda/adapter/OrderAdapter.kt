package com.stanic.pda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stanic.pda.R
import com.stanic.pda.bean.OrderBean


class OrderAdapter(
    private val context: Context,
    private val list: ArrayList<OrderBean.DataBean>
) : RecyclerView.Adapter<OrderAdapter.OrderHolder>() {

    var selectListener: OnSelectedListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): OrderHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_product, p0, false)
        return OrderHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: OrderHolder, p1: Int) {
        p0.textView.text = list[p1].ordernum
        p0.textView.setOnClickListener {
            selectListener?.setSelected(list[p1])
        }
    }

    fun setSelectedListener(selectListener: OnSelectedListener) {
        this.selectListener = selectListener
    }

    inner class OrderHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView = view.findViewById<TextView>(R.id.tv_product_name)
    }


    interface OnSelectedListener {
        fun setSelected(bean: OrderBean.DataBean)
    }

}