package com.thejuki.kformmaster.helper

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ArrayAdapter
import com.thejuki.kformmaster.listener.OnFormElementValueChangedListener
import com.thejuki.kformmaster.model.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Form Builder
 *
 * Used for Kotlin DSL to create the FormBuildHelper
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
@DslMarker
annotation class FormDsl

/** Type-safe builder method to initialize the form */
fun form(context: Context,
         recyclerView: RecyclerView,
         listener: OnFormElementValueChangedListener? = null,
         cacheForm: Boolean = true
         , commonPaddingLeft: Int = 0
         , commonPaddingTop: Int = 0
         , commonPaddingRight: Int = 0
         , commonPaddingBottom: Int = 0
         , commonlayoutMarginLeft: Int = 0
         , commonlayoutMarginTop: Int = 0
         , commonlayoutMarginRight: Int = 0
         , commonlayoutMarginBottom: Int = 0
         , commonValueColor: Int = -1
         , commonValueBold: Boolean? = null
         , commonValueTextSize: Int = -1
         , commonValuePrefixTextColor: Int = -1
         , commonValuePrefixTextBold: Boolean? = null
         , commonValuePrefixTextSize: Int = -1
         , commonValueSuffixColor: Int = -1
         , commonValueSuffixBold: Boolean? = null
         , commonValueSuffixSize: Int = -1
         , commonHintColor: Int = -1
         , commonTitleTextSize: Int = -1
         , commonTitlesColor: Int = -1
         , commonTitleFocusColor: Int = -1
         , commonRequiredShowAsterisk: Int = -1
         , commonTitleBold: Boolean? = null
         , commonDividerPaddingLeft: Int = 0
         , commonDividerPaddingRight: Int = 0
         , commonDividerHeight: Int = 1
         , commonDividerColor: Int = -1
         , commonLayoutBackground: Int = -1

         , init: FormBuildHelper.() -> Unit): FormBuildHelper {
    val form = FormBuildHelper(
            context = context,
            listener = listener,
            recyclerView = recyclerView,
            cacheForm = cacheForm,
            commonPaddingLeft = commonPaddingLeft,
            commonPaddingRight = commonPaddingRight,
            commonPaddingTop = commonPaddingTop,
            commonPaddingBottom = commonPaddingBottom,
            commonlayoutMarginLeft = commonlayoutMarginLeft,
            commonlayoutMarginTop = commonlayoutMarginTop,
            commonlayoutMarginRight = commonlayoutMarginRight,
            commonlayoutMarginBottom = commonlayoutMarginBottom
            , commonValueColor = commonValueColor
            , commonValueBold = commonValueBold
            , commonValueTextSize = commonValueTextSize
            , commonValuePrefixTextColor = commonValuePrefixTextColor
            , commonValuePrefixTextBold = commonValuePrefixTextBold
            , commonValuePrefixTextSize = commonValuePrefixTextSize
            , commonValueSuffixColor = commonValueSuffixColor
            , commonValueSuffixBold = commonValueSuffixBold
            , commonValueSuffixSize = commonValueSuffixSize
            , commonHintColor = commonHintColor
            , commonTitleTextSize = commonTitleTextSize
            , commonTitlesColor = commonTitlesColor
            , commonTitleFocusColor = commonTitleFocusColor
            , commonRequiredShowAsterisk = commonRequiredShowAsterisk
            , commonTitleBold = commonTitleBold
            , commonDividerPaddingLeft = commonDividerPaddingLeft
            , commonDividerPaddingRight = commonDividerPaddingRight
            , commonDividerHeight = commonDividerHeight
            , commonDividerColor = commonDividerColor
            , commonLayoutBackground = commonLayoutBackground
    )
//    recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
    form.init()
    form.setItems()
    return form
}

/** FieldBuilder interface */
@FormDsl
interface FieldBuilder {
    fun build(): BaseFormElement<*>
}

/** Builder method to add a FormHeader */
class HeaderBuilder(var title: String = "") : FieldBuilder {
    var tag: Int = -1
    var collapsible: Boolean = false
    var titleTextSize: Int = -1
    var titlesColor: Int = -1
    var titleBold: Boolean = false
    var showTitleLayout: Int = View.VISIBLE
    var titleDrawableLeft: Int = -1
    var titleDrawableRight: Int = -1
    var titleDrawablePadding: Int = -1

    var centerTitle: String = ""
    var centerTitleTextSize: Int = -1
    var centerTitlesColor: Int = -1
    var centerTitleBold: Boolean = false
    var showCenterTitleLayout: Int = View.GONE
    var centerTitleDrawableLeft: Int = -1
    var centerTitleDrawableRight: Int = -1
    var centerTitleDrawablePadding: Int = -1

    var rightTitle: String = ""
    var rightTitleTextSize: Int = -1
    var rightTitlesColor: Int = -1
    var rightTitleBold: Boolean = false
    var showRightTitleLayout: Int = View.GONE
    var rightTitleDrawableLeft: Int = -1
    var rightTitleDrawableRight: Int = -1
    var rightTitleDrawablePadding: Int = -1

    var paddingLeft: Int = -1
    var paddingTop: Int = -1
    var paddingRight: Int = -1
    var paddingBottom: Int = -1
    var layoutMarginLeft: Int = -1
    var layoutMarginTop: Int = -1
    var layoutMarginRight: Int = -1
    var layoutMarginBottom: Int = -1

    var dividerPaddingLeft: Int = 0
    var dividerPaddingRight: Int = 0
    var dividerHeight: Int = 1
    var dividerColor: Int = -1
    var layoutBackground: Int = -1
    override fun build() =
            FormHeader(tag).apply {
                this@HeaderBuilder.let {
                    title = it.title
                    titleBold = it.titleBold
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    showTitleLayout = it.showTitleLayout
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawableRight = it.titleDrawableRight
                    titleDrawablePadding = it.titleDrawablePadding

                    centerTitle = it.centerTitle
                    centerTitleTextSize = it.centerTitleTextSize
                    centerTitlesColor = it.centerTitlesColor
                    centerTitleBold = it.centerTitleBold
                    showCenterTitleLayout = it.showCenterTitleLayout
                    centerTitleDrawableLeft = it.centerTitleDrawableLeft
                    centerTitleDrawableRight = it.centerTitleDrawableRight
                    centerTitleDrawablePadding = it.centerTitleDrawablePadding

                    rightTitle = it.rightTitle
                    rightTitleTextSize = it.rightTitleTextSize
                    rightTitlesColor = it.rightTitlesColor
                    rightTitleBold = it.rightTitleBold
                    showRightTitleLayout = it.showRightTitleLayout
                    rightTitleDrawableLeft = it.rightTitleDrawableLeft
                    rightTitleDrawableRight = it.rightTitleDrawableRight
                    rightTitleDrawablePadding = it.rightTitleDrawablePadding

                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                }
            }.setCollapsible(collapsible)
}

/** FormBuildHelper extension to add a FormHeader */
fun FormBuildHelper.header(init: HeaderBuilder.() -> Unit): FormHeader {
    return addFormElement(HeaderBuilder().apply(init).build())
}

/** Builder method to add a BaseFormElement */
@FormDsl
abstract class BaseElementBuilder<T>(protected val tag: Int = -1, var title: String? = null) : FieldBuilder {
    /**
     * Form Element Value
     */
    var value: T? = null
    var valueColor: Int = -1
    var valueBold: Boolean = false
    var valueTextSize: Int = -1
    var valuePrefixText: String? = null
    var valuePrefixTextColor: Int = -1
    var valuePrefixTextBold: Boolean = false
    var valuePrefixTextSize: Int = -1
    var valueSuffixText: String? = null
    var valueSuffixColor: Int = -1
    var valueSuffixBold: Boolean = false
    var valueSuffixSize: Int = -1
    var valueSuffixImage: Int = -1
    var valueOnClickListener: View.OnClickListener? = null
    var hintColor: Int = -1

    var titlePrefixImage: Int = -1
    var titleTextSize: Int = -1
    var titlesColor: Int = -1
    var titleFocusColor: Int = -1
    var titleDrawableLeft: Int = -1
    var titleDrawableRight: Int = -1
    var titleDrawablePadding: Int = -1
    var requiredShowAsterisk: Int = -1
    var titleBold: Boolean = false
    var showColln: Boolean = false

    var showTitleLayout: Int = View.VISIBLE
    var showValueLayout: Int = View.VISIBLE
    var valueMaxLength: Int = -1

    var paddingLeft: Int = -1
    var paddingTop: Int = -1
    var paddingRight: Int = -1
    var paddingBottom: Int = -1
    var layoutMarginLeft: Int = -1
    var layoutMarginTop: Int = -1
    var layoutMarginRight: Int = -1
    var layoutMarginBottom: Int = -1

    var showTopDivider: Boolean = true
    var showBottomDivider = false
    var selectAllOnFocus = false

    var dividerPaddingLeft: Int = 0
    var dividerPaddingRight: Int = 0
    var dividerHeight: Int = 1
    var dividerColor: Int = -1
    var layoutBackground: Int = -1
    /**
     * Form Element Hint
     */
    var hint: String? = null

    /**
     * Form Element RTL
     */
    var rightToLeft: Boolean = false

    /**
     * Form Element Max Lines
     */
    var maxLines: Int = 1

    /**
     * Form Element Error
     */
    var error: String? = null

    /**
     * Form Element Required
     */
    var required: Boolean = false

    /**
     * Form Element Visibility
     */
    var visible: Boolean = true

    /**
     * Form Element Enabled
     */
    var enabled: Boolean = true

    /**
     * Form Element Value Observers
     */
    val valueObservers = mutableListOf<(value: T?, element: BaseFormElement<T>) -> Unit>()
}

/** Builder method to add a FormSingleLineEditTextElement */
class SingleLineEditTextBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormSingleLineEditTextElement(tag).apply {
                this@SingleLineEditTextBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    valueMaxLength = it.valueMaxLength
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider
                    selectAllOnFocus = it.selectAllOnFocus

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormSingleLineEditTextElement */
fun FormBuildHelper.text(tag: Int = -1, init: SingleLineEditTextBuilder.() -> Unit): FormSingleLineEditTextElement {
    return addFormElement(SingleLineEditTextBuilder(tag).apply(init).build())
}

/** Builder method to add a FormMultiLineEditTextElement */
class MultiLineEditTextBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormMultiLineEditTextElement(tag).apply {
                this@MultiLineEditTextBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    valueMaxLength = it.valueMaxLength
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider
                    selectAllOnFocus = it.selectAllOnFocus

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormMultiLineEditTextElement */
fun FormBuildHelper.textArea(tag: Int = -1, init: MultiLineEditTextBuilder.() -> Unit): FormMultiLineEditTextElement {
    return addFormElement(MultiLineEditTextBuilder(tag).apply(init).build())
}

/** Builder method to add a FormNumberEditTextElement */
class NumberEditTextBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    var numbersOnly: Boolean = true
    var allowRadixPoint: Boolean = false
    var beforeRadixPointNum: Int = -1
    var afterRadixPontNum: Int = -1
    var maxValue: Long = -1
    override fun build() =
            FormNumberEditTextElement(tag).apply {
                this@NumberEditTextBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    numbersOnly = it.numbersOnly
                    allowRadixPoint = it.allowRadixPoint
                    beforeRadixPointNum = it.beforeRadixPointNum
                    afterRadixPontNum = it.afterRadixPontNum
                    maxValue = it.maxValue
                    valueMaxLength = it.valueMaxLength
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider
                    selectAllOnFocus = it.selectAllOnFocus

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormNumberEditTextElement */
fun FormBuildHelper.number(tag: Int = -1, init: NumberEditTextBuilder.() -> Unit): FormNumberEditTextElement {
    return addFormElement(NumberEditTextBuilder(tag).apply(init).build())
}

/** Builder method to add a FormEmailEditTextElement */
class EmailEditTextBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormEmailEditTextElement(tag).apply {
                this@EmailEditTextBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    valueMaxLength = it.valueMaxLength
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider
                    selectAllOnFocus = it.selectAllOnFocus

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormEmailEditTextElement */
fun FormBuildHelper.email(tag: Int = -1, init: EmailEditTextBuilder.() -> Unit): FormEmailEditTextElement {
    return addFormElement(EmailEditTextBuilder(tag).apply(init).build())
}

/** Builder method to add a FormPhoneEditTextElement */
class PasswordEditTextBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    var showHidePasswordBtn: Boolean = false
    var hidePasswordBtnRes: Int? = null
    var defaultHidePassword: Boolean = true
    override fun build() =
            FormPasswordEditTextElement(tag).apply {
                this@PasswordEditTextBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    valueMaxLength = it.valueMaxLength
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider
                    selectAllOnFocus = it.selectAllOnFocus
                    showHidePasswordBtn = it.showHidePasswordBtn
                    hidePasswordBtnRes = it.hidePasswordBtnRes
                    defaultHidePassword = it.defaultHidePassword

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a PasswordEditTextBuilder */
fun FormBuildHelper.password(tag: Int = -1, init: PasswordEditTextBuilder.() -> Unit): FormPasswordEditTextElement {
    return addFormElement(PasswordEditTextBuilder(tag).apply(init).build())
}

/** Builder method to add a FormEmailEditTextElement */
class PhoneEditTextBuilder(tag: Int = -1) : BaseElementBuilder<String>(tag) {
    override fun build() =
            FormPhoneEditTextElement(tag).apply {
                this@PhoneEditTextBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    valueMaxLength = it.valueMaxLength
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider
                    selectAllOnFocus = it.selectAllOnFocus

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormPhoneEditTextElement */
fun FormBuildHelper.phone(tag: Int = -1, init: PhoneEditTextBuilder.() -> Unit): FormPhoneEditTextElement {
    return addFormElement(PhoneEditTextBuilder(tag).apply(init).build())
}

/** Builder method to add a FormAutoCompleteElement */
class AutoCompleteBuilder<T>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var arrayAdapter: ArrayAdapter<*>? = null
    var dropdownWidth: Int? = null
    var options: List<T>? = null
    override fun build() =
            FormAutoCompleteElement<T>(tag).apply {
                this@AutoCompleteBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    arrayAdapter = it.arrayAdapter
                    dropdownWidth = it.dropdownWidth
                    options = it.options
                    valueMaxLength = it.valueMaxLength
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider
                    selectAllOnFocus = it.selectAllOnFocus

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormAutoCompleteElement */
fun <T> FormBuildHelper.autoComplete(tag: Int = -1, init: AutoCompleteBuilder<T>.() -> Unit): FormAutoCompleteElement<T> {
    return addFormElement(AutoCompleteBuilder<T>(tag).apply(init).build())
}

/** Builder method to add a FormTokenAutoCompleteElement */
class AutoCompleteTokenBuilder<T : List<*>>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var arrayAdapter: ArrayAdapter<*>? = null
    var dropdownWidth: Int? = null
    var options: T? = null
    override fun build() =
            FormTokenAutoCompleteElement<T>(tag).apply {
                this@AutoCompleteTokenBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    arrayAdapter = it.arrayAdapter
                    dropdownWidth = it.dropdownWidth
                    options = it.options
                    valueMaxLength = it.valueMaxLength
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}


/** FormBuildHelper extension to add a FormTokenAutoCompleteElement */
fun <T : List<*>> FormBuildHelper.autoCompleteToken(tag: Int = -1, init: AutoCompleteTokenBuilder<T>.() -> Unit): FormTokenAutoCompleteElement<T> {
    return addFormElement(AutoCompleteTokenBuilder<T>(tag).apply(init).build())
}


/** Builder method to add a FormButtonElement */
class ButtonBuilder(val tag: Int = -1) : FieldBuilder {
    var value: String? = null
    var visible: Boolean = true
    var enabled: Boolean = true
    val valueObservers = mutableListOf<(value: String?, element: BaseFormElement<String>) -> Unit>()
    override fun build() =
            FormButtonElement(tag).apply {
                this@ButtonBuilder.let {
                    value = it.value
                    enabled = it.enabled
                    visible = it.visible
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormButtonElement */
fun FormBuildHelper.button(tag: Int = -1, init: ButtonBuilder.() -> Unit): FormButtonElement {
    return addFormElement(ButtonBuilder(tag).apply(init).build())
}

/** Builder method to add a FormPickerDateElement */
class DateBuilder(tag: Int = -1) : BaseElementBuilder<FormPickerDateElement.DateHolder>(tag) {
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
    var dateValue: Date? = null
    override fun build() =
            FormPickerDateElement(tag).apply {
                this@DateBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    value = it.value ?: FormPickerDateElement.DateHolder(it.dateValue, it.dateFormat)
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    valueMaxLength = it.valueMaxLength
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormPickerDateElement */
fun FormBuildHelper.date(tag: Int = -1, init: DateBuilder.() -> Unit): FormPickerDateElement {
    return addFormElement(DateBuilder(tag).apply(init).build())
}

/** Builder method to add a FormPickerTimeElement */
class TimeBuilder(tag: Int = -1) : BaseElementBuilder<FormPickerTimeElement.TimeHolder>(tag) {
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
    var dateValue: Date? = null
    override fun build() =
            FormPickerTimeElement(tag).apply {
                this@TimeBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    value = it.value ?: FormPickerTimeElement.TimeHolder(it.dateValue, it.dateFormat)
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    valueMaxLength = it.valueMaxLength
                    dateValue = it.dateValue
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormPickerTimeElement */
fun FormBuildHelper.time(tag: Int = -1, init: TimeBuilder.() -> Unit): FormPickerTimeElement {
    return addFormElement(TimeBuilder(tag).apply(init).build())
}

/** Builder method to add a FormButtonElement */
class DateTimeBuilder(tag: Int = -1) : BaseElementBuilder<FormPickerDateTimeElement.DateTimeHolder>(tag) {
    var dateFormat: DateFormat = SimpleDateFormat.getDateInstance()
    var dateValue: Calendar? = null
    var hasYear: Boolean = true
    var hasMonth: Boolean = true
    var hasDay: Boolean = true
    var hasHours: Boolean = true
    var hasMins: Boolean = true
    var hasSeconds: Boolean = true
    var startDate: Calendar? = null
    var endDate: Calendar? = null

    override fun build() =
            FormPickerDateTimeElement(tag).apply {
                this@DateTimeBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    value = it.value ?: FormPickerDateTimeElement.DateTimeHolder(it.dateValue, it.dateFormat)
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    valueMaxLength = it.valueMaxLength
                    dateFormat = it.dateFormat
                    hasYear = it.hasYear
                    hasMonth = it.hasMonth
                    hasDay = it.hasDay
                    hasHours = it.hasHours
                    hasMins = it.hasMins
                    hasSeconds = it.hasSeconds
                    startDate = it.startDate
                    endDate = it.endDate
                    dateValue = it.dateValue
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormPickerDateTimeElement */
fun FormBuildHelper.dateTime(tag: Int = -1, init: DateTimeBuilder.() -> Unit): FormPickerDateTimeElement {
    return addFormElement(DateTimeBuilder(tag).apply(init).build())
}

/** Builder method to add a FormPickerDropDownElement */
class DropDownBuilder<T>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var dialogTitle: String? = null
    var arrayAdapter: ArrayAdapter<*>? = null
    var options: List<T>? = null

    override fun build() =
            FormPickerDropDownElement<T>(tag).apply {
                this@DropDownBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    options = it.options ?: ArrayList()
                    dialogTitle = it.dialogTitle
                    arrayAdapter = it.arrayAdapter
                    valueMaxLength = it.valueMaxLength
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormPickerDropDownElement */
fun <T> FormBuildHelper.dropDown(tag: Int = -1, init: DropDownBuilder<T>.() -> Unit): FormPickerDropDownElement<T> {
    return addFormElement(DropDownBuilder<T>(tag).apply(init).build())
}


/** Builder method to add a FormPickerMultiCheckBoxElement */
class TagBuilder<T>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var maxSelectedCount: Int = 1
    var selectedIdList: MutableList<Long> = mutableListOf()
    var tagOptions: MutableList<BaseTag> = mutableListOf()
    var tagLayoutRes: Int? = null
    override fun build() =
            FormPickerTagElement<T>(tag).apply {
                this@TagBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
//                    dialogTitle = it.dialogTitle
                    valueMaxLength = it.valueMaxLength
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
//                    tagAdapter = it.tagAdapter
                    maxSelectedCount = it.maxSelectedCount
                    selectedIdList = it.selectedIdList
                    tagOptions = it.tagOptions
                    tagLayoutRes = it.tagLayoutRes
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormPickerMultiCheckBoxElement */
fun <T> FormBuildHelper.tagGroup(tag: Int = -1, init: TagBuilder<T>.() -> Unit): FormPickerTagElement<T> {
    return addFormElement(TagBuilder<T>(tag).apply(init).build())
}

/** Builder method to add a FormPickerMultiCheckBoxElement */
class MultiCheckBoxBuilder<T : List<*>>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var dialogTitle: String? = null
    var options: T? = null
    override fun build() =
            FormPickerMultiCheckBoxElement<T>(tag).apply {
                this@MultiCheckBoxBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    hint = it.hint
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    options = it.options
                    dialogTitle = it.dialogTitle
                    valueMaxLength = it.valueMaxLength
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormPickerMultiCheckBoxElement */
fun <T : List<*>> FormBuildHelper.multiCheckBox(tag: Int = -1, init: MultiCheckBoxBuilder<T>.() -> Unit): FormPickerMultiCheckBoxElement<T> {
    return addFormElement(MultiCheckBoxBuilder<T>(tag).apply(init).build())
}

/** Builder method to add a FormSwitchElement */
class SwitchBuilder<T>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var onValue: T? = null
    var offValue: T? = null
    override fun build() =
            FormSwitchElement<T>(tag).apply {
                this@SwitchBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    onValue = it.onValue
                    offValue = it.offValue
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormSwitchElement */
fun <T> FormBuildHelper.switch(tag: Int = -1, init: SwitchBuilder<T>.() -> Unit): FormSwitchElement<T> {
    return addFormElement(SwitchBuilder<T>(tag).apply(init).build())
}

/** Builder method to add a FormCheckBoxElement */
class CheckBoxBuilder<T>(tag: Int = -1) : BaseElementBuilder<T>(tag) {
    var checkedValue: T? = null
    var unCheckedValue: T? = null
    override fun build() =
            FormCheckBoxElement<T>(tag).apply {
                this@CheckBoxBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    checkedValue = it.checkedValue
                    unCheckedValue = it.unCheckedValue
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormCheckBoxElement */
fun <T> FormBuildHelper.checkBox(tag: Int = -1, init: CheckBoxBuilder<T>.() -> Unit): FormCheckBoxElement<T> {
    return addFormElement(CheckBoxBuilder<T>(tag).apply(init).build())
}

/** Builder method to add a FormSliderElement */
class SliderBuilder(tag: Int = -1) : BaseElementBuilder<Int>(tag) {
    var max: Int = 100
    var min: Int = 0
    var steps: Int = 1
    override fun build() =
            FormSliderElement(tag).apply {
                this@SliderBuilder.let {
                    title = it.title.orEmpty()
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixBold
                    valueSuffixTextColor = it.valueSuffixColor
                    valueSuffixTextSize = it.valueSuffixSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    error = it.error
                    required = it.required
                    enabled = it.enabled
                    visible = it.visible
                    max = it.max
                    min = it.min
                    steps = it.steps
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormSliderElement */
fun FormBuildHelper.slider(tag: Int = -1, init: SliderBuilder.() -> Unit): FormSliderElement {
    return addFormElement(SliderBuilder(tag).apply(init).build())
}

class TextViewBuilder(val tag: Int = -1) : FieldBuilder {
    var title: CharSequence? = null
    var titlePrefixImage: Int = -1
    var titleTextSize: Int = -1
    var titlesColor: Int = -1
    var titleFocusColor: Int = -1
    var titleDrawableLeft: Int = -1
    var titleDrawableRight: Int = -1
    var titleDrawablePadding: Int = -1
    var requiredShowAsterisk: Int = -1
    var titleBold: Boolean? = null
    var showColln: Boolean = true
    var value: String? = null
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
    var valueSuffixImage: Int = -1
    var valueOnClickListener: View.OnClickListener? = null
    var visible: Boolean = true
    var rightToLeft: Boolean = true
    var maxLines: Int = 1

    var hintColor: Int = -1

    var paddingLeft: Int = -1
    var paddingTop: Int = -1
    var paddingRight: Int = -1
    var paddingBottom: Int = -1
    var layoutMarginLeft: Int = -1
    var layoutMarginTop: Int = -1
    var layoutMarginRight: Int = -1
    var layoutMarginBottom: Int = -1
    var showTopDivider: Boolean = true
    var showBottomDivider = false

    var showTitleLayout: Int = View.VISIBLE
    var showValueLayout: Int = View.VISIBLE
    var valueMaxLength: Int = -1

    var dividerPaddingLeft: Int = 0
    var dividerPaddingRight: Int = 0
    var dividerHeight: Int = 1
    var dividerColor: Int = -1
    var layoutBackground: Int = -1

    val valueObservers = mutableListOf<(value: String?, element: BaseFormElement<String>) -> Unit>()
    override fun build() =
            FormTextViewElement(tag).apply {
                this@TextViewBuilder.let {
                    title = it.title
                    titlePrefixImage = it.titlePrefixImage
                    titleTextSize = it.titleTextSize
                    titlesColor = it.titlesColor
                    titleFocusColor = it.titleFocusColor
                    titleBold = it.titleBold
                    titleDrawableLeft = it.titleDrawableLeft
                    titleDrawablePadding = it.titleDrawablePadding
                    titleDrawableRight = it.titleDrawableRight
                    requiredShowAsterisk = it.requiredShowAsterisk
                    showColln = it.showColln
                    value = it.value
                    valueBold = it.valueBold
                    valueColor = it.valueColor
                    valueTextSize = it.valueTextSize
                    valuePrefixText = it.valuePrefixText
                    valuePrefixTextBold = it.valuePrefixTextBold
                    valuePrefixTextColor = it.valuePrefixTextColor
                    valuePrefixTextSize = it.valuePrefixTextSize
                    valueSuffixText = it.valueSuffixText
                    valueSuffixTextBold = it.valueSuffixTextBold
                    valueSuffixTextColor = it.valueSuffixTextColor
                    valueSuffixTextSize = it.valueSuffixTextSize
                    valueSuffixImage = it.valueSuffixImage
                    valueOnClickListener = it.valueOnClickListener
                    visible = it.visible
                    rightToLeft = it.rightToLeft
                    maxLines = it.maxLines
                    showTitleLayout = it.showTitleLayout
                    showValueLayout = it.showValueLayout
                    valueMaxLength = it.valueMaxLength
                    paddingLeft = it.paddingLeft
                    paddingTop = it.paddingTop
                    paddingRight = it.paddingRight
                    paddingBottom = it.paddingBottom
                    layoutMarginLeft = it.layoutMarginLeft
                    layoutMarginTop = it.layoutMarginTop
                    layoutMarginRight = it.layoutMarginRight
                    layoutMarginBottom = it.layoutMarginBottom
                    hintColor = it.hintColor
                    showTopDivider = it.showTopDivider
                    showBottomDivider = it.showBottomDivider

                    dividerPaddingLeft = it.dividerPaddingLeft
                    dividerPaddingRight = it.dividerPaddingRight
                    dividerHeight = it.dividerHeight
                    dividerColor = it.dividerColor
                    layoutBackground = it.layoutBackground
                    valueObservers.addAll(it.valueObservers)
                }
            }
}

/** FormBuildHelper extension to add a FormTextViewElement */
fun FormBuildHelper.textView(tag: Int = -1, init: TextViewBuilder.() -> Unit): FormTextViewElement {
    return addFormElement(TextViewBuilder(tag).apply(init).build())
}


