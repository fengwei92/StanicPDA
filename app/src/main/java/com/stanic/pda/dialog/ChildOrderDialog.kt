package com.stanic.pda.dialog

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.android.volley.VolleyError
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.adapter.ChildOrderAdapter
import com.stanic.pda.bean.ChildOrderBean
import com.stanic.pda.bean.OrderBean
import com.stanic.sjty_pda.util.VolleyUtil
import kotlinx.android.synthetic.main.dialog_child_order.*


class ChildOrderDialog(context:Context,private val orderBean: OrderBean.DataBean) : BaseDialog(context) , ChildOrderAdapter.OnSelectedListener{
    private var dialogSelectListener : DialogSelectListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_child_order)
        initView()
        initData()
    }

    private fun initView(){
        rv_child_order.layoutManager = LinearLayoutManager(context)
    }

    private fun initData(){
        try {
            val url = "${BASE_URL}pdaout/getsuborders"
            VolleyUtil.get(url,object : VolleyUtil.OnResponse<String>{
                override fun onMap(map: HashMap<String, String>) {
                    map["project"] = StanicManager.stanicManager.projectCode!!
                    map["orderid"] = orderBean.id
                }

                override fun onSuccess(response: String) {
                    Log.d("httpResponse",response)
                    val bean = JSONObject.parseObject(response,ChildOrderBean::class.java)
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
        }catch (e: Exception){
            Toast.makeText(context,"获取经销商列表失败,错误代码:5001",Toast.LENGTH_SHORT).show()
        }

    }

    private fun setAdapter(dataBean : ArrayList<ChildOrderBean.DataBean>){
        val childOrderAdapter = ChildOrderAdapter(context, dataBean)
        childOrderAdapter.setSelectedListener(this)
        rv_child_order.adapter = childOrderAdapter
    }

    /**
     * @param agencyBean 选择栏目
     */
    override fun setSelected(childOrderBean: ChildOrderBean.DataBean) {
        dialogSelectListener?.setDailogSelect(childOrderBean)
    }

    fun setSelectListener(listener : DialogSelectListener){
        this.dialogSelectListener = listener
    }

    interface DialogSelectListener{
        fun setDailogSelect(childOrderBean: ChildOrderBean.DataBean)
    }


}