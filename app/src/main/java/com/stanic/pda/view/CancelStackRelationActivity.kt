package com.stanic.pda.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.presenter.MvpPresenter
import com.stanic.pda.util.UrlList
import kotlinx.android.synthetic.main.activity_cancel_stack_relation.*

class CancelStackRelationActivity : BaseActivity() , MvpView , View.OnClickListener{
    private var currentIndex = 0
    private lateinit var mReceiver: BroadcastReceiver
    private var mvpPresenter : MvpPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel_stack_relation)
        mvpPresenter = MvpPresenter()
        mvpPresenter?.attachView(this)
        scanControl = true
        initView()
        initReceiver()
        setClickListener()
    }

    private fun initView(){
        rv_cancel_stack_relation.layoutManager = LinearLayoutManager(this)
        tv_case.setTextColor(resources.getColor(R.color.mian_color))
        iv_1.setBackgroundColor(resources.getColor(R.color.mian_color))
    }

    private fun setClickListener(){
        tv_case.setOnClickListener(this)
        tv_stack.setOnClickListener(this)
        tv_submit.setOnClickListener(this)
    }

    override fun showData(data: Any) {

    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.tv_case -> {
                if (currentIndex == 1){
                    currentIndex = 0
                    clearAllColor()
                    tv_case.setTextColor(resources.getColor(R.color.mian_color))
                    iv_1.setBackgroundColor(resources.getColor(R.color.mian_color))
                }
            }
            R.id.tv_stack -> {
                if (currentIndex == 0){
                    currentIndex = 1
                    clearAllColor()
                    tv_stack.setTextColor(resources.getColor(R.color.mian_color))
                    iv_2.setBackgroundColor(resources.getColor(R.color.mian_color))

                }
            }
            R.id.tv_submit -> {
                val code = et_input_code.text.toString().trim()
                if (TextUtils.isEmpty(code)){
                    Toast.makeText(this@CancelStackRelationActivity,"请扫描或输入条码",Toast.LENGTH_SHORT).show()
                    return
                }
                cancelCaseRelation(code)

            }
        }

    }

    private fun initReceiver(){
        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val string = String(p1?.extras?.getByteArray(UrlList.KEY)!!)
                if (scanControl) {
                    scanControl = false
                    cancelCaseRelation(string)
                }
            }
        }
        val filter = IntentFilter()
        filter.addAction(UrlList.FILTER_STR)
        this.registerReceiver(mReceiver, filter)
    }

    /**
     * 取消关联
     * @param code 箱码/盒码
     */
    private fun cancelCaseRelation(code : String){
        val url = "${BASE_URL}pdaout/cancelrelationcase"
        var codeType = 0
        val map = HashMap<Any,Any>()
        map["project"] = StanicManager.stanicManager.projectCode!!
        if (currentIndex == 0){
            map["barcode"] = code
            codeType = 2
        }else{
            map["casecode"] = code
            codeType = 1
        }
        map["userid"] = StanicManager.stanicManager.userId!!
        map["codetype"] = codeType
        mvpPresenter?.getData(map,url)
    }

    private fun clearAllColor(){
        iv_1.setBackgroundColor(resources.getColor(R.color.white))
        iv_2.setBackgroundColor(resources.getColor(R.color.white))
        tv_case.setTextColor(resources.getColor(R.color.text_color))
        tv_stack.setTextColor(resources.getColor(R.color.text_color))
    }
}