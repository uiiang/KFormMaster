package com.thejuki.kformmaster.model

import android.support.v7.widget.*
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.github.vivchar.rendererrecyclerviewadapter.ViewModel
import kotlin.properties.Delegates

/**
 * Base Form Element
 *
 * Holds the class variables used by most form elements
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
open class BaseFormElement<T>(var tag: Int = -1) : ViewModel {
    /**
     * Form Element Title
     */
    var title: CharSequence? = null
        set(value) {
            field = value
            titleView?.let {
                it.text = value
            }
        }
    var titlePrefixImage: Int = -1
    var titleTextSize: Int = -1
    var titlesColor: Int = -1
    var titleFocusColor: Int = -1
    var titleBold: Boolean? = null
    //        set(value) {
//            field = value
//            titleView?.let {
//                val tp = it.paint
//                value?.let {
//                    tp.isFakeBoldText = value
//                }
//            }
//        }
    var showColln: Boolean = true
    var requiredShowAsterisk: Int = -1
    var titleDrawableLeft: Int = -1
    var titleDrawableRight: Int = -1
    var titleDrawablePadding: Int = -1

    var valueColor: Int = -1
    var valueBold: Boolean? = null
    var valueTextSize: Int = -1
    var valuePrefixText: String? = null
    var valuePrefixTextColor: Int = -1
    var valuePrefixTextBold: Boolean? = null
    var valuePrefixTextSize: Int = -1
    var valueSuffixText: String? = null
    var valueSuffixTextColor: Int = -1
    var valueSuffixTextBold: Boolean? = null
    var valueSuffixTextSize: Int = -1
    var showTitleLayout: Int = View.VISIBLE
    var showValueLayout: Int = View.VISIBLE
    var valueSuffixImage: Int = -1
    var valueOnClickListener: View.OnClickListener? = null

    var valueMaxLength: Int = -1

    var showTopDivider: Boolean = true
    var showBottomDivider = false

    var paddingLeft: Int = -1
    var paddingTop: Int = -1
    var paddingRight: Int = -1
    var paddingBottom: Int = -1
    var layoutMarginLeft: Int = -1
    var layoutMarginTop: Int = -1
    var layoutMarginRight: Int = -1
    var layoutMarginBottom: Int = -1
    var selectAllOnFocus = false
        set(value) {
            field = value
            editView?.let {
                if (it is AppCompatEditText) {
                    it.setSelectAllOnFocus(value)
                }
            }
        }


    var dividerPaddingLeft: Int = 0
    var dividerPaddingRight: Int = 0
    var dividerHeight: Int = 1
    var dividerColor: Int = -1
    var layoutBackground: Int = -1

    /*
     * Form Element Unique ID
     */
    var id: Int = 0

    /**
     * Form Element Value Observers
     */
    val valueObservers = mutableListOf<(value: T?, element: BaseFormElement<T>) -> Unit>()

    /**
     * Form Element Value
     */
    var value: T? by Delegates.observable<T?>(null) { _, _, newValue ->
        valueObservers.forEach { it(newValue, this) }
        editView?.let {
            if (it is AppCompatEditText && it.text.toString() != value as? String) {
                it.setText(value as? String)

            } else if (it is TextView && value is String &&
                    it.text.toString() != value as? String &&
                    it !is SwitchCompat && it !is AppCompatCheckBox) {
                it.text = value as? String
            }
        }
    }

    var hintColor: Int = -1
//        set(value) {
//            field = value
//            value?.let {
//                editView?.let {
//                    if (it is TextView) {
//                        it.setHintTextColor(value)
//                    }
//                }
//            }
//        }
    /**
     * Form Element Hint
     */
    var hint: String? = null
        set(value) {
            field = value
            editView?.let {
                if (it is TextView) {
                    it.hint = hint
                }
            }
        }

    /**
     * Form Element Max Lines
     */
    var maxLines: Int = 1
        set(value) {
            field = value
            editView?.let {
                if (it is TextView && it !is AppCompatCheckBox && it !is AppCompatButton && it !is SwitchCompat) {
                    it.setSingleLine(maxLines == 1)
                    it.maxLines = maxLines
                }
            }
        }

    /**
     * Form Element RTL
     */
    var rightToLeft: Boolean = false
        set(value) {
            field = value
            editView?.let {
                if (it is TextView && it !is AppCompatCheckBox && it !is AppCompatButton && it !is SwitchCompat) {
                    it.gravity = if (rightToLeft) Gravity.END else Gravity.START
                }
            }
        }

    /**
     * Form Element Error
     */
    var error: String? = null
        set(value) {
            field = value
            if (value != null) {
                errorView?.let {
                    it.visibility = View.VISIBLE
                    it.text = value
                }

            } else {
                errorView?.let {
                    it.visibility = View.GONE
                    it.text = null
                }
            }
        }

    /**
     * Form Element Required
     */
    var required: Boolean = false

    /**
     * Form Element Item View
     */
    var itemView: View? = null
        set(value) {
            field = value
            itemView?.let {
                //                it.isEnabled = enabled
                if (!enabled) {
                    it.focusable = View.NOT_FOCUSABLE
                }
            }
        }

    /**
     * Form Element Edit View
     */
    var editView: View? = null
        set(value) {
            field = value
            editView?.let {
                //                it.isEnabled = enabled

                if (!enabled) {
                    it.focusable = View.NOT_FOCUSABLE
                }
                if (it is TextView && it !is AppCompatCheckBox && it !is AppCompatButton && it !is SwitchCompat) {
                    it.gravity = if (rightToLeft) Gravity.END else Gravity.START
                    it.setSingleLine(maxLines == 1)
                    it.maxLines = maxLines
                }
            }
        }

    /**
     * Form Element Title View
     */
    var titleView: AppCompatTextView? = null
        set(value) {
            field = value
            titleView?.let {
                it.isEnabled = enabled
            }
        }

    /**
     * Form Element Error View
     */
    var errorView: AppCompatTextView? = null

    /**
     * Form Element Visibility
     */
    var visible: Boolean = true
        set(value) {
            field = value
            if (value) {
                itemView?.let {
                    it.visibility = View.VISIBLE
                    val params = it.layoutParams
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    it.layoutParams = params
                }

            } else {
                itemView?.let {
                    it.visibility = View.GONE
                    val params = it.layoutParams
                    params.width = ViewGroup.LayoutParams.MATCH_PARENT
                    params.height = 0
                    it.layoutParams = params
                }
            }
        }

    /**
     * Form Element Enabled
     */
    var enabled: Boolean = true
        set(value) {
            field = value
            itemView?.let {
                it.isEnabled = value
            }
            titleView?.let {
                it.isEnabled = value
            }
            editView?.let {
                //                                it.isEnabled = value
                if (!value) {
                    it.focusable = View.NOT_FOCUSABLE
                }
            }
        }

    /**
     * Form Element Value String value
     */
    val valueAsString: String
        get() = this.value?.toString() ?: ""

    /**
     * Base validation
     */
    open val isValid: Boolean
        get() = !required || (required && value != null &&
                (value !is String || !(value as? String).isNullOrEmpty()))

    /**
     * Clear edit view
     */
    open fun clear() {
        this.value = null
    }

    /**
     * Tag builder setter
     */
    fun setTag(mTag: Int): BaseFormElement<T> {
        this.tag = mTag
        return this
    }

    /**
     * Title builder setter
     */
    fun setTitle(mTitle: String): BaseFormElement<T> {
        this.title = mTitle
        return this
    }

    /**
     * Value builder setter
     */
    @Suppress("UNCHECKED_CAST")
    open fun setValue(value: Any?): BaseFormElement<T> {
        this.value = value as T?
        return this
    }

    /**
     * Hint builder setter
     */
    fun setHint(hint: String?): BaseFormElement<T> {
        this.hint = hint
        return this
    }

    /**
     * RTL builder setter
     */
    fun setRightToLeft(rightToLeft: Boolean): BaseFormElement<T> {
        this.rightToLeft = rightToLeft
        return this
    }

    /**
     * Max Lines builder setter
     */
    fun setMaxLines(maxLines: Int): BaseFormElement<T> {
        this.maxLines = maxLines
        return this
    }

    /**
     * Error builder setter
     */
    fun setError(error: String?): BaseFormElement<T> {
        this.error = error
        return this
    }

    /**
     * Required builder setter
     */
    fun setRequired(required: Boolean): BaseFormElement<T> {
        this.required = required
        return this
    }

    /**
     * Visible builder setter
     */
    fun setVisible(visible: Boolean): BaseFormElement<T> {
        this.visible = visible
        return this
    }

    /**
     * Enabled builder setter
     */
    fun setEnabled(enabled: Boolean): BaseFormElement<T> {
        this.enabled = enabled
        return this
    }

    /**
     * Adds a value observer
     */
    fun addValueObserver(observer: (T?, BaseFormElement<T>) -> Unit): BaseFormElement<T> {
        this.valueObservers.add(observer)
        return this
    }

    /**
     * Adds a list of value observers
     */
    fun addAllValueObservers(observers: List<(T?, BaseFormElement<T>) -> Unit>): BaseFormElement<T> {
        this.valueObservers.addAll(observers)
        return this
    }
}
