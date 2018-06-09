package com.thejuki.kformmaster.utils

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatAutoCompleteTextView
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.util.TypedValue
import android.view.View
import com.thejuki.kformmaster.token.ItemsCompletionView

fun Int.dp2px(context: Context): Int {
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
            toFloat(), context.resources.displayMetrics).toInt()
}

fun View.setBackgroundColorExt(resId: Int) {
    resId.let { setBackgroundColor(ContextCompat.getColor(context, it)) }
}

fun AppCompatTextView.setTextColorExt(colorId: Int) {
    colorId.let { setTextColor(ContextCompat.getColor(context, it)) }
}

fun AppCompatTextView.setHintTextColorExt(colorId: Int) {
    colorId.let { setHintTextColor(ContextCompat.getColor(context, it)) }
}

fun AppCompatTextView.setTextSizeExt(textSize: Int) {
    textSize.let { setTextSize(TypedValue.COMPLEX_UNIT_SP, it.toFloat()) }
}


fun AppCompatTextView.setTextBoldExt(isBold: Boolean) {
    paint.isFakeBoldText = isBold
}


fun AppCompatEditText.setTextColorExt(colorId: Int) {
    colorId.let { setTextColor(ContextCompat.getColor(context, it)) }
}

fun AppCompatEditText.setHintTextColorExt(colorId: Int) {
    colorId.let { setHintTextColor(ContextCompat.getColor(context, it)) }
}

fun AppCompatEditText.setTextSizeExt(textSize: Int) {
    textSize.let { setTextSize(TypedValue.COMPLEX_UNIT_SP, it.toFloat()) }
}

fun AppCompatEditText.setTextBoldExt(isBold: Boolean) {
    paint.isFakeBoldText = isBold
}


fun AppCompatAutoCompleteTextView.setTextColorExt(colorId: Int) {
    colorId.let { setTextColor(ContextCompat.getColor(context, it)) }
}

fun AppCompatAutoCompleteTextView.setHintTextColorExt(colorId: Int) {
    colorId.let { setHintTextColor(ContextCompat.getColor(context, it)) }
}

fun AppCompatAutoCompleteTextView.setTextSizeExt(textSize: Int) {
    textSize.let { setTextSize(TypedValue.COMPLEX_UNIT_SP, it.toFloat()) }
}

fun AppCompatAutoCompleteTextView.setTextBoldExt(isBold: Boolean) {
    paint.isFakeBoldText = isBold
}


fun ItemsCompletionView.setTextColorExt(colorId: Int) {
    colorId.let { setTextColor(ContextCompat.getColor(context, it)) }
}

fun ItemsCompletionView.setHintTextColorExt(colorId: Int) {
    colorId.let { setHintTextColor(ContextCompat.getColor(context, it)) }
}

fun ItemsCompletionView.setTextSizeExt(textSize: Int) {
    textSize.let { setTextSize(TypedValue.COMPLEX_UNIT_SP, it.toFloat()) }
}

fun ItemsCompletionView.setTextBoldExt(isBold: Boolean) {
    paint.isFakeBoldText = isBold
}