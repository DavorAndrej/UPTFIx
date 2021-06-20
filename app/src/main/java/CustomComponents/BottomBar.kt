package CustomComponents

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.app.uptfix.R
import kotlinx.android.synthetic.main.blue_button.view.*
import kotlinx.android.synthetic.main.bottom_bar.view.*

class BottomBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    lateinit var layout: RelativeLayout
    lateinit var profileButton: ImageView
    lateinit var homeButton: ImageView
    lateinit var historyButton: ImageView
    var profileButtonFocused = true
    var homeButtonFocused = false
    var historyButtonFocused = false

    init {
        LayoutInflater.from(context).inflate(R.layout.bottom_bar, this, true)
        attrs?.let {
            initComponents()
            val typedArray = context.obtainStyledAttributes(it, R.styleable.BottomBar, 0, 0)
            profileButtonFocused = typedArray.getBoolean(R.styleable.BottomBar_focusProfileButton, false)
            homeButtonFocused = typedArray.getBoolean(R.styleable.BottomBar_focusHomeButton, false)
            historyButtonFocused = typedArray.getBoolean(R.styleable.BottomBar_focusHistoryButton, false)
            focusHomeButton(homeButtonFocused)
            focusProfileButton(profileButtonFocused)
            focusHistoryButton(historyButtonFocused)
            typedArray.recycle()

        }

    }
    fun focusHomeButton(focused: Boolean) {
        if (focused) {
            homeButton.imageAlpha=255
            profileButton.imageAlpha=100
            historyButton.imageAlpha=100

        }

    }
    fun focusProfileButton(focused: Boolean) {
        if (focused) {
            homeButton.imageAlpha=100
            profileButton.imageAlpha=255
            historyButton.imageAlpha=100

        }

    }
    fun focusHistoryButton(focused: Boolean) {
        if (focused) {
            homeButton.imageAlpha=100
            profileButton.imageAlpha=100
            historyButton.imageAlpha=255

        }

    }

    fun initComponents() {
        layout = barLayout
        profileButton = profileImageButton
        homeButton=homeImageButton
        historyButton=historyImageButton
    }}