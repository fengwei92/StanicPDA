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
import com.stanic.pda.adapter.AddLabelAdapter
import com.stanic.pda.bean.AddLabelBean
import com.stanic.pda.presenter.MvpPresenter
import com.stanic.pda.util.UrlList
import kotlinx.android.synthetic.main.activity_add_label.*

class AddLabelActivity : BaseActivity() , MvpView , View.OnClickListener{
    private var currentCode = ""
    private lateinit var mReceiver: BroadcastReceiver
    private var mvpPresenter : MvpPresenter? = null
    private val list =  ArrayList<AddLabelBean>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_label)
        mvpPresenter = MvpPresenter()
        mvpPresenter?.attachView(this)
        scanControl = true
        initView()
        initReceiver()
        setClickListener()
    }

    private fun initView(){
        rv_add_label_result.layoutManager = LinearLayoutManager(this)
        list.clear()
    }

    private fun setClickListener(){
        btn_add_label.setOnClickListener(this)
        btn_clean.setOnClickListener(this)
    }

    private fun initReceiver(){
        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val string = String(p1?.extras?.getByteArray(UrlList.KEY)!!)
                if (scanControl) {
                    var caseCode = et_add_case.text.toString().trim()
                    var boxCode = et_add_box.text.toString().trim()
                    if (TextUtils.isEmpty(caseCode)) {
                        caseCode = string
                        et_add_case.setText(caseCode)
                    }else if (TextUtils.isEmpty(boxCode)){
                        boxCode = string
                        et_add_box.setText(boxCode)
                        addLabel(boxCode,caseCode)
                    }
                }
            }
        }
        val filter = IntentFilter()
        filter.addAction(UrlList.FILTER_STR)
        this.registerReceiver(mReceiver, filter)
    }



    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btn_add_label -> {
                val boxCode = et_add_box.text.toString()
                val caseCode = et_add_case.text.toString()
                addLabel(boxCode,caseCode)
            }
            R.id.btn_clean -> {
                et_add_case.setText("")
                et_add_box.setText("")
            }
        }
    }

    override fun showData(data: Any) {
        scanControl = true
        Log.d("httpResponse->",data.toString())
        val addLabelBean = JSONObject.parseObject(data.toString(),AddLabelBean::class.java)
        addLabelBean.setsCode(currentCode)
        if (addLabelBean.code == 1){
            et_add_box.setText("")
            list.add(0,addLabelBean)
            setAdapter()
        }else{
            Toast.makeText(this,addLabelBean.msg,Toast.LENGTH_SHORT).show()
        }

    }

    private fun setAdapter(){
        val addLabelAdapter = AddLabelAdapter(this,list)
        rv_add_label_result.adapter = addLabelAdapter
    }

    /**
     * 补标
     */
    private fun addLabel(boxCode : String,caseCode: String){
        currentCode = boxCode
        scanControl = false
        if (TextUtils.isEmpty(boxCode)){
            Toast.makeText(this,"盒码不能为空",Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(caseCode)){
            Toast.makeText(this,"箱码不能为空",Toast.LENGTH_SHORT).show()
            return
        }
        val url = "${BASE_URL}pdaout/replabel"
        val map = HashMap<Any,Any>()
        map["project"] = StanicManager.stanicManager.projectCode!!
        map["barcode"] = boxCode
        map["casecode"] = caseCode
        map["userid"] = StanicManager.stanicManager.userId!!
        mvpPresenter?.getData(map,url)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }
}