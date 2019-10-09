package com.stanic.pda.presenter

import android.util.Log
import com.stanic.pda.callback.Callback
import com.stanic.pda.model.MvpModel
import com.stanic.pda.view.MvpView



class MvpPresenter : BasePresenter<MvpView>() {
    private var mvpModel : MvpModel? = null
    init {
        mvpModel = MvpModel()
    }

    fun getData(params: Map<Any,Any>,url : String) {
        if (!isViewAttached) {
            //如果没有绑定view就不加载数据
            return
        }
        //显示正在加载进度条
        attachedView?.showLoading()
        mvpModel?.getNetData(url,params,object : Callback {
            override fun onSuccess(data: Any) {
                Log.d("data==",data.toString())
                attachedView?.showData(data)
            }

            override fun onFailure(msg: String) {
                attachedView?.hideLoading()
            }

            override fun onError(msg: String) {
                attachedView?.hideLoading()
                attachedView?.showErr(msg)
            }

            override fun onComplete() {
                attachedView?.hideLoading()
            }

        })

    }

    fun getProductData(url:String,params: Map<Any,Any>){
        if (!isViewAttached){
            return
        }

        //显示正在加载进度条
        attachedView?.showLoading()
        mvpModel?.getNetData(url,params,object : Callback {
            override fun onSuccess(data: Any) {
                Log.d("data==",data.toString())
                attachedView?.showData(data)
            }

            override fun onFailure(msg: String) {
                attachedView?.hideLoading()
            }

            override fun onError(msg: String) {
                attachedView?.hideLoading()
            }

            override fun onComplete() {
                attachedView?.hideLoading()
            }

        })
    }


    fun dealData(a : Any) : Any{
        return mvpModel?.dealData(a)!!
    }

}
