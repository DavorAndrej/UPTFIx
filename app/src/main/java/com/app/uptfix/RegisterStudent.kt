package com.app.uptfix

import DatabaseHandlers.CreateAccountHandler
import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_register_student.*
import kotlinx.android.synthetic.main.blue_button.view.*
import kotlinx.android.synthetic.main.blue_textbox.view.*

class RegisterStudent : AppCompatActivity() {
    lateinit var dormSpinner: CustomComponents.BlueSpinner
    lateinit var phoneNumber: CustomComponents.EditText
    lateinit var roomText: CustomComponents.EditText
    lateinit var password: CustomComponents.EditText
    lateinit var confirmPassword: CustomComponents.EditText
    lateinit var createAccountButton: CustomComponents.BlueButton
    lateinit var name: String
    lateinit var surname: String
    lateinit var email: String
    lateinit var accountType: String
    lateinit var dorm: String
    lateinit var room: String
    lateinit var passwdText: String
    lateinit var phoneNo: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_student)
        dormSpinner = blueDormSpinner
        phoneNumber=phoneNumberText
        roomText = roomNumberText
        password = passwordText
        confirmPassword = confirmPasswdText
        createAccountButton = finishButton
        name = intent.getStringExtra("name").toString()
        surname = intent.getStringExtra("surname").toString()
        email = intent.getStringExtra("email").toString()
        accountType = intent.getStringExtra("accountType").toString()
        openPage(roomText, password, confirmPassword, createAccountButton)

    }

    fun openPage(
        roomText: CustomComponents.EditText,
        passwordText: CustomComponents.EditText,
        confirmPassword: CustomComponents.EditText,
        finishButton: CustomComponents.BlueButton
    ) {

        finishButton.customBlueButtonLayout.setOnClickListener {
            dorm= dormSpinner.customSpinner.selectedItem.toString()
            room = roomText.customEditText.text.toString()
            phoneNo=phoneNumberText.customEditText.text.toString()
            if (verifyRoom(roomText) && passwordVerify(passwordText, confirmPassword)) {
                passwdText= passwordText.customEditText.text.toString()
                var createAcc = CreateAccountHandler(email, passwdText, name, surname, dorm,accountType,this,phoneNo,room)
                createAcc.createAccount()

                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)



            }

        }


    }

    fun verifyRoom(roomText: CustomComponents.EditText): Boolean {
        var text: String = roomText.customEditText.text.toString()
        var nameVerify = Regex("^([1-9][0-9]{0,2}|600)\$")
        if (text.isEmpty()) {
            roomText.customEditText.requestFocus()
            roomText.customEditText.setError(getString(R.string.errorEmptyRoom))
            return false;

        }
        if (!(text.matches(nameVerify))) {
            roomText.customEditText.requestFocus()
            roomText.customEditText.setError(getString(R.string.validRoomNumber))
            return false;

        }
        return true; }

    fun passwordVerify(
        passwdText: CustomComponents.EditText,
        confirmPasswdText: CustomComponents.EditText
    ): Boolean {
        var text: String = passwdText.customEditText.text.toString()
        var textConfirm: String = confirmPasswdText.customEditText.text.toString()
        if (text.isEmpty()) {
            passwdText.customEditText.requestFocus()
            passwdText.customEditText.setError(getString(R.string.errorEmptyPassword))
            return false;

        }

        if (textConfirm.isEmpty()) {
            confirmPasswdText.customEditText.requestFocus()
            confirmPasswdText.customEditText.setError(getString(R.string.errorEmptyPassword))
            return false;

        }
        if (!(text.equals(textConfirm))) {
            confirmPasswdText.customEditText.setError(getString(R.string.matchingPasswords))
            return false;

        }
        return true; }


}
