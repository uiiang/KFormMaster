package com.thejuki.kformmaster.model

import android.view.View

/**
 * Form Header
 *
 * Form element for Header TextView
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormHeader(tag: Int = -1) : BaseFormElement<String>(tag) {

    var centerTitle: String = ""
    var centerTitleTextSize: Int = -1
    var centerTitlesColor: Int = -1
    var centerTitleBold: Boolean? = null
    var showCenterTitleLayout: Int = View.GONE
    var centerTitleDrawableLeft: Int = -1
    var centerTitleDrawableRight: Int = -1
    var centerTitleDrawablePadding: Int = -1

    var rightTitle: String = ""
    var rightTitleTextSize: Int = -1
    var rightTitlesColor: Int = -1
    var rightTitleBold: Boolean? = null
    var showRightTitleLayout: Int = View.GONE
    var rightTitleDrawableLeft: Int = -1
    var rightTitleDrawableRight: Int = -1
    var rightTitleDrawablePadding: Int = -1

    /**
     * Enable to collapse/un-collapse elements below the header
     * when the header is tapped
     */
    var collapsible: Boolean = false

    /**
     * Indicates if elements under header are collapsed or not
     */
    var allCollapsed: Boolean = false

    /**
     * No validation needed
     */
    override val isValid: Boolean
        get() = true

    /**
     * Collapsible builder setter
     */
    fun setCollapsible(collapsible: Boolean): FormHeader {
        this.collapsible = collapsible
        return this
    }
}