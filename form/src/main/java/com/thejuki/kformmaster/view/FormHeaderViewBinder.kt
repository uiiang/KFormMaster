package com.thejuki.kformmaster.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewFinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmaster.model.FormHeader
import com.thejuki.kformmaster.state.FormHeaderViewState
import com.thejuki.kformmaster.utils.*

/**
 * Form Header ViewBinder
 *
 * View Binder for [FormHeader]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormHeaderViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element_header, FormHeader::class.java, { model, finder, _ ->
        buildHeaderLayout(model, finder, context, formBuilder)
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewCenterTitle = finder.find(R.id.formElementCenterTitle) as AppCompatTextView
        val textViewRightTitle = finder.find(R.id.formElementRightTitle) as AppCompatTextView
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, null, itemView)

        if (model.titleDrawableLeft > -1 || model.titleDrawableRight > -1) {
            var drawableLeft: Drawable? = null
            var drawableRight: Drawable? = null
            if (model.titleDrawableLeft > -1) {
                drawableLeft = ContextCompat.getDrawable(context, model.titleDrawableLeft)
            }
            if (model.titleDrawableRight > -1) {
                drawableRight = ContextCompat.getDrawable(context, model.titleDrawableRight)
            }
            textViewTitle.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableRight, null)
            if (model.titleDrawablePadding > -1) {
                textViewTitle.compoundDrawablePadding = model.titleDrawablePadding.dp2px(context)
            }
        }
        textViewTitle.apply {
            val titleSize = getParamTypeInt(model.titleTextSize, formBuilder.commonTitleTextSize)
            if (titleSize > -1) {
                setTextSizeExt(titleSize)
            }
            setTextBoldExt(getParamTypeBoolean(model.valueBold, formBuilder.commonValueBold))
            val titleColor = getParamTypeInt(model.titlesColor, formBuilder.commonTitlesColor)
            if (titleColor > -1) {
                setTextColorExt(titleColor)
            }
            text = model.title
            visibility = model.showTitleLayout
        }


        if (model.centerTitleDrawableLeft > -1 || model.centerTitleDrawableRight > -1) {
            var drawableLeft: Drawable? = null
            var drawableRight: Drawable? = null
            if (model.centerTitleDrawableLeft > -1) {
                drawableLeft = ContextCompat.getDrawable(context, model.centerTitleDrawableLeft)
            }
            if (model.centerTitleDrawableRight > -1) {
                drawableRight = ContextCompat.getDrawable(context, model.centerTitleDrawableRight)
            }
            textViewTitle.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableRight, null)
            if (model.centerTitleDrawablePadding > -1) {
                textViewTitle.compoundDrawablePadding = model.centerTitleDrawablePadding.dp2px(context)
            }
        }
        textViewCenterTitle.apply {
            val titleSize = getParamTypeInt(model.centerTitleTextSize, formBuilder.commonTitleTextSize)
            if (titleSize > -1) {
                setTextSizeExt(titleSize)
            }
            setTextBoldExt(getParamTypeBoolean(model.centerTitleBold, formBuilder.commonValueBold))
            val titleColor = getParamTypeInt(model.centerTitlesColor, formBuilder.commonTitlesColor)
            if (titleColor > -1) {
                setTextColorExt(titleColor)
            }
            text = model.centerTitle
            visibility = model.showCenterTitleLayout
        }

        if (model.rightTitleDrawableLeft > -1 || model.rightTitleDrawableRight > -1) {
            var drawableLeft: Drawable? = null
            var drawableRight: Drawable? = null
            if (model.rightTitleDrawableLeft > -1) {
                drawableLeft = ContextCompat.getDrawable(context, model.rightTitleDrawableLeft)
            }
            if (model.rightTitleDrawableRight > -1) {
                drawableRight = ContextCompat.getDrawable(context, model.rightTitleDrawableRight)
            }
            textViewTitle.setCompoundDrawablesWithIntrinsicBounds(drawableLeft, null, drawableRight, null)
            if (model.rightTitleDrawablePadding > -1) {
                textViewTitle.compoundDrawablePadding = model.rightTitleDrawablePadding.dp2px(context)
            }
        }
        textViewRightTitle.apply {
            val titleSize = getParamTypeInt(model.rightTitleTextSize, formBuilder.commonTitleTextSize)
            if (titleSize > -1) {
                setTextSizeExt(titleSize)
            }
            setTextBoldExt(getParamTypeBoolean(model.rightTitleBold, formBuilder.commonValueBold))
            val titleColor = getParamTypeInt(model.rightTitlesColor, formBuilder.commonTitlesColor)
            if (titleColor > -1) {
                setTextColorExt(titleColor)
            }
            text = model.rightTitle
            visibility = model.showRightTitleLayout
        }

        itemView.setOnClickListener {
            if (model.collapsible) {
                model.allCollapsed = !model.allCollapsed

                val index = formBuilder.elements.indexOf(model) + 1
                if (index != formBuilder.elements.size) {
                    for (i in index until formBuilder.elements.size) {
                        if (formBuilder.elements[i] is FormHeader) {
                            break
                        }
                        formBuilder.elements[i].visible = !model.allCollapsed
                    }
                }
            }
        }
    }, object : ViewStateProvider<FormHeader, ViewHolder> {
        override fun createViewStateID(model: FormHeader): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormHeaderViewState(holder)
        }
    })


    fun buildHeaderLayout(model: BaseFormElement<*>, finder: ViewFinder, context: Context, formBuilder: FormBuildHelper) {

        formBuilder.apply {
            val layoutWrap = finder.find(R.id.layout_wrap) as RelativeLayout
            layoutWrap.setPadding(getLayoutRange(model.paddingLeft, commonPaddingLeft).dp2px(context)
                    , getLayoutRange(model.paddingTop, commonPaddingTop).dp2px(context)
                    , getLayoutRange(model.paddingRight, commonPaddingRight).dp2px(context)
                    , getLayoutRange(model.paddingBottom, commonPaddingBottom).dp2px(context))

            (layoutWrap.layoutParams as LinearLayout.LayoutParams).setMargins(
                    getLayoutRange(model.layoutMarginLeft, commonlayoutMarginLeft).dp2px(context)
                    , getLayoutRange(model.layoutMarginTop, commonlayoutMarginTop).dp2px(context)
                    , getLayoutRange(model.layoutMarginRight, commonlayoutMarginRight).dp2px(context)
                    , getLayoutRange(model.layoutMarginBottom, commonlayoutMarginBottom).dp2px(context))

            val bgResId = getParamTypeInt(model.layoutBackground, commonLayoutBackground)
            if (bgResId > -1) {
                layoutWrap.setBackgroundColorExt(bgResId)
            }

            val topDivider = finder.find(R.id.top_divider) as View
            topDivider.apply {
                if (model.showTopDivider) {
                    visibility = View.VISIBLE
                    setPadding(getParamTypeInt(model.dividerPaddingLeft, commonDividerPaddingLeft).dp2px(context), 0
                            , getParamTypeInt(model.dividerPaddingRight, commonDividerPaddingRight).dp2px(context), 0)
                    layoutParams.height = getParamTypeInt(model.dividerHeight, commonDividerHeight).dp2px(context)
                    if (getParamTypeInt(model.dividerColor, commonDividerColor) > -1) {
                        setBackgroundColorExt(getParamTypeInt(model.dividerColor, commonDividerColor))
                    }
                } else {
                    visibility = View.GONE
                }
            }
            val bottomDivider = finder.find(R.id.bottom_divider) as View
            bottomDivider.apply {
                if (model.showBottomDivider) {
                    visibility = View.VISIBLE
                    setPadding(getParamTypeInt(model.dividerPaddingLeft, commonDividerPaddingLeft).dp2px(context), 0
                            , getParamTypeInt(model.dividerPaddingRight, commonDividerPaddingRight).dp2px(context), 0)
                    layoutParams.height = getParamTypeInt(model.dividerHeight, commonDividerHeight).dp2px(context)
                    if (getParamTypeInt(model.dividerColor, commonDividerColor) > -1) {
                        setBackgroundColorExt(getParamTypeInt(model.dividerColor, commonDividerColor))
                    }
                } else {
                    visibility = View.GONE
                }
            }
        }
    }
}