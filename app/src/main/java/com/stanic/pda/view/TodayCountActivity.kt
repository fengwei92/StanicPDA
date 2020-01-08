package com.stanic.pda.view

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.gingold.basislibrary.utils.BasisTimesUtils
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.adapter.DayoutAdapterAdapter
import com.stanic.pda.bean.DayOutBean
import com.stanic.pda.presenter.MvpPresenter
import kotlinx.android.synthetic.main.activity_today_count.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class TodayCountActivity : BaseActivity(), MvpView, View.OnClickListener {
    private var mvpPresenter: MvpPresenter? = null
    private var date = ""
    private var mYear = ""
    private var mMonth = ""
    private var mDay = ""
    private val mList: ArrayList<DayOutBean.DataBean> = ArrayList()
    private var dayOutAdapter = DayoutAdapterAdapter(this,mList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_today_count)
        mvpPresenter = MvpPresenter()
        mvpPresenter?.attachView(this)
        initView()
        getTodayCount("$mYear$mMonth$mDay")
    }

    private fun initView() {
        rv_today_count.layoutManager = LinearLayoutManager(this)
        tv_choose_date.setOnClickListener(this)
        date = BasisTimesUtils.getDeviceTimeOfYMD()
        val dateArray = date.split("-")
        try {
            mYear = dateArray[0]
            mMonth = dateArray[1]
            if (mMonth.length == 1){
                mMonth = "0$mMonth"
            }
            mDay = dateArray[2]
            if (mDay.length == 1){
                mDay = "0$mDay"
            }
            tv_choose_date.text = "${mYear}年${mMonth}月${mDay}日"
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    private fun getTodayCount(time: String) {
        val url = "${BASE_URL}pdaout/todayCount"
        val map = HashMap<Any, Any>()
        map["project"] = StanicManager.stanicManager.projectCode!!
        map["userid"] = StanicManager.stanicManager.userId!!
        map["qdate"] = time
        mvpPresenter?.getData(map, url)
    }

    override fun showData(data: Any) {
        Log.d("httpResponse ->", data.toString())
        val dayOutBean = JSONObject.parseObject(data.toString(), DayOutBean::class.java)
        val code = dayOutBean.code
        if (code == 1) {
            val list = dayOutBean.data as ArrayList
            mList.addAll(list)
            setAdapter()
        } else {
            Toast.makeText(this, dayOutBean.msg, Toast.LENGTH_SHORT).show()
        }
    }

    private fun setAdapter() {
        dayOutAdapter = DayoutAdapterAdapter(this, mList)
        rv_today_count.adapter = dayOutAdapter
    }


    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_choose_date -> {
                BasisTimesUtils.showDatePickerDialog(
                    TodayCountActivity@ this,
                    BasisTimesUtils.THEME_DEVICE_DEFAULT_LIGHT,
                    "请选择日期",
                    mYear.toInt(),
                    mMonth.toInt(),
                    mDay.toInt(),
                    object : BasisTimesUtils.OnDatePickerListener {
                        override fun onCancel() {

                        }

                        override fun onConfirm(year: Int, month: Int, dayOfMonth: Int) {
                            mYear = year.toString()
                            mMonth = month.toString()
                            mDay = dayOfMonth.toString()
                            if (mMonth.length == 1){
                                mMonth = "0$mMonth"
                            }
                            if (mDay.length == 1){
                                mDay = "0$mDay"
                            }
                            mList.clear()
                            dayOutAdapter.notifyDataSetChanged()
                            tv_choose_date.text = "${mYear}年${mMonth}月${mDay}日"
                            getTodayCount("$mYear$mMonth$mDay")
                        }

                    }
                )
            }
        }
    }
}