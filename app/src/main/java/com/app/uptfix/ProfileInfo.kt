package com.app.uptfix

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile_info.*
import kotlinx.android.synthetic.main.activity_register_student.*
import kotlinx.android.synthetic.main.blue_button.view.*
import kotlinx.android.synthetic.main.profile_data.*
import java.util.*


class ProfileInfo : AppCompatActivity() {
    var authf: FirebaseAuth? = FirebaseAuth.getInstance()
    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    var currentUser: FirebaseUser? = authf!!.currentUser
    var userID: String = currentUser?.uid.toString()


    lateinit var profileName: TextView
    lateinit var logOutBttn: CustomComponents.BlueButton
    lateinit var bottomBar: CustomComponents.BottomBar
    lateinit var addProfileImage: ImageView
    lateinit var tapToAddImageText: TextView
    lateinit var fAuth: FirebaseAuth
    lateinit var storage: FirebaseStorage
    lateinit var profileImageReference: StorageReference
    lateinit var reference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_info)
        profileName = textViewProfileName
        bottomBar = blueBottomBar
        logOutBttn = logOutButton
        addProfileImage = profileImage
        tapToAddImageText = textViewAddProfilePic
        fAuth = FirebaseAuth.getInstance()
        storage = FirebaseStorage.getInstance()
        reference = storage.getReference()
        profileImageReference =
            reference.child("Profile Images/" + fAuth.currentUser.uid + "/profile.jpg");

        setProfileName()
        setProfileData(this)
        logOut(logOutBttn)
        setProfilePic()
    }


    fun setProfileName() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val name =
                    dataSnapshot.child("Users").child(userID).child("name")
                        .getValue(String::class.java)
                val surname =
                    dataSnapshot.child("Users").child(userID).child("surname")
                        .getValue(String::class.java)
                var profileFullName = name + " " + surname
                profileName.text = profileFullName
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })


    }


    fun setProfileData(context: Context) {
        databaseReference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {


                val email =
                    dataSnapshot.child("Users").child(userID).child("email")
                        .getValue(String::class.java)
                val phoneNumber =
                    dataSnapshot.child("Users").child(userID).child("phoneNumber")
                        .getValue(String::class.java)
                val accountType =
                    dataSnapshot.child("Users").child(userID).child("accountType")
                        .getValue(String::class.java)
                val dorm =
                    dataSnapshot.child("Users").child(userID).child("dorm")
                        .getValue(String::class.java)

                if (accountType.equals(getString(R.string.accTypeStudent))) {
                    val dormRoom =
                        dataSnapshot.child("Users").child(userID).child("room")
                            .getValue(String::class.java)
                    emailDataView.setProfileData(email)
                    phoneNumberDataView.setProfileData(phoneNumber)
                    dormDataView.setProfileData(dorm)
                    dormRoomDataView.setProfileData(dormRoom)
                    profileImageReference.downloadUrl.addOnSuccessListener {

                        Picasso.get().load(it).into(addProfileImage)
                        tapToAddImageText.visibility = View.GONE
                    }

                    openStudentHomePage(bottomBar)
                    openHistoryPageStudent(bottomBar)

                } else if (accountType.equals(getString(R.string.accTypeAdmin))) {
                    emailDataView.setProfileData(email)
                    phoneNumberDataView.setProfileData(phoneNumber)
                    dormDataView2.visibility=View.VISIBLE
                    dormDataView2.dataCategoryText.text=getString(R.string.administratedDorm)
                    dormDataView2.setProfileData(dorm)
                    phoneNumberDataView.visibility=View.GONE
                    dormDataView.visibility=View.GONE
                    dormRoomDataView.visibility = View.GONE

                    profileImageReference.downloadUrl.addOnSuccessListener {

                        Picasso.get().load(it).into(addProfileImage)
                        tapToAddImageText.visibility = View.GONE
                    }

                    openAdminHomePage(bottomBar)
                    openHistoryPageAdmin(bottomBar)

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }

    fun logOut(logOutButton: CustomComponents.BlueButton) {
        logOutButton.customBlueButtonLayout.setOnClickListener(View.OnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        })
    }

    fun choosePicture() {
        var intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        startActivityForResult(intent, 1)

    }

    fun setProfilePic() {
        addProfileImage.setOnClickListener {
            tapToAddImageText.visibility = View.GONE
            choosePicture()


        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK && data != null && data.data != null) {
            var imageUri = data.data
            uploadPicture(imageUri)
        }
    }

    fun uploadPicture(uri: Uri?) {
        var imagesRef = reference.child("Profile Images/" + fAuth.currentUser.uid + "/profile.jpg");
        if (uri != null) {
            imagesRef.putFile(uri).addOnSuccessListener {
                if (it.task.isSuccessful) {
                    imagesRef.downloadUrl.addOnSuccessListener() {
                        Picasso.get().load(uri).into(addProfileImage)

                    }

                } else {

                    Toast.makeText(this, "Image Upload Failed", Toast.LENGTH_SHORT).show()

                }

            }
        }


    }

    fun openStudentHomePage(bottomBar: CustomComponents.BottomBar) {
        bottomBar.homeButton.setOnClickListener {
            val intent = Intent(this, MainInterfaceStudent::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }
    }

    fun openAdminHomePage(bottomBar: CustomComponents.BottomBar) {
        bottomBar.homeButton.setOnClickListener {
            val intent = Intent(this, MainInterfaceAdmin::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }
    }

    fun openHistoryPageStudent(bottomBar: CustomComponents.BottomBar) {
        bottomBar.historyButton.setOnClickListener {
            val intent = Intent(this, ProblemHistoryStudent::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }
    }

    fun openHistoryPageAdmin(bottomBar: CustomComponents.BottomBar) {
        bottomBar.historyButton.setOnClickListener {
            val intent = Intent(this, ProblemHistoryAdmin::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }


    }
    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}







