package CustomComponents

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.net.toUri
import com.app.uptfix.R
import kotlinx.android.synthetic.main.activity_register_student.view.*
import kotlinx.android.synthetic.main.reported_problem_card.view.*

@SuppressLint("ViewConstructor")
class ReportedProblemCard @JvmOverloads constructor(
    fullName:String,
    phoneNumber:String,
    dorm:String,
    dormRoom:String,
    date: String,
    time: String,
    category: String,
    description:String,
    problemUploadedPicture:String,
    problemUploadedPictureUri:String,
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {
    lateinit var layout: RelativeLayout
    lateinit var reportedProblemIcon: ImageView
    lateinit var reportedProblemRoom: TextView
    lateinit var reportedProblemDetails: TextView
    var fullName:String
    var phoneNumber:String
    var dorm:String
    var dormRoom:String
    var date:String
    var time:String
    var category:String
    var description:String
    var problemUploadedPicture:String
    var problemUploadedPictureUri:String
    init {
        LayoutInflater.from(context).inflate(R.layout.reported_problem_card, this, true)
        initComponents()
        this.fullName=fullName
        this.phoneNumber=phoneNumber
        this.dorm=dorm
        this.dormRoom=dormRoom
        this.date=date
        this.time=time
        this.category=category
        this.description=description
        this.problemUploadedPicture=problemUploadedPicture+".jpg"
        this.problemUploadedPictureUri=problemUploadedPictureUri



            setProblemCardRoomText(dormRoom)
            setProblemDetailsText(description)


    }
   @SuppressLint("SetTextI18n")
    fun setProblemCardRoomText(titleText: String) {
       reportedProblemRoom.text = context.getString(R.string.reportedProblemRoom)+" "+titleText}
    fun setProblemDetailsText(titleText: String) {
        reportedProblemDetails.text = titleText}
    fun initComponents() {
        layout = reportedProblemCardLayout
        reportedProblemIcon = reportedProblemImage
        reportedProblemRoom=reportedProblemRoomText
        reportedProblemDetails=problemText
    }}

