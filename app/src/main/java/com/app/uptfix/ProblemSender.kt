package com.app.uptfix

import DatabaseHandlers.ReportProblemHandler
import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_problem_sender.*
import kotlinx.android.synthetic.main.blue_textbox.view.*
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.util.*

class ProblemSender : AppCompatActivity() {
    lateinit var selectedCategoryTextView: TextView
    lateinit var exitBttn: ImageView
    lateinit var problemImageView: ImageView
    lateinit var uploadPicBttn: CustomComponents.BlueButton
    lateinit var reportProblemBttn: CustomComponents.BlueButton
    lateinit var reportedProblemEditText: CustomComponents.EditText
    lateinit var uploadedPictureText: TextView
    lateinit var selectedCategory: String
    lateinit var problemID: String
    lateinit var name: String
    lateinit var surname: String
    lateinit var dorm: String
    lateinit var room: String
    lateinit var phoneNumber: String
    lateinit var date: String
    lateinit var time: String
    lateinit var problemDescription: String
     var currentPhotoPath: Uri? =null


    lateinit var fAuth: FirebaseAuth
    lateinit var storage: FirebaseStorage
     lateinit var profileImageReference: StorageReference
    lateinit var reference: StorageReference
    var authf: FirebaseAuth? = FirebaseAuth.getInstance()
    var currentUser: FirebaseUser? = authf!!.currentUser
    var currentUserID: String = currentUser?.uid.toString()
    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem_sender)
        selectedCategoryTextView = categoryTextView
        selectedCategory = intent.getStringExtra("category").toString()
        uploadPicBttn = uploadPictureButton
        uploadedPictureText = textViewUploadePicture
        exitBttn = exitButton
        problemImageView = selectedImageView
        reportedProblemEditText = problemDescriptionEditText
        reportProblemBttn = reportProblemButton
        fAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        reference = storage.getReference()
        profileImageReference =
            reference.child("Profile Images/" + fAuth.currentUser.uid + "/profile.jpg");
        setCategory(selectedCategory)
        uploadPicture()
        sendProblemReport(this)
        exitProblemSender()
    }
    @SuppressLint("SimpleDateFormat")
    @Throws(IOException::class)
    fun setCategory(selectedCategory: String) {
        selectedCategoryTextView.text=selectedCategory


    }

    fun exitProblemSender() {
        exitBttn.setOnClickListener {

            val intent = Intent(this, MainInterfaceStudent::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }


    }

    fun uploadPicture() {
        uploadPicBttn.layout.setOnClickListener {
            var intent = Intent()
            intent.setType("image/*")
            intent.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(intent, 1)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null) {
            currentPhotoPath= data?.data!!
            Picasso.get().load(currentPhotoPath).into(problemImageView)
        }


    }

    fun verifyProblemDetails(problemText: CustomComponents.EditText): Boolean {
        var text: String = problemText.customEditText.text.toString()
        if (text.isEmpty()) {
            problemText.customEditText.requestFocus()
            problemText.customEditText.setError(getString(R.string.emptyProblemDetails))
            return false;

        }
        return true
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sendProblemReport(context: Context) {
        reportProblemBttn.layout.setOnClickListener {
            if (verifyProblemDetails(reportedProblemEditText)) {
                databaseReference.addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        name =
                            dataSnapshot.child("Users").child(currentUserID).child("name")
                                .getValue(String::class.java).toString()
                        surname =
                            dataSnapshot.child("Users").child(currentUserID).child("surname")
                                .getValue(String::class.java).toString()
                        room =
                            dataSnapshot.child("Users").child(currentUserID).child("room")
                                .getValue(String::class.java).toString()
                        phoneNumber =
                            dataSnapshot.child("Users").child(currentUserID).child("phoneNumber")
                                .getValue(String::class.java).toString()
                        dorm =
                            dataSnapshot.child("Users").child(currentUserID).child("dorm")
                                .getValue(String::class.java).toString()
                        date = LocalDate.now().toString()
                        time = LocalTime.now().toString()
                        problemID=date.substring(1).replace("-","").replace(" ","")+time.substring(1).replace(":","").replace(" ","").replace(".","")+"_"+fAuth.currentUser.uid
                        // val category = intent.getStringExtra("category").toString()
                        problemDescription = reportedProblemEditText.editText.text.toString()
                        val problemData = ReportProblemHandler(
                            problemID,
                            name,
                            surname,
                            dorm,
                            room,
                            phoneNumber,
                            date,
                            time,
                            selectedCategory,
                            problemDescription,
                            context,
                            currentPhotoPath
                        )
                        problemData.reportProblem()

                    }

                    override fun onCancelled(error: DatabaseError) {
                    }
                })


            }


        }


    }
    override fun onBackPressed() {
        moveTaskToBack(true)
    }


}
