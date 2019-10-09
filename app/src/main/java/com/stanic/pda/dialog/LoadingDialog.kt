package com.stanic.pda.dialog

import android.app.Dialog
import android.content.Context
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.stanic.pda.R


class LoadingDialog(context : Context,themeResId : Int) : Dialog(context,themeResId) {

    class Builder(context: Context) {
        private lateinit var context: Context
        private lateinit var message : String
        private var isShowMessage = true
        private var isCancelable = false
        private var isCancelOutside = false

        init {
            this.context = context
        }

        /**
         * 设置提示信息
         * @param message
         * @return LoadingDialog.Builder
         */
        fun setMessage(message : String) : Builder{
            this.message = message
            return this
        }

        /**
         * 设置是否显示提示信息
         * @param isShowMessage
         * @return LoadingDialog.Builder
         */
        fun setShowMessage(isShowMessage : Boolean) : Builder{
            this.isShowMessage = isShowMessage
            return this
        }

        /**
         * 设置是否可以按返回键取消
         * @param isCancelable
         * @return LoadingDialog.Builder
         */
        fun setCancelable(isCancelable : Boolean) : Builder{
            this.isCancelable = isCancelable
            return this
        }

        /**
         * 设置是否可以按返回键取消
         * @param isCancelOutSide
         * @return LoadingDialog.Builder
         */
        fun setCancelOutside(isCancelOutSide : Boolean) : Builder{
            this.isCancelOutside = isCancelOutSide
            return this
        }

        fun create() : LoadingDialog{
            val inflater = LayoutInflater.from(context)
            val view = inflater.inflate(R.layout.dialog_loading,null)
            val loadingDialog = LoadingDialog(context,R.style.MyDialogStyle)
            val tvMsg = view.findViewById<TextView>(R.id.tv_tip)
            if (isShowMessage){
                tvMsg.text = message
            }else{
                tvMsg.visibility = View.GONE
            }
            loadingDialog.setContentView(view)
            loadingDialog.setCancelable(isCancelable)
            loadingDialog.setCanceledOnTouchOutside(isCancelOutside)
            return loadingDialog
        }
    }

}