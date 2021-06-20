package CustomComponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import com.app.uptfix.R
import kotlinx.android.synthetic.main.blue_button.view.*
import kotlinx.android.synthetic.main.blue_textbox.view.*

class BlueButton  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    lateinit var text: CharSequence
    lateinit var layout: RelativeLayout
    lateinit var textView: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.blue_button, this, true)
        attrs?.let {
            initComponents()
            val typedArray = context.obtainStyledAttributes(it, R.styleable.BlueButton, 0, 0)
            text = resources.getText(
                typedArray.getResourceId(
                    R.styleable.BlueButton_buttontext,
                    R.string.login
                )
            )
            setEditText(text)
            typedArray.recycle()
        }



    }
    fun setEditText(titletext: CharSequence) {
        textView.setText(titletext)
    }


    fun initComponents() {
        layout = customBlueButtonLayout
        textView = blueButtonTextView
    }}