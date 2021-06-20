package CustomComponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.RelativeLayout
import android.widget.Spinner
import android.widget.TextView
import com.app.uptfix.R
import kotlinx.android.synthetic.main.blue_spinner.view.*

class BlueSpinner  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    lateinit var text: CharSequence
    lateinit var layout: RelativeLayout
    lateinit var customSpinner: Spinner
    lateinit var customSpinnerText: TextView

    enum class spinnerContent {
        profileType,dorms
    }
    init {
        LayoutInflater.from(context).inflate(R.layout.blue_spinner, this, true)
        attrs?.let {
            initComponents()
            val typedArray = context.obtainStyledAttributes(it, R.styleable.BlueSpinner, 0, 0)
            text = resources.getText(
                typedArray.getResourceId(
                    R.styleable.BlueSpinner_spinnertext,
                    R.string.email
                )
            )
            val spinnerContent: spinnerContent = spinnerContent.values().get(typedArray.getInt(
                R.styleable.BlueSpinner_spinnerContent,0))
            setTextView(text)
            setSpinnerContent(spinnerContent)
            typedArray.recycle()

        }
    }
    fun setTextView(titletext: CharSequence) {
        customSpinnerText.setText(titletext)
    }
    fun setSpinnerContent(content: spinnerContent) {
        if(content.name.equals(spinnerContent.profileType.toString())){
            val adapter = ArrayAdapter.createFromResource(
                context,
                R.array.Profile,
                R.layout.spinner_text_color
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            customSpinner.setAdapter(adapter)}
        else if(content.name.equals(spinnerContent.dorms.toString())){
            val adapter = ArrayAdapter.createFromResource(
                context,
                R.array.Dorms,
                R.layout.spinner_text_color
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            customSpinner.setAdapter(adapter)
    }

}
    fun initComponents() {
        layout = customSpinnerLayout
        customSpinnerText = spinnerText
        customSpinner = customBlueSpinner
    }}