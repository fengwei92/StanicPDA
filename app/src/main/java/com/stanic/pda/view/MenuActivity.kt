package com.stanic.pda.view

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.stanic.pda.adapter.MenuAdapter
import com.stanic.pda.R
import com.stanic.pda.bean.MenuBean
import com.stanic.pda.util.CodeToName
import com.stanic.pda.util.SpacesItemDecoration
import kotlinx.android.synthetic.main.activity_menu.*


class MenuActivity : BaseActivity(), MvpView, View.OnClickListener, MenuAdapter.OnSelectedListener {
    override fun onClick(v: View?) {

    }

    override fun setSelectedNum(menuName: MenuBean) {
        val intent = Intent()
        when (menuName.name) {
            CodeToName.groupBox ->
                intent.setClass(this@MenuActivity, RelationCaseActivity::class.java)
            CodeToName.groupSupport ->
                intent.setClass(this@MenuActivity,RelationStackActivity::class.java)
            CodeToName.outPut -> {
                intent.setClass(this@MenuActivity, OutputActivity::class.java)
                intent.putExtra("outPutStr", menuName)
            }
            CodeToName.cancelOut -> {
                intent.setClass(this@MenuActivity, CancelOutActivity::class.java)
                intent.putExtra("cancelOutPutStr", menuName)
            }
            CodeToName.returnOut -> {
                intent.setClass(this@MenuActivity, ReturnOutActivity::class.java)
                intent.putExtra("returnOutPutStr", menuName)
            }
            CodeToName.cancelRelationCase -> {
                intent.setClass(this@MenuActivity, CancelCaseRelationActivity::class.java)
                intent.putExtra("cancelCaseRelation", menuName)
            }
            CodeToName.cancelRelationStack -> {
                intent.setClass(this@MenuActivity, CancelStackRelationActivity::class.java)
                intent.putExtra("cancelStackRelation", menuName)
            }
            CodeToName.pdtInquiry -> {
                intent.setClass(this@MenuActivity,QueryPdtActivity::class.java)
                intent.putExtra("queryPdt",menuName)
            }
            CodeToName.addMark -> {
                intent.setClass(this@MenuActivity,AddLabelActivity::class.java)
                intent.putExtra("addLabel",menuName)
            }
            CodeToName.dayOutStorage -> {
                intent.setClass(this@MenuActivity,TodayCountActivity::class.java)
                intent.putExtra("dayOut",menuName)
            }

        }
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initView()
        initData()
    }


    private fun initView() {
        val grideLayout = GridLayoutManager(this, 2)
        rv_menu.layoutManager = grideLayout
        rv_menu.addItemDecoration(SpacesItemDecoration(5, 20))
    }

    private fun initData() {
        val menuList = intent.getSerializableExtra("menuBeanList") as? ArrayList<MenuBean>
        setAdapter(menuList)

    }

    override fun showData(data: Any) {

    }

    private fun setAdapter(menuList: ArrayList<MenuBean>?) {
        val menuAdapter = MenuAdapter(this, menuList!!)
        rv_menu.adapter = menuAdapter
        menuAdapter.setSelectedListener(this)
    }


}