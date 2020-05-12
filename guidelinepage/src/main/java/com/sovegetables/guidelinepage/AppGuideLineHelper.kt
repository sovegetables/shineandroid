package com.sovegetables.guidelinepage

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.AbsoluteLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButtonDrawable

class AppGuideLineHelper private constructor(){

    private var showed = false
    private var parent:ViewGroup? = null
    private var anchorView: View? = null
    private var guideContainer:ViewGroup? = null
    private var idleHandler: Runnable? = null
    private var layoutListener: ViewTreeObserver.OnGlobalLayoutListener? = null

    companion object{

        fun create(): AppGuideLineHelper {
            return AppGuideLineHelper()
        }
    }


    fun isShow(): Boolean{
        return showed
    }

    fun hide(){
        if(showed && guideContainer != null && parent != null){
            parent!!.removeView(guideContainer)
            idleHandler = null
            guideContainer = null
            layoutListener = null
            showed = false
        }
    }

    fun showGuildLine(context: Context?, anchorView: View, msg: String,  listener: View.OnClickListener,
                      imageUp: Boolean = false,
                      @ColorInt bgColor: Int = Color.parseColor("#a0000000")){
        showGuildLine((context as Activity).window.decorView as ViewGroup, anchorView, msg, listener, imageUp, bgColor)
    }

    fun showGuildLine(parent: ViewGroup, anchorView: View, msg: String,  listener: View.OnClickListener,
                      imageUp: Boolean = false,
                      @ColorInt bgColor: Int = Color.parseColor("#a0000000")){
        if(showed && idleHandler != null){
            return
        }
        this.parent = parent
        this.anchorView = anchorView

        layoutListener = object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (showed || idleHandler != null) {
                    return
                }
                idleHandler = Runnable {
                    realShowGuide(anchorView, parent, msg, listener, imageUp, bgColor)
                }
                Handler().postDelayed(idleHandler!!, 300)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                    anchorView.viewTreeObserver?.removeOnGlobalLayoutListener(this)
                }
            }
        }
        anchorView.viewTreeObserver.addOnGlobalLayoutListener(layoutListener)
    }

    private fun realShowGuide(
        anchorView: View,
        parent: ViewGroup,
        msg: String,
        listener: View.OnClickListener, imageUp: Boolean, @ColorInt bgColor: Int): Boolean {
        try {
            val location = IntArray(2)
            anchorView.getLocationOnScreen(location)
            if (showed || (location[0] == 0 && location[1] == 0 )) {
                return false
            }
            showed = true
            val tvMsg = TextView(parent.context)
            tvMsg.setTextColor(Color.WHITE)
            tvMsg.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18f)
            tvMsg.text = msg
            tvMsg.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)

            val msgH = tvMsg.measuredHeight
            val msgW = tvMsg.measuredWidth

            val measuredWidth = anchorView.measuredWidth
            val measuredHeight = anchorView.measuredHeight
            val perW = measuredWidth.coerceAtMost(measuredHeight)
            val radius = perW / 2.0f
            val de = (parent.resources.displayMetrics.density + 0.5f).toInt()
            val anchor = View(parent.context)
            val roundButtonDrawable = QMUIRoundButtonDrawable()
            roundButtonDrawable.cornerRadius = radius
            roundButtonDrawable.setStrokeData(de, ColorStateList.valueOf(Color.WHITE))
            val xCenter = (2 * location[0] + measuredWidth) / 2
            val yCenter = (2 * location[1] + measuredHeight) / 2
            val x =
                if (measuredWidth >= measuredHeight) { xCenter - radius.toInt() } else location[0]
            val y =
                if (measuredWidth >= measuredHeight) location[1] else { yCenter - radius.toInt() }
            anchor.layoutParams = AbsoluteLayout.LayoutParams(perW, perW, x, y)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                anchor.background = roundButtonDrawable
            }else{
                anchor.setBackgroundDrawable(roundButtonDrawable)
            }
            tvMsg.layoutParams = AbsoluteLayout.LayoutParams(msgW, msgH, x - msgW - 10 * de, y - msgH - de * 10)

            val image = ImageView(parent.context)
            if(imageUp){
                val imageW = de * 40
                val imageH = 441 * imageW / 255
                image.layoutParams = AbsoluteLayout.LayoutParams(imageW, imageH, xCenter - imageW / 2, y - imageH - de * 2)
            }else{
                val imageW = de * 75
                val imageH = de * 75
                image.layoutParams = AbsoluteLayout.LayoutParams(imageW, imageH, xCenter - imageW / 2, y + perW / 2 + de * 2)
            }

            if(this.guideContainer == null){
                this.guideContainer = GuileView(parent.context)
            }
            guideContainer!!.visibility = View.VISIBLE

            guideContainer!!.addView(anchor)
            guideContainer!!.addView(tvMsg)
            guideContainer!!.addView(image)
            val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            guideContainer!!.layoutParams = layoutParams
            guideContainer!!.setBackgroundColor(bgColor)
            parent.addView(guideContainer)
            anchor.setOnClickListener(listener)
            guideContainer!!.setOnClickListener{}
            if(imageUp){
                Glide.with(image).load(R.drawable.app_guide).into(image)
            }else{
                Glide.with(image).load(R.drawable.guide_down).into(image)
            }
        } catch (ignored: Exception) {
            ignored.printStackTrace()
        }
        return false
    }


    class GuileView: AbsoluteLayout{

        constructor(context: Context?) : super(context)

        constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

        constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
            context,
            attrs,
            defStyleAttr
        )

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        constructor(
            context: Context?,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int
        ) : super(context, attrs, defStyleAttr, defStyleRes)
    }


}

