package com.stanic.pda.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.adapter.DayoutAdapterAdapter
import com.stanic.pda.bean.DayOutBean
import com.stanic.pda.presenter.MvpPresenter
import kotlinx.android.synthetic.main.activity_today_count.*

class TodayCountActivity : BaseActivity(), MvpView {
    private var mvpPresenter: MvpPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_count)
        mvpPresenter = MvpPresenter()
        mvpPresenter?.attachView(this)
        initView()
        getTodayCount()
    }

    private fun initView() {
        rv_today_count.layoutManager = LinearLayoutManager(this)
    }

    private fun getTodayCount() {
        val url = "${BASE_URL}pdaout/todayCount"
        val map = HashMap<Any, Any>()
        map["project"] = StanicManager.stanicManager.projectCode!!
        map["userid"] = StanicManager.stanicManager.userId!!
        mvpPresenter?.getData(map, url)
    }

    override fun showData(data: Any) {
        Log.d("httpResponse ->", data.toString())
        val dayOutBean = JSONObject.parseObject(data.toString(), DayOutBean::class.java)
        val code = dayOutBean.code
        if (code == 1) {
            val list = dayOutBean.data as ArrayList
            setAdapter(list)
        } else {
            Toast.makeText(this, dayOutBean.msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapter(list: ArrayList<DayOutBean.DataBean>){
        val dayOutAdapter = DayoutAdapterAdapter(this,list)
        rv_today_count.adapter = dayOutAdapter
    }
}