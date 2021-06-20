package com.app.uptfix

import DatabaseHandlers.CreateAccountHandler
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register_admin.*
import kotlinx.android.synthetic.main.blue_button.view.*
import kotlinx.android.synthetic.main.blue_textbox.view.*

class RegisterAdmin : AppCompatActivity() {
    lateinit var dormSpinner: CustomComponents.BlueSpinner
    lateinit var password: CustomComponents.EditText
    lateinit var confirmPassword: CustomComponents.EditText
    lateinit var createAccountButton: CustomComponents.BlueButton
    lateinit var name: String
    lateinit var surname: String
    lateinit var email: String
    lateinit var accountType: String
    lateinit var dorm: String
    lateinit var passwdText: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_admin)
        dormSpinner = blueDormSpinner
        password = passwordText
        confirmPassword = confirmPasswdText
        createAccountButton = finishButton
        name = intent.getStringExtra("name").toString()
        surname = intent.getStringExtra("surname").toString()
        email = intent.getStringExtra("email").toString()
        accountType = intent.getStringExtra("accountType").toString()
        openPage(password,confirmPassword,createAccountButton)
    }
    fun openPage(
        passwordText: CustomComponents.EditText,
        confirmPassword: CustomComponents.EditText,
        finishButton: CustomComponents.BlueButton
    ) {

        finishButton.customBlueButtonLayout.setOnClickListener {
            if(passwordVerify(passwordText,confirmPassword)){
                dorm= dormSpinner.customSpinner.selectedItem.toString()
                passwdText= passwordText.customEditText.text.toString()
                var createAcc = CreateAccountHandler(email, passwdText, name, surname, dorm,accountType,this)
                createAcc.createAccount()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }


        }


    }

    fun passwordVerify(passwdText:CustomComponents.EditText,confirmPasswdText: CustomComponents.EditText): Boolean {
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
        if(!(text.equals(textConfirm))){
            confirmPasswdText.customEditText.setError(getString(R.string.matchingPasswords))
            return false;

        }
        return true; }
}
