package com.thejuki.kformmaster.view

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormTokenAutoCompleteElement
import com.thejuki.kformmaster.state.FormTokenAutoCompleteViewState
import com.thejuki.kformmaster.token.ItemsCompletionView
import com.thejuki.kformmaster.utils.setHintTextColorExt
import com.thejuki.kformmaster.utils.setTextBoldExt
import com.thejuki.kformmaster.utils.setTextColorExt
import com.thejuki.kformmaster.utils.setTextSizeExt
import com.tokenautocomplete.TokenCompleteTextView

/**
 * Form TokenAutoComplete ViewBinder
 *
 * View Binder for [FormTokenAutoCompleteElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormTokenAutoCompleteViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element_token_auto_complete, FormTokenAutoCompleteElement::class.java, { model, finder, _ ->
        buildLayout(model, finder, context, formBuilder)
        val (textViewTitle, textViewError, itemView) = buildTitle(model, finder, context, formBuilder)
        buildValueWrap(model, finder, formBuilder)


        val itemsCompletionView = finder.find(R.id.formElementValue) as ItemsCompletionView

        if (model.value != null) {
            (model.value as List<*>).forEach({
                itemsCompletionView.addObject(it)
            })
        }

        itemsCompletionView.hint = model.hint ?: ""
        val hintColor = getParamTypeInt(model.hintColor, formBuilder.commonHintColor)
        if (hintColor > -1) {
            itemsCompletionView.setHintTextColorExt(hintColor)
        }

        model.editView = itemsCompletionView

        setEditTextFocusEnabled(itemsCompletionView, itemView)

        val itemsAdapter = if (model.arrayAdapter != null)
            model.arrayAdapter
        else
            ArrayAdapter(context, android.R.layout.simple_list_item_1, model.options)
        itemsCompletionView.setAdapter<ArrayAdapter<*>>(itemsAdapter)

        model.dropdownWidth?.let {
            itemsCompletionView.dropDownWidth = it
        }

        itemsCompletionView.setTokenClickStyle(TokenCompleteTextView.TokenClickStyle.Select)
        itemsCompletionView.allowDuplicates(false)

        setEditTextFocusEnabled(itemsCompletionView, itemView)

        itemsCompletionView.apply {
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

        itemsCompletionView.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                itemsCompletionView.setTextColorExt(model.titleFocusColor)
            } else {
                itemsCompletionView.setTextColorExt(model.titlesColor)

                model.setValue(itemsCompletionView.objects)
                model.error = null
                formBuilder.onValueChanged(model)
            }
        }
    }, object : ViewStateProvider<FormTokenAutoCompleteElement<*>, ViewHolder> {
        override fun createViewStateID(model: FormTokenAutoCompleteElement<*>): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormTokenAutoCompleteViewState(holder)
        }
    })

    private fun setEditTextFocusEnabled(itemsCompletionView: ItemsCompletionView, itemView: View) {
        itemView.setOnClickListener {
            itemsCompletionView.requestFocus()
            if (itemsCompletionView.text.isNotEmpty()) {
                itemsCompletionView.selectAll()
            }
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            itemsCompletionView.setSelection(itemsCompletionView.text.length)
            imm.showSoftInput(itemsCompletionView, InputMethodManager.SHOW_IMPLICIT)
        }
    }
}
