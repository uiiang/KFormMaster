package com.thejuki.kformmasterexample.item

import com.thejuki.kformmaster.helper.BaseTag

/**
 * List Item
 *
 * An example class used for dropDown and multiCheckBox
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
data class ListItem(val id: Long? = null,
                    val name: String? = null
) : BaseTag {
    override fun getTagId(): Long {
        return id!!
    }

    override fun getTagText(): String {
        return name.orEmpty()
    }
    override fun toString(): String {
        return name.orEmpty()
    }
}