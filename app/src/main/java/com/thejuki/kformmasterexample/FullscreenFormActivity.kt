package com.thejuki.kformmasterexample

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.thejuki.kformmaster.helper.*
import com.thejuki.kformmaster.model.FormEmailEditTextElement
import com.thejuki.kformmaster.model.FormHeader
import com.thejuki.kformmaster.model.FormPasswordEditTextElement
import com.thejuki.kformmaster.model.FormPickerDateTimeElement
import com.thejuki.kformmasterexample.FullscreenFormActivity.Tag.*
import com.thejuki.kformmasterexample.adapter.ContactAutoCompleteAdapter
import com.thejuki.kformmasterexample.item.ContactItem
import com.thejuki.kformmasterexample.item.ListItem
import kotlinx.android.synthetic.main.activity_fullscreen_form.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.Date

/**
 * Fullscreen Form Activity
 *
 * The Form takes up the whole activity screen
 *
 * @author **TheJuki** ([GitHub](https://github.com/TheJuki))
 * @version 1.0
 */
class FullscreenFormActivity : AppCompatActivity() {

    private lateinit var formBuilder: FormBuildHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_form)

        setupToolBar()

        setupForm()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_form, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.action_validate -> {
                    validate()
                    true
                }
                R.id.action_show_hide -> {
                    showHideAll()
                    true
                }
                R.id.action_clear -> {
                    clear()
                    true
                }
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    private fun clear() {
        formBuilder.clearAll()
    }

    private fun showHideAll() {
        formBuilder.elements.forEach({
            if (it !is FormHeader) {
                it.visible = !it.visible
            }
        })
    }

    private fun validate() {
        val alert = AlertDialog.Builder(this@FullscreenFormActivity).create()

        if (formBuilder.isValidForm) {
            alert.setTitle(this@FullscreenFormActivity.getString(R.string.FormValid))
        } else {
            alert.setTitle(this@FullscreenFormActivity.getString(R.string.FormInvalid))

            formBuilder.elements.forEach({
                if (!it.isValid) {
                    it.error = "This field is required!"
                }
            })
        }

        alert.setButton(AlertDialog.BUTTON_POSITIVE, this@FullscreenFormActivity.getString(android.R.string.ok), { _, _ -> })
        alert.show()
    }

    private fun setupToolBar() {

        val actionBar = supportActionBar

        if (actionBar != null) {
            actionBar.title = getString(R.string.full_screen_form)
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeButtonEnabled(true)
        }
    }

    private val fruits = listOf(ListItem(id = 91, name = "Banana"),
            ListItem(id = 29, name = "Orange"),
            ListItem(id = 13, name = "Mango"),
            ListItem(id = 42, name = "Guava"),
            ListItem(id = 15, name = "Apple")
    )

    private enum class Tag {
        Email,
        Phone,
        Location,
        Address,
        ZipCode,
        Date,
        Time,
        DateTime,
        Password,
        SingleItem,
        MultiItems,
        AutoCompleteElement,
        AutoCompleteTokenElement,
        ButtonElement,
        TextViewElement,
        SwitchElement,
        SliderElement,
        CheckBoxElement
    }

    lateinit var email: FormEmailEditTextElement
    lateinit var password: FormPasswordEditTextElement
    lateinit var dateTime: FormPickerDateTimeElement
    private fun setupForm() {
        val sDate = Calendar.getInstance()
        sDate.set(1990, 0, 1)
        val eDate = Calendar.getInstance()
        eDate.time = Date()
        val selectDate = Calendar.getInstance()
        selectDate.set(2017, 3, 2)
        formBuilder = form(this, recyclerView, commonPaddingBottom = 16
                , commonlayoutMarginTop = 16, commonlayoutMarginRight = 16
                , commonPaddingLeft = 16, commonTitleTextSize = 14, commonTitleBold = false
                , commonTitlesColor = android.R.color.black) {
            header {
                rightTitle = getString(R.string.PersonalInfo); collapsible = false
                rightTitleDrawableLeft = R.drawable.icon_new_right_arrow;paddingLeft = 0
                layoutMarginRight = 0;showRightTitleLayout = View.VISIBLE;paddingRight = 0
            }
            email = email(Email.ordinal) {
                title = getString(R.string.email)
                hint = getString(R.string.email_hint)
                value = "mail@mail.com"
                required = true
                requiredShowAsterisk = View.VISIBLE
                valueMaxLength = 15
                paddingLeft = 0
                titleBold = true
                layoutMarginLeft = 0
                rightToLeft = true
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            password = password(Password.ordinal) {
                title = getString(R.string.password)
                value = "Password123"
                required = true
                titleTextSize = 16
                valueOnClickListener = object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        Toast.makeText(this@FullscreenFormActivity, "aaaaaa", Toast.LENGTH_SHORT).show()
                    }

                }
                enabled = true
                showHidePasswordBtn = true
                defaultHidePassword = false
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            phone(Phone.ordinal) {
                title = getString(R.string.Phone)
                titlesColor = android.R.color.holo_red_light
                showColln = false
                value = "+8801712345678"
                rightToLeft = false
                maxLines = 3
                valuePrefixText = "086"
                valuePrefixTextColor = android.R.color.holo_blue_bright
                required = true
                showTitleLayout = View.INVISIBLE
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            header { title = getString(R.string.FamilyInfo); collapsible = true }
            tagGroup<ListItem>(MultiItems.ordinal) {
                title = getString(R.string.MultiItems)
//                dialogTitle = getString(R.string.MultiItems)
                tagOptions = fruits.toMutableList()
                tagLayoutRes = R.layout.testkotlinform_tag
                maxSelectedCount = 5
                selectedIdList = mutableListOf(29, 15)
                showTitleLayout = View.GONE
                maxLines = 3
                required = true
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            text(Location.ordinal) {
                title = getString(R.string.Location)
                titlesColor = android.R.color.holo_red_light
                value = "Dhaka"
                valueSuffixText = "元"
                rightToLeft = false
                selectAllOnFocus = true
                titleTextSize = 20
                required = true
                requiredShowAsterisk = View.VISIBLE
                enabled = true
                rightToLeft = true
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            textArea(Address.ordinal) {
                title = getString(R.string.Address)
                value = "123 Street"
                rightToLeft = false
                selectAllOnFocus = true
                maxLines = 2
                required = true
                enabled = true
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            number(ZipCode.ordinal) {
                title = getString(R.string.ZipCode)
                titlesColor = android.R.color.black
                showColln = true
                requiredShowAsterisk = View.VISIBLE
                value = "123456"
                numbersOnly = true
                allowRadixPoint = true
                afterRadixPontNum = 2
                beforeRadixPointNum = 5
//                valueSuffixImage = R.drawable.leak_canary_icon
//                maxValue = 11
                required = true
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            header { title = getString(R.string.Schedule); collapsible = true }
            date(Tag.Date.ordinal) {
                title = getString(R.string.Date)
                dateValue = Date()
                dateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.US)
                required = true
                maxLines = 1
                rightToLeft = false
                enabled = true
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            time(Time.ordinal) {
                title = getString(R.string.Time)
                dateValue = Date()
                dateFormat = SimpleDateFormat("hh:mm a", Locale.US)
                required = true
                maxLines = 1
                rightToLeft = false
                enabled = true
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            dateTime = dateTime(DateTime.ordinal) {
                title = getString(R.string.DateTime)
                dateValue = selectDate
                dateFormat = SimpleDateFormat("yyyy年MM月dd日 HH:mm", Locale.CHINA)
                required = true
                maxLines = 1
                rightToLeft = false
                enabled = true
                hasSeconds = false
                startDate = sDate
                endDate = eDate
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            header { title = getString(R.string.PreferredItems); collapsible = true }
            dropDown<ListItem>(SingleItem.ordinal) {
                title = getString(R.string.SingleItem)
                dialogTitle = getString(R.string.SingleItem)
                options = fruits
                enabled = true
                rightToLeft = false
                maxLines = 3
                value = ListItem(id = 1, name = "Banana")
                required = true
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            multiCheckBox<List<ListItem>>(MultiItems.ordinal) {
                title = getString(R.string.MultiItems)
                dialogTitle = getString(R.string.MultiItems)
                options = fruits
                value = listOf(ListItem(id = 1, name = "Banana"))
            }
            autoComplete<ContactItem>(AutoCompleteElement.ordinal) {
                title = getString(R.string.AutoComplete)
                arrayAdapter = ContactAutoCompleteAdapter(this@FullscreenFormActivity,
                        android.R.layout.simple_list_item_1, defaultItems =
                arrayListOf(ContactItem(id = 0, value = "", label = "Try \"Apple May\"")))
                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
                value = ContactItem(id = 1, value = "John Smith", label = "John Smith (Tester)")
                enabled = true
                maxLines = 3
                rightToLeft = false
                required = true
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
//            autoCompleteToken<ArrayList<ContactItem>>(AutoCompleteTokenElement.ordinal) {
//                title = getString(R.string.AutoCompleteToken)
//                arrayAdapter = EmailAutoCompleteAdapter(this@FullscreenFormActivity,
//                        android.R.layout.simple_list_item_1)
//                dropdownWidth = ViewGroup.LayoutParams.MATCH_PARENT
//                hint = "Try \"Apple May\""
//                value = arrayListOf(ContactItem(id = 1, value = "John.Smith@mail.com", label = "John Smith (Tester)"))
//                required = true
//                maxLines = 3
//                rightToLeft = false
//                enabled = true
//                valueObservers.add({ newValue, element ->
//                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
//                })
//            }
            textView(TextViewElement.ordinal) {
                title = getString(R.string.TextView)
                rightToLeft = false
                maxLines = 1
                value = "This is readonly!"
                valueColor = android.R.color.holo_orange_dark
                valueOnClickListener = object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        Toast.makeText(this@FullscreenFormActivity, "aaaaaa", Toast.LENGTH_SHORT).show()
                    }

                }
            }
            header { title = getString(R.string.MarkComplete); collapsible = true }
            switch<String>(SwitchElement.ordinal) {
                title = getString(R.string.Switch)
                value = "Yes"
                onValue = "Yes"
                offValue = "No"
                enabled = true
                required = true
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            slider(SliderElement.ordinal) {
                title = getString(R.string.Slider)
                value = 50
                min = 0
                max = 100
                steps = 20
                enabled = true
                required = true
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            checkBox<Boolean>(CheckBoxElement.ordinal) {
                title = getString(R.string.CheckBox)
                value = true
                checkedValue = true
                unCheckedValue = false
                required = true
                enabled = true
                valueObservers.add({ newValue, element ->
                    //Toast.makeText(this@FullscreenFormActivity, newValue.toString(), LENGTH_SHORT).show()
                })
            }
            button(ButtonElement.ordinal) {
                value = getString(R.string.Button)
                enabled = true
                valueObservers.add({ newValue, element ->
                    //                    email.value = "uiiang@163.com"
                    Log.d("ktform", "password = ${password.value}")
                    Toast.makeText(this@FullscreenFormActivity, "email is valid ${email.isValid}", Toast.LENGTH_SHORT).show()
                    Log.d("ktform", "dateTime.dateValue = ${dateTime.dateValue}")
                })
            }
            textView(999) {
                value = "神经病人思维广--二逼青年欢乐多"
                showTitleLayout = View.GONE
            }
        }
    }
}