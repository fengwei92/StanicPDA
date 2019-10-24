package com.stanic.pda.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.bean.QueryBean
import com.stanic.pda.presenter.MvpPresenter
import com.stanic.pda.util.UrlList
import kotlinx.android.synthetic.main.activity_query_pdt.*

class QueryPdtActivity : BaseActivity(), MvpView, View.OnClickListener {
    private lateinit var mReceiver: BroadcastReceiver
    private var mvpPresenter: MvpPresenter? = null
    private var status = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_query_pdt)
        initView()
        mvpPresenter = MvpPresenter()
        mvpPresenter?.attachView(this)
        scanControl = true
        initReceiver()
    }

    private fun initView() {
        tv_query_pdt.setOnClickListener(this)
        tv_query_case.setOnClickListener(this)
        tv_query_parts.setOnClickListener(this)
        tv_query_support.setOnClickListener(this)
        tv_query_parts.setTextColor(resources.getColor(R.color.mian_color))
        iv_1.setBackgroundColor(resources.getColor(R.color.mian_color))
        status = 3
    }


    override fun showData(data: Any) {
        Log.d("httpResponse -> ", data.toString())
        val queryBean = JSONObject.parseObject(data.toString(), QueryBean::class.java)
        if (queryBean.code == 1) {
            if (queryBean.data == null) {
                Toast.makeText(this, "暂无数据", Toast.LENGTH_SHORT).show()
            } else {
                val productOutCount = if (queryBean.data.outcount == null){
                    "暂无"
                }else{
                    queryBean.data.outcount
                }


                var text = "产品名称：  ${queryBean.data.pdtname}" + "\r\n" +
                        "经销商：  ${queryBean.data.agency}" + "\r\n"
                if (!TextUtils.isEmpty(queryBean.data.barcode)) {
                    text += "盒码：  ${queryBean.data.barcode}" + "\r\n"
                }
                if (!TextUtils.isEmpty(queryBean.data.casecode)) {
                    text += "箱码：  ${queryBean.data.casecode}" + "\r\n"
                }
                if (!TextUtils.isEmpty(queryBean.data.storecode)) {
                    text += "托码：  ${queryBean.data.storecode}" + "\r\n"
                }
                text += "数量：  $productOutCount\r\n"
                tv_pdt_detail.text = text
            }
        } else {
            Toast.makeText(this, queryBean.msg, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.tv_query_pdt -> {
                val code = et_query_code.text.toString().trim()
                if (TextUtils.isEmpty(code)) {
                    Toast.makeText(this@QueryPdtActivity, "请扫描或输入条码", Toast.LENGTH_SHORT).show()
                } else {
                    queryPdt(code)
                }
            }
            R.id.tv_query_parts -> {
                cleanAllColor()
                status = 3
                tv_query_parts.setTextColor(resources.getColor(R.color.mian_color))
                iv_1.setBackgroundColor(resources.getColor(R.color.mian_color))
                et_query_code.setText("")
            }
            R.id.tv_query_case -> {
                cleanAllColor()
                status = 2
                tv_query_case.setTextColor(resources.getColor(R.color.mian_color))
                iv_2.setBackgroundColor(resources.getColor(R.color.mian_color))
                et_query_code.setText("")
            }
            R.id.tv_query_support -> {
                cleanAllColor()
                status = 1
                tv_query_support.setTextColor(resources.getColor(R.color.mian_color))
                iv_3.setBackgroundColor(resources.getColor(R.color.mian_color))
                et_query_code.setText("")
            }
        }
    }

    private fun cleanAllColor() {
        tv_query_parts.setTextColor(resources.getColor(R.color.text_color))
        iv_1.setBackgroundColor(resources.getColor(R.color.white))
        tv_query_case.setTextColor(resources.getColor(R.color.text_color))
        iv_2.setBackgroundColor(resources.getColor(R.color.white))
        tv_query_support.setTextColor(resources.getColor(R.color.text_color))
        iv_3.setBackgroundColor(resources.getColor(R.color.white))
    }

    private fun queryPdt(code: String) {
        val url = "${BASE_URL}pdaout/getPdtInfo"
        val map = HashMap<Any, Any>()
        map["project"] = StanicManager.stanicManager.projectCode!!
        map["codetype"] = status
        map["code"] = code
        mvpPresenter?.getData(map, url)
    }

    private fun initReceiver() {
        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val string = String(p1?.extras?.getByteArray(UrlList.KEY)!!)
                if (scanControl) {
                    scanControl = false
                    queryPdt(string)
                }
            }
        }
        val filter = IntentFilter()
        filter.addAction(UrlList.FILTER_STR)
        this.registerReceiver(mReceiver, filter)
    }
}