package DatabaseHandlers

import android.content.Context
import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class ReportedProblem(
    problemID:String,
    name: String,
    surname: String,
    dorm: String,
    room: String,
    phoneNumber: String,
    date: String,
    time: String,
    category: String,
    problemDescription: String
) {
    var problemID:String
      var name: String
     var surname: String
      var dorm: String
     var room: String
     var phoneNumber:String
     var date: String
     var time: String
     var category: String
     var problemDescription: String
     var photoUri: String=""



    init {
        this.problemID=problemID
        this.name = name
        this.surname = surname
        this.dorm = dorm
        this.room = room
        this.phoneNumber=phoneNumber
        this.date = date
        this.time = time
        this.category = category
        this.problemDescription = problemDescription


    }

    constructor(
        problemID: String,
        name: String,
        surname: String,
        dorm: String,
        room: String,
        phoneNumber:String,
        date: String,
        time: String,
        category: String,
        problemDescription: String,
        photoUri: String
    ) : this(
        problemID,
        name,
        surname,
        dorm,
        room,
        phoneNumber,
        date,
        time,
        category,
        problemDescription

    ) {
        this.problemID=problemID
        this.name = name
        this.surname = surname
        this.dorm = dorm
        this.room = room
        this.phoneNumber=phoneNumber
        this.date = date
        this.time = time
        this.category = category
        this.problemDescription = problemDescription
        this.photoUri = photoUri


    }
}