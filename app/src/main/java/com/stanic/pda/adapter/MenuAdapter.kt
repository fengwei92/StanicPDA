package com.stanic.pda.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.stanic.pda.R
import com.stanic.pda.bean.MenuBean

class MenuAdapter(private val context:Context,private val menuList:ArrayList<MenuBean>) : RecyclerView.Adapter<MenuAdapter.ViewHold>(){
    var selectListener : OnSelectedListener? = null

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHold {
        val inflater = LayoutInflater.from(context)
        return ViewHold(inflater.inflate(R.layout.item_menu,null))
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(p0: ViewHold, p1: Int) {
        p0.tvMenu.setImageResource(menuList[p1].logo)
        p0.tvMenu.setOnClickListener { v ->
            when(v?.id){
                R.id.iv_menu -> selectListener?.setSelectedNum(menuList[p1])
            }
        }
    }


    inner class ViewHold(view:View) : RecyclerView.ViewHolder(view){
        val tvMenu : ImageView = view.findViewById(R.id.iv_menu)
    }

    fun setSelectedListener(selectListener : OnSelectedListener){
        this.selectListener = selectListener
    }

    interface OnSelectedListener{
        fun setSelectedNum(menuName : MenuBean)
    }
}