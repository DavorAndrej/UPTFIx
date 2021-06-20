package DatabaseHandlers

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.app.uptfix.MainInterfaceAdmin
import com.app.uptfix.MainInterfaceStudent
import com.app.uptfix.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import com.google.firebase.firestore.FirebaseFirestore


class LogInHandler(
    email: String,
    password: String,
    context: Context
) {

    var email: String
    var password: String
    var authf: FirebaseAuth? = null
    var context: Context
    var databaseReference: DatabaseReference
    var currentUser:FirebaseUser?
    var userID: String


    init {
        this.email = email
        this.password = password
        this.authf = FirebaseAuth.getInstance()
        this.context = context
        this.databaseReference = FirebaseDatabase.getInstance().reference
        this.currentUser= authf!!.currentUser
        this.userID = currentUser?.uid.toString()
    }
    interface MyCallback {
        fun onCallback(profileType:String?)
    }
    fun logInWithEmailAndPassword(myCallback: MyCallback) {

        authf?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener {

            if (it.isSuccessful) {
                databaseReference.addValueEventListener(object : ValueEventListener {

                    override fun onDataChange(dataSnapshot: DataSnapshot) {
                        val profileType =
                            dataSnapshot.child("Users").child(userID).child("accountType")
                                .getValue(String::class.java)
                        myCallback.onCallback(profileType)
                    }

                    override fun onCancelled(error: DatabaseError) {
                    }

                })
            } else {
                Toast.makeText(
                    context,
                    "Log-in Failed Please enter a valid E-mail and Password!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }


    }

    fun openMainInterrface(){
        logInWithEmailAndPassword(object : MyCallback {
            override fun onCallback(profileType:String?) {
                if (profileType.equals(context.getString(R.string.accTypeStudent))) {
                    Toast.makeText(context, "Log-in Succesful!", Toast.LENGTH_SHORT).show()
               val intent = Intent(context, MainInterfaceStudent::class.java)
               startActivity(context, intent, null)


           }
           else if (profileType.equals(context.getString(R.string.accTypeAdmin))) {
                    Toast.makeText(context, "Log-in Succesful!", Toast.LENGTH_SHORT).show()
               val intent = Intent(context, MainInterfaceAdmin::class.java)
               startActivity(context, intent, null)

           }
            }
        })


    }
}