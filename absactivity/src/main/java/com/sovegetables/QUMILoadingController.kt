package com.sovegetables

import android.content.Context
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog

class QUMILoadingController(private var context: Context) : ILoadingDialogController{

    private var loadingDialog: QMUITipDialog? = null

    override fun showLoadingDialog(
        msg: String?,
        icon: Int,
        canceled: Boolean,
        model: ILoadingDialogController.Model?
    ) {
        if (loadingDialog == null) {
            loadingDialog = QMUITipDialog.Builder(context)
                .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .create()
        }
        loadingDialog!!.setCancelable(canceled)
        if (!loadingDialog!!.isShowing) {
            loadingDialog!!.show()
        }
    }

    override fun hideLoadingDialog() {
        if (loadingDialog != null) {
            loadingDialog!!.dismiss()
        }
        loadingDialog = null
    }

    override fun isLoadingDialogShow(): Boolean {
        return loadingDialog?.isShowing?:false
    }

}