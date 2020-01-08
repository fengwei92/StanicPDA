package com.stanic.pda.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.adapter.CancelOutAdapter
import com.stanic.pda.bean.CancelOutBean
import com.stanic.pda.bean.MenuBean
import com.stanic.pda.presenter.MvpPresenter
import com.stanic.pda.util.CodeToName
import com.stanic.pda.util.UrlList
import kotlinx.android.synthetic.main.activity_cancel_out.*
import kotlinx.android.synthetic.main.activity_cancel_out.iv_1
import kotlinx.android.synthetic.main.activity_cancel_out.iv_2
import kotlinx.android.synthetic.main.activity_cancel_out.iv_3
import kotlinx.android.synthetic.main.activity_cancel_out.tv_case
import kotlinx.android.synthetic.main.activity_cancel_out.tv_parts
import kotlinx.android.synthetic.main.activity_cancel_out.tv_support


class CancelOutActivity : BaseActivity(), MvpView, View.OnClickListener {
    var outstatus = 0 //1 按垛出库，2 按箱出库，3 按盒出库
    private var mvpPresenter: MvpPresenter? = null
    private lateinit var mReceiver: BroadcastReceiver
    private val list = ArrayList<CancelOutBean>()
    private var currentCode = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel_out)
        mvpPresenter = MvpPresenter()
        mvpPresenter?.attachView(this)
        scanControl = true
        initView()
        initReceiver()
        initData()
    }

    private fun initView() {
        tv_parts.setOnClickListener(this)
        tv_case.setOnClickListener(this)
        tv_support.setOnClickListener(this)
        tv_submit.setOnClickListener(this)
        rv_detail.layoutManager = LinearLayoutManager(this)
        iv_1.setBackgroundColor(resources.getColor(R.color.mian_color))
        tv_parts.setTextColor(resources.getColor(R.color.mian_color))
        outstatus = 3
    }

    private fun initReceiver() {
        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val string = String(p1?.extras?.getByteArray(UrlList.KEY)!!)
                if (scanControl) {
                    scanControl = false
                    et_code.setText(string)
                    cancelOut(string)
                }
            }
        }
        val filter = IntentFilter()
        filter.addAction(UrlList.FILTER_STR)
        this.registerReceiver(mReceiver, filter)
    }

    private fun initData() {
        val outPut = intent.getSerializableExtra("cancelOutPutStr") as? MenuBean
        val outPutStr = outPut?.nameList
        if (outPutStr!!.contains(CodeToName.cancelPartsOutNum.toString())) {
            tv_parts.visibility = View.VISIBLE
            iv_1.visibility = View.VISIBLE
        } else {
            tv_parts.visibility = View.GONE
            iv_1.visibility = View.GONE
        }
        if (outPutStr!!.contains(CodeToName.cancelBoxOutNum.toString())) {
            tv_case.visibility = View.VISIBLE
            iv_2.visibility = View.VISIBLE
        } else {
            tv_case.visibility = View.GONE
            iv_2.visibility = View.GONE
        }
        if (outPutStr!!.contains(CodeToName.cancelSupportOutNum.toString())) {
            tv_support.visibility = View.VISIBLE
            iv_3.visibility = View.VISIBLE
        } else {
            tv_support.visibility = View.GONE
            iv_3.visibility = View.GONE
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_parts -> {
                setSelectedColor(0)
                outstatus = 3
            }
            R.id.tv_case -> {
                setSelectedColor(1)
                outstatus = 2
            }
            R.id.tv_support -> {
                setSelectedColor(2)
                outstatus = 1
            }
            R.id.tv_submit -> {
                val code = et_code.text.toString().trim()
                if (TextUtils.isEmpty(code)){
                    Toast.makeText(this@CancelOutActivity,"请扫描或输入条码",Toast.LENGTH_SHORT).show()
                }else{
                    cancelOut(code)
                }
            }
        }

    }

    private fun setSelectedColor(position: Int) {
        cleanAllColor()
        when (position) {
            0 -> {
                iv_1.setBackgroundColor(resources.getColor(R.color.mian_color))
                tv_parts.setTextColor(resources.getColor(R.color.mian_color))
            }

            1 -> {
                iv_2.setBackgroundColor(resources.getColor(R.color.mian_color))
                tv_case.setTextColor(resources.getColor(R.color.mian_color))
            }
            2 -> {
                iv_3.setBackgroundColor(resources.getColor(R.color.mian_color))
                tv_support.setTextColor(resources.getColor(R.color.mian_color))
            }
        }

    }

    private fun cleanAllColor() {
        iv_1.setBackgroundColor(resources.getColor(R.color.white))
        iv_2.setBackgroundColor(resources.getColor(R.color.white))
        iv_3.setBackgroundColor(resources.getColor(R.color.white))
        tv_parts.setTextColor(resources.getColor(R.color.text_color))
        tv_case.setTextColor(resources.getColor(R.color.text_color))
        tv_support.setTextColor(resources.getColor(R.color.text_color))
    }

    private fun cancelOut(code: String) {
        currentCode = code
        val projectCode = StanicManager.stanicManager.projectCode
        val url = "${BASE_URL}pdaout/cancelout"
        val map = HashMap<Any, Any>()
        when (outstatus) {
            0 -> {
                Toast.makeText(this, "请选择出库类别", Toast.LENGTH_SHORT).show()
                return
            }
            1 -> map["storecode"] = code
            2 -> map["casecode"] = code
            3 -> map["barcode"] = code

        }
        map["project"] = projectCode!!
        map["userid"] = StanicManager.stanicManager.userId!!
        map["outstatus"] = outstatus
        mvpPresenter?.getData(map, url)
    }


    override fun showData(data: Any) {
        //取消列表展示
        Log.d("httpResponse ->", data.toString())
        val cancelOutBean = JSONObject.parseObject(data.toString(), CancelOutBean::class.java)
        if (cancelOutBean.code == 1){
            cancelOutBean.setsCode(currentCode)
            list.clear()
            list.add(cancelOutBean)
            setAdapter()
        }else{
            Toast.makeText(this,cancelOutBean.msg,Toast.LENGTH_SHORT).show()
        }

    }

    private fun setAdapter(){
        val cancelOutAdapter = CancelOutAdapter(this,list)
        rv_detail.adapter = cancelOutAdapter

    }
}