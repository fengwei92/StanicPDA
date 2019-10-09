package com.stanic.pda.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.stanic.pda.R

class TipDialog(context: Context) : Dialog(context) {

    class Builder(context: Context){
        private lateinit var context: Context
        private lateinit var message : String
        private var isShowMessage = true
        private var isCancelable = false
        private var isCancelOutside = false
        private lateinit var selectListener : SelectListener
        init {
            this.context = context
        }

        /**
         * 设置提示信息
         * @param message
         * @return LoadingDialog.Builder
         */
        fun setMessage(message : String) : TipDialog.Builder {
            this.message = message
            return this
        }

        /**
         * 设置是否显示提示信息
         * @param isShowMessage
         * @return LoadingDialog.Builder
         */
        fun setShowMessage(isShowMessage : Boolean) : TipDialog.Builder {
            this.isShowMessage = isShowMessage
            return this
        }

        /**
         * 设置是否可以按返回键取消
         * @param isCancelable
         * @return LoadingDialog.Builder
         */
        fun setCancelable(isCancelable : Boolean) : TipDialog.Builder {
            this.isCancelable = isCancelable
            return this
        }

        /**
         * 设置是否可以按返回键取消
         * @param isCancelOutSide
         * @return LoadingDialog.Builder
         */
        fun setCancelOutside(isCancelOutSide : Boolean) : TipDialog.Builder {
            this.isCancelOutside = isCancelOutSide
            return this
        }

        fun setSelectListener(selectListener: SelectListener) : TipDialog.Builder{
            this.selectListener = selectListener
            return this
        }

        fun create() : TipDialog{
            val infalter = LayoutInflater.from(context)
            val view = infalter.inflate(R.layout.dialog_tip,null)
            val tipDialog = TipDialog(context)
            val tvDetial = view.findViewById<TextView>(R.id.tv_detail)
            val tvCancel = view.findViewById<TextView>(R.id.tv_cancel)
            val tvDefine = view.findViewById<TextView>(R.id.tv_define)
            tvCancel.setOnClickListener {
                selectListener.onSelect(0)
            }
            tvDefine.setOnClickListener {
                selectListener.onSelect(1)
            }

            tvDetial.setText(message)
            tipDialog.setContentView(view)
            tipDialog.setCanceledOnTouchOutside(isCancelOutside)
            tipDialog.setCancelable(isCancelable)
            return tipDialog
        }
    }

    interface SelectListener{
        fun onSelect(position : Int)
    }




}