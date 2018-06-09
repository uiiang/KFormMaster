package com.thejuki.kformmaster.model

import com.thejuki.kformmaster.helper.BaseTag

/**
 * Form Picker MultiCheckBox Element
 *
 * Form
 * element for AppCompatEditText (which on click opens a MultiCheckBox dialog)
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormPickerTagElement<T>(tag: Int = -1) : FormPickerElement<T>(tag) {

    //    var tagAdapter: TagAdapter<BaseTag>? = null
    var maxSelectedCount: Int = 1
    var selectedIdList: MutableList<Long> = mutableListOf()
    var tagOptions: MutableList<BaseTag> = mutableListOf()
    var tagLayoutRes: Int? = null

//    fun setTagAdapter(tagAdapter: TagAdapter<BaseTag>): FormPickerTagElement<T> {
//        this.tagAdapter = tagAdapter
//        return this
//    }

    fun setMaxSelectedCount(maxSelectedCount: Int): FormPickerTagElement<T> {
        this.maxSelectedCount = maxSelectedCount
        return this
    }

    fun setSelectIdList(selectedIdList: MutableList<Long>): FormPickerTagElement<T> {
        this.selectedIdList = selectedIdList
        return this
    }

}
