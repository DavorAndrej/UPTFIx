package DatabaseHandlers

import android.content.Context
import android.content.Intent
import android.provider.Settings.Global.getString
import android.view.View
import android.widget.ProgressBar
import com.app.uptfix.R
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.app.uptfix.MainActivity
import com.google.firebase.database.FirebaseDatabase
import kotlin.coroutines.coroutineContext

class CreateAccountHandler(
    email: String,
    password: String,
    name: String,
    surname: String,
    dorm: String,
    accountType: String,
    context: Context
) {
    var email: String
    var password: String
    var name: String
    var surname: String
    var dorm: String
    var room: String = ""
    var phoneNumber: String =""
    var accountType: String
    var authf: FirebaseAuth? = null
    var context: Context

    init {

        this.email = email
        this.password = password
        this.name = name
        this.surname = surname
        this.dorm = dorm
        this.accountType = accountType
        this.authf = FirebaseAuth.getInstance()
        this.context = context


    }

    constructor(
        email: String,
        password: String,
        name: String,
        surname: String,
        dorm: String,
        accountType: String,
        context: Context,
        phoneNumber:String,
        room: String
    ) : this(
        email,
        password,
        name,
        surname,
        dorm,
        accountType,
        context

    ) {
        this.email = email
        this.password = password
        this.name = name
        this.surname = surname
        this.accountType = accountType
        this.dorm = dorm
        this.room = room
        this.phoneNumber=phoneNumber
        this.authf = FirebaseAuth.getInstance()
        this.context = context

    }

    fun createAccount() {
        authf?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener {

            if (it.isSuccessful) {
                var user = User(email, password, name, surname, dorm, accountType, phoneNumber,room)
                FirebaseAuth.getInstance().currentUser?.uid?.let { it1 ->
                    FirebaseDatabase.getInstance().getReference("Users").child(
                        it1
                    ).setValue(user).addOnCompleteListener {
                        Toast.makeText(context, "Account Created", Toast.LENGTH_SHORT).show()

                    }
                }
            } else {
                Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show()
            }

        }


    }


}


