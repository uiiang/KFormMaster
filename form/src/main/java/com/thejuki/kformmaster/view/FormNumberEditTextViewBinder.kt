package com.thejuki.kformmaster.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatEditText
import android.support.v7.widget.AppCompatTextView
import android.text.*
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormNumberEditTextElement
import com.thejuki.kformmaster.state.FormEditTextViewState
import com.thejuki.kformmaster.utils.setHintTextColorExt
import com.thejuki.kformmaster.utils.setTextBoldExt
import com.thejuki.kformmaster.utils.setTextColorExt
import com.thejuki.kformmaster.utils.setTextSizeExt
import java.util.regex.Pattern

/**
 * Form Number EditText ViewBinder
 *
 * View Binder for [FormNumberEditTextElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormNumberEditTextViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element, FormNumberEditTextElement::class.java, { model, finder, _ ->
        buildLayout(model, finder, context, formBuilder)
        val (textViewTitle, textViewError, itemView) = buildTitle(model, finder, context, formBuilder)
        buildValueWrap(model, finder, formBuilder)

        val editTextValue = finder.find(R.id.formElementValue) as AppCompatEditText
        model.valueOnClickListener?.let {
            editTextValue.setOnClickListener(it)
        }

        editTextValue.setText(model.valueAsString)
        editTextValue.hint = model.hint ?: ""
        val hintColor = getParamTypeInt(model.hintColor, formBuilder.commonHintColor)
        if (hintColor > -1) {
            editTextValue.setHintTextColorExt(hintColor)
        }
        editTextValue.setSelectAllOnFocus(model.selectAllOnFocus)
        editTextValue.apply {
            val size = getParamTypeInt(model.valueTextSize, formBuilder.commonValueTextSize)
            if (size > -1) {
                setTextSizeExt(size)
            }
            setTextBoldExt(getParamTypeBoolean(model.valueBold, formBuilder.commonValueBold))
            val color = getParamTypeInt(model.valueColor, formBuilder.commonValueColor)
            if (color > -1) {
                setTextColorExt(color)
            }
        }
        model.editView = editTextValue

        setEditTextFocusEnabled(editTextValue, itemView)

        if (model.numbersOnly) {
            if (!model.allowRadixPoint) {
                editTextValue.inputType = InputType.TYPE_CLASS_NUMBER
                if (model.beforeRadixPointNum > -1) {
                    val filter = NumberRadixInputFilter(model.beforeRadixPointNum, 0, model.maxValue)
                    editTextValue.filters = arrayOf(filter)
                } else {
                    val filter = NumberRadixInputFilter(model.maxValue.toString().length, 0, model.maxValue)
                    editTextValue.filters = arrayOf(filter)
                }
            } else {
                editTextValue.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                if (model.afterRadixPontNum > -1 || model.beforeRadixPointNum > -1) {
                    val filter = NumberRadixInputFilter(
                            if (model.beforeRadixPointNum > -1) {
                                model.beforeRadixPointNum
                            } else {
                                10
                            }, if (model.afterRadixPontNum > -1) {
                        model.afterRadixPontNum
                    } else {
                        10
                    }, if (model.maxValue > -1) {
                        model.maxValue
                    } else {
                        1000000000
                    })
                    editTextValue.filters = arrayOf(filter)
                } else {
                    if (model.maxValue > 0) {
                        val filter = NumberRadixInputFilter(model.maxValue.toString().length, 0, model.maxValue)
                        editTextValue.filters = arrayOf(filter)
                    }
                }
            }
        } else {
            editTextValue.setRawInputType(InputType.TYPE_CLASS_NUMBER or InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS)
        }

        editTextValue.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i2: Int, i3: Int) {

                // get current form element, existing value and new value
                val currentValue = model.valueAsString
                val newValue = charSequence.toString()

                // trigger event only if the value is changed
                if (currentValue != newValue) {
                    model.setValue(newValue)
                    model.error = null
                    formBuilder.onValueChanged(model)
                }
            }

            override fun afterTextChanged(editable: Editable) {}
        })
    }, object : ViewStateProvider<FormNumberEditTextElement, ViewHolder> {
        override fun createViewStateID(model: FormNumberEditTextElement): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormEditTextViewState(holder)
        }
    })

    private fun setEditTextFocusEnabled(editTextValue: AppCompatEditText, itemView: View) {
        itemView.setOnClickListener {
            editTextValue.requestFocus()
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            editTextValue.setSelection(editTextValue.text.length)
            imm.showSoftInput(editTextValue, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}

class NumberRadixInputFilter : InputFilter {

    private var mPattern: Pattern? = null
    //输入的最大金额
    private var MAX_VALUE = Long.MAX_VALUE

    //小数点后的2位数
    private var POINTER_AFTER_LENGTH = 2

    //小数点前的7位数
    private var POINTER_BEFORE_LENGTH = 7

    private val POINTER = "."

    private val ZERO = "0"

    constructor() {
        mPattern = Pattern.compile("([0-9]|\\.)*")
    }

    constructor(before: Int, after: Int, _maxValue: Long) {
        mPattern = Pattern.compile("([0-9]|\\.)*")
//        POINTER_BEFORE_LENGTH = _maxLength - POINTER_AFTER_LENGTH - 1
        POINTER_BEFORE_LENGTH = before
        POINTER_AFTER_LENGTH = after
        MAX_VALUE = _maxValue + 1
    }


    /**
     * @param source    新输入的字符串
     * @param start     新输入的字符串起始下标，一般为0
     * @param end       新输入的字符串终点下标，一般为source长度-1
     * @param dest      输入之前文本框内容
     * @param dstart    原内容起始坐标，一般为0
     * @param dend      原内容终点坐标，一般为dest长度-1
     * @return          输入内容
     */

    override fun filter(source: CharSequence, start: Int, end: Int,
                        dest: Spanned, dstart: Int, dend: Int): CharSequence {

        val sourceText = source.toString()
        val destText = dest.toString()

        //验证删除等按键
        if (TextUtils.isEmpty(sourceText)) {
            return ""
        }

        val matcher = mPattern!!.matcher(source)
        //已经输入小数点的情况下，只能输入数字
        if (destText.contains(POINTER)) {
            if (!matcher.matches()) {
                return ""
            } else {
                if (POINTER == source) {  //只能输入一个小数点
                    return ""
                }
            }


            val index = destText.indexOf(POINTER)
            //验证小数点精度，保证小数点后只能输入两位
            val afterLength = dend - index

            // 输入后，修改控制不了，//验证小数点精度，保证小数点前位数

            if (dstart == POINTER_BEFORE_LENGTH && index == POINTER_BEFORE_LENGTH) {
                return dest.subSequence(dstart, dend)

            }


            //验证小数点精度，保证小数点后只能输入两位
            if (afterLength > POINTER_AFTER_LENGTH) {
                return dest.subSequence(dstart, dend)
            }


        } else {
            //没有输入小数点的情况下，只能输入小数点和数字，但首位不能输入小数点和0
            if (!matcher.matches()) {
                return ""
            } else {
                //验证小数点精度，保证小数点前只能输入7位

                if (dstart == POINTER_BEFORE_LENGTH) {
                    return if (sourceText.contains(POINTER))
                        dest.subSequence(dstart, dend).toString() + sourceText// + ZERO + ZERO
                    else
                        dest.subSequence(dstart, dend).toString()// + POINTER + ZERO + ZERO
                }
                if ((POINTER == source || ZERO == source) && TextUtils.isEmpty(destText)) {
                    return ZERO + POINTER + ZERO + ZERO
                }
            }
        }

        //验证输入金额的大小
        val sumText = java.lang.Double.parseDouble(destText + sourceText)
        Log.d("fulq", "$sumText >= $MAX_VALUE is ${sumText >= MAX_VALUE}")
        val ret: CharSequence
        if (sumText >= MAX_VALUE) {
            ret = dest.subSequence(dstart, dend)
        } else {
            ret = dest.subSequence(dstart, dend).toString() + sourceText
        }
        Log.d("fulq", "ret = $ret")
        return ret
    }
}