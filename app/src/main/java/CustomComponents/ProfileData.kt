package CustomComponents

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.app.uptfix.R
import kotlinx.android.synthetic.main.category_card.view.*
import kotlinx.android.synthetic.main.profile_data.view.*

class ProfileData @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    lateinit var layout: RelativeLayout
    lateinit var dataImage: ImageView
    lateinit var dataCategoryText: TextView
    lateinit var dataText: TextView
    lateinit var category: CharSequence
    enum class dataIcons {
        student,email,phone,dorm,door,datetime,details
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.profile_data, this, true)
        initComponents()
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.ProfileData, 0, 0)
            category = resources.getText(
                typedArray.getResourceId(
                    R.styleable.ProfileData_profileCategory,
                    R.string.studentacc
                )
            )
            val icon:dataIcons=dataIcons.values().get(typedArray.getInt(R.styleable.ProfileData_dataCategory,0))
            setCategoryDataText(category)
            setProfileData(category.toString())
            setCategoryIcon(icon)
            typedArray.recycle()

        }

    }

    @SuppressLint("SetTextI18n")
    fun setCategoryDataText(titleText: CharSequence) {
        dataCategoryText.text =
            titleText[0].toUpperCase() + titleText.substring(1).replace("_", " ")
    }
    fun setCategoryIcon(icon: dataIcons) {
        val id = context.resources.getIdentifier(icon.name, "drawable", context.packageName)
        val drawable = context.resources.getDrawable(id)
        dataImage.setImageDrawable(drawable)
    }

    fun setProfileData(titleText: String?) {
        dataText.text = titleText
    }

    fun initComponents() {
        layout = customProfileDataLayout
        dataImage = profileInfoImage
        dataCategoryText = profileInfoCategory
        dataText = dataTextView
    }
}