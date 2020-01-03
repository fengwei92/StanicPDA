package com.stanic.pda.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import android.widget.Toast
import com.alibaba.fastjson.JSONArray
import com.android.volley.VolleyError
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.util.Dept
import com.stanic.pda.util.Node
import com.stanic.pda.util.NodeHelper
import com.stanic.pda.util.NodeTreeAdapter
import com.stanic.sjty_pda.util.VolleyUtil
import kotlinx.android.synthetic.main.dialog_admin_agency.*
import org.json.JSONException
import java.util.*
import kotlin.collections.HashMap


class AdminAgencyDialog(context: Context) : BaseDialog(context) {
    private var mAdapter: NodeTreeAdapter? = null
    private val mLinkedList = LinkedList<Node<Any>>()
    private var mListView: ListView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_dept_layout)
        mListView = findViewById(R.id.id_tree)
        mAdapter = NodeTreeAdapter(context, mListView, mLinkedList)
        mListView?.adapter = mAdapter
        initData()
    }


    private fun initData() {

        try {
            val url = "${BASE_URL}pdaout/listallBytree"
            VolleyUtil.get(url, object : VolleyUtil.OnResponse<String> {
                override fun onMap(map: HashMap<String, String>) {
                    map["project"] = StanicManager.stanicManager.projectCode!!
                    map["agencyid"] = StanicManager.stanicManager.userAgencyId!!
                }

                override fun onSuccess(response: String) {
                    Log.d("httpResponse", response)
                    try {
                        val jsonArray = JSONArray.parseArray(response)
                        val data = ArrayList<Node<Any>>()
                        addOne(data,jsonArray)
                        mLinkedList.addAll(NodeHelper.sortNodes(data))
                        mAdapter?.notifyDataSetChanged()
                    } catch (e: java.lang.Exception) {
                        Toast.makeText(context, "获取经销商列表失败,错误代码:5001", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onError(error: VolleyError) {

                }
            })
        } catch (e: Exception) {
            Toast.makeText(context, "获取经销商列表失败,错误代码:5001", Toast.LENGTH_SHORT).show()
        }

    }

    private fun addOne(data: MutableList<Node<Any>> , jsonArray: JSONArray) {

        try {
            for (i in 0 until jsonArray.size) {
                val id = jsonArray.getJSONObject(i).getString("id")
                val pid = jsonArray.getJSONObject(i).getString("pId")
                val name = jsonArray.getJSONObject(i).getString("name")
                data.add(Dept(id, pid, name) as Node<Any>)
            }

        } catch (e: JSONException) {
            e.printStackTrace()
        }

    }

    /**
     * jsonArray转list
     */
    private fun arrayToList(jsonArray: JSONArray) {

        for (i in 0 until jsonArray.size) {
            val jsonObject = jsonArray.getJSONObject(i)
            val ID = jsonObject.getString("id")
            val NAME = jsonObject.getString("name")
            val PARENTID = jsonObject.getString("pId")


        }

    }




}