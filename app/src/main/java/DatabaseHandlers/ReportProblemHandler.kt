package DatabaseHandlers

import CustomComponents.CategoryCard
import android.content.Context
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.time.LocalDate
import java.time.LocalTime

class ReportProblemHandler(
    problemID:String,
    name: String,
    surname: String,
    dorm: String,
    room: String,
    phoneNumber:String,
    date: String,
    time: String,
    category: String,
    problemDescription: String,
    context: Context
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
     var context: Context
      var photoUri: Uri? = null
    var storage: FirebaseStorage
    var reference: StorageReference


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
        this.context = context
        this.storage = FirebaseStorage.getInstance()
        this.reference = storage.getReference()

    }

    constructor(
        problemID:String,
        name: String,
        surname: String,
        dorm: String,
        room: String,
        phoneNumber: String,
        date: String,
        time: String,
        category: String,
        problemDescription: String,
        context: Context,
        photoUri: Uri?
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
        problemDescription,
        context

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
        this.context = context
        this.photoUri = photoUri
        this.storage = FirebaseStorage.getInstance()
        this.reference = storage.getReference()


    }

    fun reportProblem() {
        if (photoUri != null) {
            var reportedProblem =
                ReportedProblem(problemID,name, surname, dorm, room,phoneNumber, date, time, category, problemDescription,photoUri.toString())
            FirebaseAuth.getInstance().let { it1 ->
                FirebaseDatabase.getInstance().getReference("Reported Problems").child(
                    problemID
                ).setValue(reportedProblem).addOnCompleteListener {
                    Toast.makeText(context, "Your problem has been reported", Toast.LENGTH_LONG).show()

                }

                uploadPicture(photoUri,problemID)

            }

        } else {
            var reportedProblem =
                ReportedProblem(problemID,name, surname, dorm, room, phoneNumber, date, time, category, problemDescription)
            FirebaseAuth.getInstance().let { it1 ->
                FirebaseDatabase.getInstance().getReference("Reported Problems").child(
                    problemID
                ).setValue(reportedProblem).addOnCompleteListener {
                    Toast.makeText(context, "Your problem has been reported", Toast.LENGTH_SHORT).show()

                }

            }


        }

    }
    fun uploadPicture(uri: Uri?,problemID:String) {
        var imagesRef = reference.child("Reprted file Images/$problemID.jpg");
        if (uri != null) {
            imagesRef.putFile(uri).addOnSuccessListener {
                if (it.task.isSuccessful) {
                    Toast.makeText(context, "Image Upload succesfully", Toast.LENGTH_SHORT).show()
                    }

                else {

                    Toast.makeText(context, "Image Upload Failed", Toast.LENGTH_LONG).show()

                }

            }
        }


    }
}