package com.thejuki.kformmaster.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewHolder
import com.github.vivchar.rendererrecyclerviewadapter.ViewState
import com.github.vivchar.rendererrecyclerviewadapter.ViewStateProvider
import com.github.vivchar.rendererrecyclerviewadapter.binder.ViewBinder
import com.thejuki.kformmaster.R
import com.thejuki.kformmaster.helper.BaseTag
import com.thejuki.kformmaster.helper.FormBuildHelper
import com.thejuki.kformmaster.model.FormPickerTagElement
import com.thejuki.kformmaster.state.FormEditTextViewState
import com.zhy.view.flowlayout.FlowLayout
import com.zhy.view.flowlayout.TagAdapter
import com.zhy.view.flowlayout.TagFlowLayout

/**
 * Form Picker MultiCheckBox ViewBinder
 *
 * View Binder for [FormPickerMultiCheckBoxElement]
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerTagViewBinder(private val context: Context, private val formBuilder: FormBuildHelper) : BaseFormViewBinder() {
    var viewBinder = ViewBinder(R.layout.form_element_tag, FormPickerTagElement::class.java, { model, finder, _ ->
        buildLayout(model, finder, context, formBuilder)
        val (textViewTitle, textViewError, itemView) = buildTitle(model, finder, context, formBuilder)
        buildValueWrap(model, finder, formBuilder)
        val tagLayout = finder.find(R.id.tag_flow_layout) as TagFlowLayout
        model.valueOnClickListener?.let {
            tagLayout.setOnClickListener(it)
        }

        tagLayout.apply {
            setMaxSelectCount(model.maxSelectedCount)
            if (model.tagOptions.size > 0) {
                adapter = object : TagAdapter<BaseTag>(model.tagOptions) {
                    override fun getView(parent: FlowLayout?, position: Int, t: BaseTag): View {
                        val tv = LayoutInflater.from(this@FormPickerTagViewBinder.context)
                                .inflate(if (model.tagLayoutRes != null) {
                                    model.tagLayoutRes!!
                                } else {
                                    R.layout.include_kotlinform_tag
                                }, tagLayout, false) as TextView
                        tv.text = t.getTagText()
                        return tv
                    }
                }
            } else {
//                LO.d("tagOptions 参数里包含要显示的数据，必须得传值，不然我显示什么呀，你说是吧")
                Log.d("ktform", "tagOptions 参数里包含要显示的数据，必须得传值，不然我显示什么呀，你说是吧")
            }
//            }
            if (model.selectedIdList.isNotEmpty()) {
                val selectIndex = mutableSetOf<Int>()
                model.tagOptions//.filter { model.selectedIdList.contains(it.getTagId()) }
                        .forEachIndexed { index, baseTag ->
                            if (model.selectedIdList.contains(baseTag.getTagId())) {
                                selectIndex.add(index)
                            }
                        }
                if (selectIndex.isNotEmpty()) {
                    adapter.setSelectedList(selectIndex)
                }
            }
            tagLayout.setOnSelectListener { selectPosSet ->
                model.selectedIdList.clear()
                selectPosSet.forEach {
                    if (model.tagOptions.size > it) {
                        model.selectedIdList.add(model.tagOptions[it].getTagId())
                    }
                }
            }
        }
    }, object : ViewStateProvider<FormPickerTagElement<*>, ViewHolder> {
        override fun createViewStateID(model: FormPickerTagElement<*>): Int {
            return model.id
        }

        override fun createViewState(holder: ViewHolder): ViewState<ViewHolder> {
            return FormEditTextViewState(holder)
        }
    })
}
