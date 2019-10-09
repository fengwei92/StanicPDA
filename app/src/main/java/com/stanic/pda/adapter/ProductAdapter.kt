package com.stanic.pda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.stanic.pda.R
import com.stanic.pda.bean.ProductBean

class ProductAdapter(private val context : Context, private val list : ArrayList<ProductBean.DataBean>) : RecyclerView.Adapter<ProductAdapter.ProductHolder>() {

    var selectListener : OnSelectedListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ProductHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.item_product,p0,false)
        return ProductHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(p0: ProductHolder, p1: Int) {
       p0.textView.text = list[p1].pdtname
        p0.textView.setOnClickListener {
            selectListener?.setSelected(list[p1])
        }
    }

    fun setSelectedListener(selectListener : OnSelectedListener){
        this.selectListener = selectListener
    }

    inner class ProductHolder(view : View) : RecyclerView.ViewHolder(view){
        val textView = view.findViewById<TextView>(R.id.tv_product_name)
    }


    interface OnSelectedListener{
        fun setSelected(productBean : ProductBean.DataBean)
    }

}