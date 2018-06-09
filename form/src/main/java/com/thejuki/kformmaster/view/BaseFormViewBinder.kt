package com.thejuki.kformmaster.view

import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatImageView
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewFinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.BaseFormElement
import com.thejuki.kformmaster.utils.*

/**
 * Base Form ViewBinder
 *
 * Base setup for title, error, and visibility
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
abstract class BaseFormViewBinder {
    fun buildTitle(model: BaseFormElement<*>, finder: ViewFinder, context: Context, formBuilder: FormBuildHelper): Triple<AppCompatTextView, AppCompatTextView, View> {
        val textViewTitle = finder.find(R.id.formElementTitle) as AppCompatTextView
        val textViewError = finder.find(R.id.formElementError) as AppCompatTextView

        val title_layout = finder.find(R.id.title_layout) as LinearLayout
        val value_layout = finder.find(R.id.value_layout) as RelativeLayout
        title_layout.visibility = model.showTitleLayout
        value_layout.visibility = model.showValueLayout
        val itemView = finder.getRootView() as View
        baseSetup(model, textViewTitle, textViewError, itemView)

        val asterisk = finder.find(R.id.request_asterisk) as AppCompatTextView
//        if (model.required) {
//            asterisk.visibility = View.VISIBLE
//        }
        asterisk.visibility = getParamTypeVisable(model.requiredShowAsterisk, formBuilder.commonRequiredShowAsterisk)

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
        formBuilder.apply {
            textViewTitle.apply {
                val titleSize = getParamTypeInt(model.titleTextSize, commonTitleTextSize)
                if (titleSize > -1) {
                    setTextSizeExt(titleSize)
                }
                setTextBoldExt(getParamTypeBoolean(model.titleBold, formBuilder.commonTitleBold))
                val titleColor = getParamTypeInt(model.titlesColor, commonTitlesColor)
                if (titleColor > -1) {
                    setTextColorExt(titleColor)
                }
                setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus) {
                        val titleColorFocus = getParamTypeInt(model.titleFocusColor, commonTitleFocusColor)
                        if (titleColorFocus > -1) {
                            setTextColorExt(titleColorFocus)
                        }
                    } else {
                        if (titleColor > -1) {
                            setTextColorExt(titleColor)
                        }
                    }
                }
                if (model.showColln) {
                    text = "$text :"
                }
            }
        }

        if (model.titlePrefixImage > -1) {
            val titlePrefixImg = finder.find(R.id.title_prefix_img) as AppCompatImageView
            titlePrefixImg.setImageResource(model.titlePrefixImage)
            titlePrefixImg.visibility = View.VISIBLE
        }
        val layoutWrap = finder.find(R.id.layout_wrap) as LinearLayout
        model.valueOnClickListener?.let {
            layoutWrap.setOnClickListener(it)
            value_layout.setOnClickListener(it)
        }

        return Triple(textViewTitle, textViewError, itemView)
    }

    fun buildValueWrap(model: BaseFormElement<*>, finder: ViewFinder, formBuilder: FormBuildHelper) {
        formBuilder.apply {
            model.valuePrefixText?.let {
                val valuePrefixTextView = finder.find(R.id.value_prefix) as AppCompatTextView
                valuePrefixTextView.apply {
                    text = it
                    visibility = View.VISIBLE
                    val valueSize = getParamTypeInt(model.valuePrefixTextSize, commonValuePrefixTextSize)
                    if (valueSize > -1) {
                        setTextSizeExt(valueSize)
                    }
                    val color = getParamTypeInt(model.valuePrefixTextColor, commonValuePrefixTextColor)
                    if (color > -1) {
                        setTextColorExt(color)
                    }
                    setTextBoldExt(getParamTypeBoolean(model.valuePrefixTextBold, formBuilder.commonValuePrefixTextBold))
                }
            }
            model.valueSuffixText?.let {
                val valueSuffixTextView = finder.find(R.id.value_suffix) as AppCompatTextView
                valueSuffixTextView.apply {
                    text = it
                    visibility = View.VISIBLE
                    val size = getParamTypeInt(model.valueSuffixTextSize, commonValueSuffixSize)
                    if (size > -1) {
                        setTextSizeExt(size)
                    }
                    val color = getParamTypeInt(model.valueSuffixTextColor, commonValueSuffixColor)
                    if (color > -1) {
                        setTextColorExt(color)
                    }
                    setTextBoldExt(getParamTypeBoolean(model.valueSuffixTextBold, formBuilder.commonValueSuffixBold))
                }
            }
            if (model.valueSuffixImage > -1) {
                val valueSuffixImg = finder.find(R.id.value_suffix_img) as AppCompatImageView
                valueSuffixImg.setImageResource(model.valueSuffixImage)
                valueSuffixImg.visibility = View.VISIBLE
            }
        }
    }


    fun buildLayout(model: BaseFormElement<*>, finder: ViewFinder, context: Context, formBuilder: FormBuildHelper) {

        formBuilder.apply {
            val layoutWrap = finder.find(R.id.layout_wrap) as LinearLayout
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

    /**
     * Initializes the base form fields
     */
    fun baseSetup(formElement: BaseFormElement<*>, textViewTitle: AppCompatTextView?,
                  textViewError: AppCompatTextView?,
                  itemView: View) {

        formElement.itemView = itemView

        // Setup title and error if present
        if (textViewTitle != null) {
            textViewTitle.text = formElement.title
            formElement.titleView = textViewTitle
        }

        if (textViewError != null) {
            setError(textViewError, formElement.error)
            formElement.errorView = textViewError
        }

        if (formElement.visible) {
            itemView.visibility = View.VISIBLE
            itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        } else {
            itemView.visibility = View.GONE
            itemView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0)
        }
    }

    /**
     * Shows/Hides the [error]
     */
    fun setError(textViewError: AppCompatTextView, error: String?) {
        if (error.isNullOrEmpty()) {
            textViewError.visibility = View.GONE
            return
        }

        textViewError.text = error
        textViewError.visibility = View.VISIBLE
    }

    /**
     * Shows the [dialog] when the form element is clicked
     */
    fun setOnClickListener(editTextValue: AppCompatEditText, itemView: View, dialog: Dialog) {
        editTextValue.isFocusable = false

        // display the dialog on click
        val listener = View.OnClickListener {
            dialog.show()
        }

        itemView.setOnClickListener(listener)
        editTextValue.setOnClickListener(listener)
    }

    fun getLayoutRange(specify: Int, common: Int): Int = if (specify > -1) {
        specify
    } else {
        common
    }

    fun getParamTypeInt(specify: Int, common: Int): Int = if (specify > -1) {
        specify
    } else {
        common
    }

    fun getParamTypeVisable(specify: Int, common: Int): Int {
        return if (specify == View.INVISIBLE || specify == View.GONE || specify == View.VISIBLE) {
            specify
        } else if (common == View.INVISIBLE || common == View.GONE || common == View.VISIBLE) {
            common
        } else {
            View.INVISIBLE
        }
    }

    fun getParamTypeBoolean(specify: Boolean?, common: Boolean?): Boolean {
        return when {
            specify != null -> specify
            common != null -> common
            else -> false
        }
    }
}