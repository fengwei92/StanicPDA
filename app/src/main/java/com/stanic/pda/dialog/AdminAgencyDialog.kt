package com.stanic.pda.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.alibaba.fastjson.JSONArray
import com.android.volley.VolleyError
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.adapter.TreeAdapter
import com.stanic.pda.bean.TreePoint
import com.stanic.sjty_pda.util.VolleyUtil
import kotlinx.android.synthetic.main.dialog_admin_agency.*


class AdminAgencyDialog(context:Context) : BaseDialog(context){
    private var adapter: TreeAdapter? = null
    private val pointList = ArrayList<TreePoint>()
    private val pointMap = HashMap<String, TreePoint>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_admin_agency)
        initView()
        initData()
    }

    private fun initView(){

    }

    private fun initData(){
        try {
            val url = "http://192.168.1.222:8083/pdaout/listallBytree"
            VolleyUtil.get(url,object : VolleyUtil.OnResponse<String>{
                override fun onMap(map: HashMap<String, String>) {
                    map["project"] = StanicManager.stanicManager.projectCode!!
                    map["agencyid"] = StanicManager.stanicManager.userAgencyId!!

                }

                override fun onSuccess(response: String) {
                    Log.d("httpResponse", response)
                    val jsonArray = JSONArray.parseArray(response)
                    arrayToList(jsonArray)


                }



                override fun onError(error: VolleyError) {

                }
            })
        }catch (e: Exception){
            Toast.makeText(context,"获取经销商列表失败,错误代码:5001",Toast.LENGTH_SHORT).show()
        }

    }

    /**
     * jsonArray转list
     */
    private fun arrayToList(jsonArray: JSONArray) {
        for (i in 0 until jsonArray.size){
            val jsonObject = jsonArray.getJSONObject(i)
            val ID = jsonObject.getString("id")
            val NAME = jsonObject.getString("name")
            val PARENTID = jsonObject.getString("pId")
            val treePoint = TreePoint(ID,NAME,PARENTID,"0",1)
            pointList.add(treePoint)

        }
        updateData()

        adapter = TreeAdapter(context,pointList,pointMap)
        lv_admin_agency.adapter = adapter
    }


    private fun updateData(){
        for (i in pointList.iterator()){
            pointMap.put(i.id,i)
        }

    }

}