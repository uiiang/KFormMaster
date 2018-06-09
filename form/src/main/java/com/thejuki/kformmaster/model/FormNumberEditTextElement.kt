package com.thejuki.kformmaster.model

/**
 * Form Number EditText Element
 *
 * Form element for AppCompatEditText
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FormNumberEditTextElement(tag: Int = -1) : BaseFormElement<String>(tag) {

    /**
     * By default, the number field can contain numbers and symbols (.,-).
     * Set to true to only allow numbers.
     */
    var numbersOnly: Boolean = true
    var allowRadixPoint: Boolean = false
    var beforeRadixPointNum: Int = -1
    var afterRadixPontNum: Int = -1
    var maxValue: Long = -1

    fun setNumbersOnly(numbersOnly: Boolean): FormNumberEditTextElement {
        this.numbersOnly = numbersOnly
        return this
    }

    fun setAllowRadixPoint(allowRadixPoint: Boolean): FormNumberEditTextElement {
        this.allowRadixPoint = allowRadixPoint
        return this
    }

    fun setBeforeRadixPointNum(beforeRadixPointNum: Int): FormNumberEditTextElement {
        this.beforeRadixPointNum = beforeRadixPointNum
        return this
    }

    fun setAfterRadixPontNum(afterRadixPontNum: Int): FormNumberEditTextElement {
        this.afterRadixPontNum = afterRadixPontNum
        return this
    }

    fun setMaxValue(maxValue: Long): FormNumberEditTextElement {
        this.maxValue = maxValue
        return this
    }
}
