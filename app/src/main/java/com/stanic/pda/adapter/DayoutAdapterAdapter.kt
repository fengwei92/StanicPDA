package com.stanic.pda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stanic.pda.R
import com.stanic.pda.bean.DayOutBean

class DayoutAdapterAdapter(
    private val context: Context,
    private var list: ArrayList<DayOutBean.DataBean>
) : RecyclerView.Adapter<DayoutAdapterAdapter.NormalHolder>() {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): NormalHolder {
        val inflater = LayoutInflater.from(context)
        val contentView = inflater.inflate(R.layout.item_day_out, p0, false)
        return NormalHolder(contentView)
    }


    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: NormalHolder, p1: Int) {
        val bean = list[p1]
        val pdtName = bean.pdtname
        val agency = bean.agency
        val count = bean.outcount
        val order = bean.ordernum
        var text = ""
        if (!TextUtils.isEmpty(order)){
            text += "订单号： ${order} \r\n"
        }
        text += "经销商： ${agency} \n产品名称： ${pdtName} \n数量： ${count}"


        p0.tvDayOut.text = text
    }

    fun cleanAllData() {
        list.clear()
        notifyDataSetChanged()
    }

    inner class NormalHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvDayOut = view.findViewById<TextView>(R.id.tv_day_out)
    }

}