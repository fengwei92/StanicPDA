package com.stanic.pda.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.text.TextUtils
import android.view.View
import com.stanic.pda.R
import com.stanic.pda.fragment.StackEntryFragment
import com.stanic.pda.fragment.StackFictitiousFragment
import com.stanic.pda.util.UrlList
import kotlinx.android.synthetic.main.activity_relation_stack.*
import kotlinx.android.synthetic.main.fragment_relation_stack_entry.*
import kotlinx.android.synthetic.main.fragment_relation_stack_entry.et_case_code
import kotlinx.android.synthetic.main.fragment_relation_stack_fictitious.*


class RelationStackActivity : BaseActivity(), MvpView, View.OnClickListener {
    companion object{
        private const val CURRENT_FRAGMENT = "STATE_FRAGMENT_SHOW"
    }
    private var currentFragment = Fragment()
    private val fragments = ArrayList<Fragment>()
    private var fragmentManager: FragmentManager? = null
    private lateinit var mReceiver: BroadcastReceiver
    private var currentIndex = 0 //0:entryFragment 1:fictitiousFragment
    private lateinit var stackEntryFragment: StackEntryFragment
    private lateinit var stackFictitiousFragment: StackFictitiousFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relation_stack)
        scanControl = true
        initView()
        initReceiver()
        initFragment(savedInstanceState)
        setClickListener()
    }

    private fun setClickListener() {
        ll_entry.setOnClickListener(this)
        ll_fictitious.setOnClickListener(this)
    }

    private fun initView(){
        tv_1.setBackgroundColor(resources.getColor(R.color.mian_color))
        tv_stack_entry.setTextColor(resources.getColor(R.color.mian_color))
    }

    /**
     * 获得广播实例
     */
    private fun initReceiver() {
        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val string = String(p1?.extras?.getByteArray(UrlList.KEY)!!)
                if (scanControl) {
                    scanControl = false
                }
                if (currentIndex == 0) {
                    dealStackEntryFragment(string)
                } else {
                    dealStackFictitiousFragment(string)
                }

            }
        }
        val filter = IntentFilter()
        filter.addAction(UrlList.FILTER_STR)
        registerReceiver(mReceiver, filter)
    }

    /**
     * 处理实体托
     */
    private fun dealStackEntryFragment(code: String){
        val stackCode = stackEntryFragment.et_stack_code.text.toString().trim()
        var caseCode = stackEntryFragment.et_case_code.text.toString().trim()
        if (TextUtils.isEmpty(stackCode)) {
            stackEntryFragment.et_stack_code.setText(code)
        } else if (TextUtils.isEmpty(caseCode)) {
            stackEntryFragment.et_case_code.setText(code)
            caseCode = code
            stackEntryFragment.relationCase(caseCode,stackCode)
        }
    }

    /**
     * 处理虚拟托
     */
    private fun dealStackFictitiousFragment(code: String){
        stackFictitiousFragment.et_case_code.setText(code)
        stackFictitiousFragment.relationCase(code)
    }

    private fun initFragment(savedInstanceState: Bundle?) {
        fragmentManager = supportFragmentManager
        if (savedInstanceState != null) {
            currentIndex = savedInstanceState.getInt(CURRENT_FRAGMENT, 0)
            fragments.removeAll(fragments)
            fragments.add(fragmentManager?.findFragmentByTag("" + 0)!!)
            fragments.add(fragmentManager?.findFragmentByTag("" + 1)!!)
            restoreFragment()
        } else {
            stackEntryFragment = StackEntryFragment.instance
            stackFictitiousFragment = StackFictitiousFragment.instance
            fragments.add(stackEntryFragment)
            fragments.add(stackFictitiousFragment)
            showFragment()
        }
    }

    override fun showData(data: Any) {

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.ll_entry -> {
                if (currentIndex == 1) {
                    cleanAllColor()
                    currentIndex = 0
                    tv_1.setBackgroundColor(resources.getColor(R.color.mian_color))
                    tv_stack_entry.setTextColor(resources.getColor(R.color.mian_color))
                }
            }

            R.id.ll_fictitious -> {
                if (currentIndex == 0) {
                    cleanAllColor()
                    currentIndex = 1
                    tv_2.setBackgroundColor(resources.getColor(R.color.mian_color))
                    tv_stack_fictitious.setTextColor(resources.getColor(R.color.mian_color))
                }
            }
        }
        showFragment()
    }

    /**
     * 清楚所有颜色
     */
    private fun cleanAllColor() {
        tv_1.setBackgroundColor(resources.getColor(R.color.white))
        tv_stack_entry.setTextColor(resources.getColor(R.color.text_color))
        tv_2.setBackgroundColor(resources.getColor(R.color.white))
        tv_stack_fictitious.setTextColor(resources.getColor(R.color.text_color))
    }

    /**
     * 使用show() hide()切换页面
     * 显示fragment
     */
    private fun showFragment() {
        val transaction = fragmentManager?.beginTransaction()
        if (!fragments[currentIndex].isAdded) {
            transaction
                ?.hide(currentFragment)
                ?.add(R.id.fl_relation, fragments[currentIndex], "" + currentIndex)
        } else {
            transaction
                ?.hide(currentFragment)
                ?.show(fragments[currentIndex])
        }
        currentFragment = fragments[currentIndex]
        transaction?.commit()
    }

    /**
     * 恢复fragment
     */
    private fun restoreFragment() {
        val mBeginTransaction = fragmentManager?.beginTransaction()
        for (i in 0..fragments.size - 1) {
            if (i == currentIndex) {
                mBeginTransaction?.show(fragments[i])
            } else {
                mBeginTransaction?.hide(fragments[i])
            }
        }
        mBeginTransaction?.commit()
        currentFragment = fragments[currentIndex]
    }

}