package com.stanic.pda.dialog

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.android.volley.VolleyError
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.bean.ProductBean
import com.stanic.pda.adapter.ProductAdapter
import com.stanic.sjty_pda.util.VolleyUtil


import kotlinx.android.synthetic.main.dialog_product.*


class ProductDialog(context:Context) : BaseDialog(context) , ProductAdapter.OnSelectedListener{
    private var productListener : ProductSelectListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_product)
        initView()
        initData()
    }

    private fun initView(){
        rv_product.layoutManager = LinearLayoutManager(context)
    }

    private fun initData(){
        val url = "${BASE_URL}pda/queryAllProductionForPda"

        VolleyUtil.get(url,object : VolleyUtil.OnResponse<String>{
            override fun onMap(map: HashMap<String, String>) {
                map["project"] = StanicManager.stanicManager.projectCode!!
            }

            override fun onSuccess(response: String) {
                Log.d("testMsg",response)
                val bean = JSONObject.parseObject(response,ProductBean::class.java)
                if (bean.code == 1){
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

    private fun setAdapter(dataBean : ArrayList<ProductBean.DataBean>){
        val productAdapter = ProductAdapter(context, dataBean)
        productAdapter.setSelectedListener(this)
        rv_product.adapter = productAdapter
    }

    override fun setSelected(productBean: ProductBean.DataBean) {
        productListener?.setProductSelect(productBean)
    }

    fun setSelectListener(listener : ProductSelectListener){
        this.productListener = listener
    }

    interface ProductSelectListener{
        fun setProductSelect(productBean: ProductBean.DataBean)
    }

}