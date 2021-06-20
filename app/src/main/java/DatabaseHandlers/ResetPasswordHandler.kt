package DatabaseHandlers

import android.content.Context
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class ResetPasswordHandler( email: String,  context: Context) {
    var email: String
    var authf: FirebaseAuth? = null
    var context: Context
    init {
        this.email = email
        this.authf = FirebaseAuth.getInstance()
        this.context = context
    }
    fun sendResetPasswordLink(){

        authf?.sendPasswordResetEmail(email)?.addOnCompleteListener {

            if (it.isSuccessful) {
                Toast.makeText(context, "Link sent succesfully", Toast.LENGTH_LONG).show()

            } else{
                Toast.makeText(context, "Please enter an existing e-mail", Toast.LENGTH_SHORT).show()
            }
        }


    }
}