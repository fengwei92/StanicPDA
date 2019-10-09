package com.stanic.pda.dialog

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.android.volley.VolleyError
import com.stanic.pda.adapter.AgencyAdapter
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.bean.AgencyBean
import com.stanic.sjty_pda.util.VolleyUtil
import kotlinx.android.synthetic.main.dialog_agency.*



class AgencyDialog(context:Context) : BaseDialog(context) , AgencyAdapter.OnSelectedListener{
    private var dialogSelectListener : DialogSelectListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_agency)
        initView()
        initData()
    }

    private fun initView(){
        rv_agency.layoutManager = LinearLayoutManager(context)
    }

    private fun initData(){
        val url = "${BASE_URL}pdaout/queryAllAgency"
        VolleyUtil.get(url,object : VolleyUtil.OnResponse<String>{
            override fun onMap(map: HashMap<String, String>) {
                map["project"] = StanicManager.stanicManager.projectCode!!
                map["agencyid"] = StanicManager.stanicManager.userAgencyId!!
            }

            override fun onSuccess(response: String) {
                Log.d("httpResponse",response)
                val bean = JSONObject.parseObject(response,AgencyBean::class.java)
                if (bean.code == 0){
                    val dataBean = bean.data as ArrayList
                    setAdapter(dataBean)
                }else{
                    Toast.makeText(context,bean.msg,Toast.LENGTH_SHORT).show()
                }
            }

            override fun onError(error: VolleyError) {

            }

        })
    }

    private fun setAdapter(dataBean : ArrayList<AgencyBean.DataBean>){

        val agencyAdapter = AgencyAdapter(context, dataBean)
        agencyAdapter.setSelectedListener(this)
        rv_agency.adapter = agencyAdapter
    }

    /**
     * @param agencyBean 选择栏目
     */
    override fun setSelected(agencyBean: AgencyBean.DataBean) {
        dialogSelectListener?.setDailogSelect(agencyBean)
    }

    fun setSelectListener(listener : DialogSelectListener){
        this.dialogSelectListener = listener
    }

    interface DialogSelectListener{
        fun setDailogSelect(agencyBean: AgencyBean.DataBean)
    }


}