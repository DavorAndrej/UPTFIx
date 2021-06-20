package DatabaseHandlers

import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseAuth

class User(
    email: String,
    password: String,
    name: String,
    surname: String,
    dorm: String,
    accountType: String
) {
    var email: String
    var password: String
    var name: String
    var surname: String
    var dorm:String
    var room: String = ""
    var phoneNumber: String=""
    var accountType: String

    init {

        this.email = email
        this.password = password
        this.name = name
        this.surname = surname
        this.dorm=dorm
        this.accountType = accountType

    }

    constructor(
        email: String,
        password: String,
        name: String,
        surname: String,
        dorm: String,
        accountType: String,
        phoneNumber:String,
        room: String
    ) : this(
        email,
        password,
        name,
        surname,
        dorm,
        accountType
    ) {
        this.email = email
        this.password = password
        this.name = name
        this.surname = surname
        this.accountType = accountType
        this.dorm=dorm
        this.phoneNumber=phoneNumber
        this.room = room

    }
}