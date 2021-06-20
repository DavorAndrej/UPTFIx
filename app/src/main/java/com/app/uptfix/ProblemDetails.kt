package com.app.uptfix

import android.content.ClipDescription
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_problem_details.*

class ProblemDetails : AppCompatActivity() {
    lateinit var exitBttn: ImageView
    lateinit var noImageAttached: TextView
    lateinit var reportedProblemImage: ImageView
    lateinit  var fullName:String
    lateinit   var phoneNumber:String
    lateinit  var dorm:String
    lateinit  var dormRoom:String
    lateinit var date:String
    lateinit var time:String
    lateinit var category:String
    lateinit  var description:String
    lateinit  var uploadedPicture:String
    lateinit  var photoUri:String
    lateinit  var currentActivity:String
    lateinit var storage: FirebaseStorage
    lateinit var reference: StorageReference

    enum class activities {
        MainInterfaceAdmin,ProblemHistoryAdmin,ProblemHistoryStudent
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem_details)
        exitBttn=exitButton
        reportedProblemImage=problemImageView
        noImageAttached=noImageTextView
        storage = FirebaseStorage.getInstance()
        reference = storage.getReference()
        fullName = intent.getStringExtra("fullName").toString()
        phoneNumber = intent.getStringExtra("phoneNumber").toString()
        dorm = intent.getStringExtra("dorm").toString()
        dormRoom = intent.getStringExtra("room").toString()
        date = intent.getStringExtra("date").toString()
        time = intent.getStringExtra("time").toString()
        category = intent.getStringExtra("category").toString()
        description = intent.getStringExtra("details").toString()
        uploadedPicture=intent.getStringExtra("uploadedPicture").toString()
        photoUri=intent.getStringExtra("pictureUri").toString()
        currentActivity = intent.getStringExtra("currentActivity").toString()
        var dateTime=date+" " +time
        setName(fullName)
        setNumber(phoneNumber)
        setDateTime(dateTime)
        setProblemDorm(dorm)
        setProblemRoom(dormRoom)
        setProblemDescription(description)
        setReportedProblemPicture(uploadedPicture)

        exiiActivity(currentActivity)
    }

    fun exiiActivity(activityToStart:String){
        exitBttn.setOnClickListener{
        if(activityToStart.equals(activities.MainInterfaceAdmin.toString())){
            val intent = Intent(this, MainInterfaceAdmin::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)}
        else  if(activityToStart.equals(activities.ProblemHistoryAdmin.toString())){
            val intent = Intent(this, ProblemHistoryAdmin::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)}
        else  if(activityToStart.equals(activities.ProblemHistoryStudent.toString())){
            val intent = Intent(this, ProblemHistoryStudent::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)}}


    }
    fun setName(fullName:String){
        studentDataView.setProfileData(fullName)


    }
    fun setNumber(phoneNumber:String){
        phoneNumberDataView.setProfileData(phoneNumber)


    }
    fun setDateTime(dateTime:String){
        dateTimeDataView.setProfileData(dateTime.substringBefore("."))


    } fun setProblemDorm(title:String){
        dormDataView.setProfileData(title)


    } fun setProblemRoom(description:String){
        dormRoomDataView.setProfileData(description)


    }
    fun setProblemDescription(description:String){
        detailsDataView.setProfileData(description)


    }

    fun setReportedProblemPicture(pictureCode:String){
        var imagesRef = reference.child("Reprted file Images/$pictureCode");
        imagesRef.downloadUrl.addOnSuccessListener() {
            Picasso.get().load(it).into(reportedProblemImage)
            noImageAttached.visibility=View.GONE

        }




    }
    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
