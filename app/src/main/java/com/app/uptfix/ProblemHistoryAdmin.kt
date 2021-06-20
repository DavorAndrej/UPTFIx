package com.app.uptfix

import CustomComponents.ReportedProblemCard
import CustomComponents.ReportedProblemCardAdapter
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main_interface_admin.*
import kotlinx.android.synthetic.main.activity_problem_history_admin.*
import kotlinx.android.synthetic.main.activity_problem_history_admin.blueBottomBar
import kotlinx.android.synthetic.main.reported_problem_card.view.*
import java.time.LocalDate

class ProblemHistoryAdmin : AppCompatActivity() {
    lateinit var bottomBar: CustomComponents.BottomBar
    lateinit var reportedProblemsListView: ListView
    lateinit var reference: DatabaseReference
    var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference
    var authf: FirebaseAuth? = FirebaseAuth.getInstance()
    var currentUser: FirebaseUser? = authf!!.currentUser
    var userID: String = currentUser?.uid.toString()
    var reportedProblemList: MutableList<ReportedProblemCard> = ArrayList()
    interface GetReportedProblemsCallback {
        fun onCallback(currentUserDorm:String?)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_problem_history_admin)
        bottomBar = blueBottomBar
        reportedProblemsListView=reportedProblemHistoryView
        reference =
            FirebaseDatabase.getInstance().getReference("Reported Problems")
        getReportedProblemsList(this)
        viewProblemDetails()
        openProfilePage(bottomBar)
        openHomePage(bottomBar)
    }

    fun readData(GetReportedProblemsCallback: GetReportedProblemsCallback) {
        databaseReference.
        addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val currentUserDorm =
                    dataSnapshot.child("Users").child(userID).child("dorm")
                        .getValue(String::class.java)
                GetReportedProblemsCallback.onCallback(currentUserDorm)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }
    fun getReportedProblemsList(context: Context) {
        readData(object : GetReportedProblemsCallback {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onCallback(currentUserDorm: String?) {
                fetchData(context,currentUserDorm)
            }
        })

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchData(context: Context, dorm:String?) {

        reference.orderByChild("dorm").equalTo(dorm).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (i in snapshot.children) {

                    var name =
                        i.child("name").getValue().toString()
                    var phoneNumber =
                        i.child("phoneNumber").getValue().toString()
                    var surname =
                        i.child("surname").getValue().toString()
                    var fullName=name + " " + surname
                    var dorm =
                        i.child("dorm").getValue().toString()
                    var dormRoom =
                        i.child("room").getValue().toString()
                    var problemDescription =
                        i.child("problemDescription").getValue().toString()
                    var date =
                        i.child("date").getValue().toString()
                    var time =
                        i.child("time").getValue().toString()
                    var uploadedPicture =
                        i.child("problemID").getValue().toString()
                    var uploadedPictureUri =
                        i.child("photoUri").getValue().toString()
                    var category =
                        i.child("category").getValue().toString().substring(0).toLowerCase()
                            .replace(" ", "_")

                    var card = ReportedProblemCard(fullName,phoneNumber,dorm,dormRoom,date,time,category,problemDescription,uploadedPicture,uploadedPictureUri,context)
                    val id = context.resources.getIdentifier(
                        category,
                        "drawable",
                        context.packageName
                    )
                    val drawable = context.resources.getDrawable(id)
                    card.layout.reportedProblemImage.setImageDrawable(drawable)
                    reportedProblemList.add(card)


                    }

                createCardLayout(reportedProblemList)
            }
        }


        )


    }
    fun createCardLayout(reportedProblemCardList:MutableList<ReportedProblemCard>) {
        reportedProblemCardList.sortWith(compareBy<ReportedProblemCard> { it.date }.thenBy { it.time })
        reportedProblemCardList.reverse()
        var cardAdapter = ReportedProblemCardAdapter(this, reportedProblemCardList)
        reportedProblemHistoryView.adapter = cardAdapter

    }
    fun viewProblemDetails(){

        reportedProblemsListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, ProblemDetails::class.java)
            var currentActivityString=getString(R.string.problemHistoryAdmin)
            intent.putExtra("fullName",reportedProblemList.get(position).fullName)
            intent.putExtra("phoneNumber",reportedProblemList.get(position).phoneNumber)
            intent.putExtra("date",reportedProblemList.get(position).date)
            intent.putExtra("time",reportedProblemList.get(position).time)
            intent.putExtra("dorm",reportedProblemList.get(position).dorm)
            intent.putExtra("room",reportedProblemList.get(position).dormRoom)
            intent.putExtra("category",reportedProblemList.get(position).category)
            intent.putExtra("details",reportedProblemList.get(position).description)
            intent.putExtra("uploadedPicture",reportedProblemList.get(position).problemUploadedPicture)
            intent.putExtra("pictureUri",reportedProblemList.get(position).problemUploadedPictureUri)
            intent.putExtra("currentActivity",currentActivityString)
            startActivity(intent)
        }



    }
    fun openProfilePage(bottomBar:CustomComponents.BottomBar){
        bottomBar.profileButton.setOnClickListener {
            val intent = Intent(this, ProfileInfo::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }}
    fun openHomePage(bottomBar:CustomComponents.BottomBar){
        bottomBar.homeButton.setOnClickListener {
            val intent = Intent(this, MainInterfaceAdmin::class.java)
            intent.flags=Intent.FLAG_ACTIVITY_NO_ANIMATION
            startActivity(intent)
        }}
    override fun onBackPressed() {
        moveTaskToBack(true)
    }
}
