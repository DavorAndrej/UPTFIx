package CustomComponents

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.app.uptfix.R
import kotlinx.android.synthetic.main.bottom_bar.view.*
import kotlinx.android.synthetic.main.category_card.view.*

class CategoryCard @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    lateinit var layout: RelativeLayout
    lateinit var categoryImage: ImageView
    lateinit var categoryText: TextView
    lateinit var category: CharSequence

    init {
        LayoutInflater.from(context).inflate(R.layout.category_card, this, true)
        initComponents()
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.CategoryCard, 0, 0)
            category = resources.getText(
                typedArray.getResourceId(
                    R.styleable.CategoryCard_category,
                    R.string.studentacc
                )
            )
            setCardText(category)
            typedArray.recycle()

        }

    }
    @SuppressLint("SetTextI18n")
    fun setCardText(titleText: CharSequence) {
        categoryText.text = titleText[0].toUpperCase()+titleText.substring(1).replace("_"," ")}
    fun initComponents() {
        layout = cardsLayout
        categoryImage = mainIcon
        categoryText=cardText
    }}