package CustomComponents

import android.content.Context
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.app.uptfix.R
import kotlinx.android.synthetic.main.blue_textbox.view.*

class EditText @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    lateinit var text: CharSequence
    lateinit var layout: RelativeLayout
    lateinit var fieldText: TextView
    lateinit var editText: EditText
    var multiLineSize=false

    enum class fieldInputType {
        normalText, emailMode,numericMode,phoneNumberMode,passwordMode,multiLineMode
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.blue_textbox, this, true)
        attrs?.let {
            initComponents()
            val typedArray = context.obtainStyledAttributes(it, R.styleable.EditText, 0, 0)
            text = resources.getText(
                typedArray.getResourceId(
                    R.styleable.EditText_text,
                    R.string.email
                )
            )
            multiLineSize=typedArray.getBoolean(R.styleable.EditText_multiLineSize,false)
            val inputType:fieldInputType=fieldInputType.values().get(typedArray.getInt(R.styleable.EditText_inputTypes,0))
            setTextView(text)
            setInputType(inputType)
            multiLineMode(multiLineSize)
            typedArray.recycle()

        }


    }

    fun setTextView(titletext: CharSequence) {
        fieldText.setText(titletext)
    }
    fun setInputType(inputType:fieldInputType){
       if(inputType.name.equals(fieldInputType.emailMode.toString())){
           editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS }
        else if(inputType.name.equals(fieldInputType.numericMode.toString())){
           editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_CLASS_NUMBER}
       else if(inputType.name.equals(fieldInputType.phoneNumberMode.toString())){
           editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_CLASS_PHONE}
        else if(inputType.name.equals(fieldInputType.passwordMode.toString())){
           editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD }
       else if(inputType.name.equals(fieldInputType.multiLineMode.toString())){
           editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE }
    }

    fun multiLineMode(value:Boolean){
        if(value){
            layout.layoutParams.height=350
            editText.setPadding(0,20,0,0)


        }



    }



    fun initComponents() {
        layout = customEditTextLayout
        fieldText = fieldTextView
        editText = customEditText
    }
}