package com.app.uptfix

import DatabaseHandlers.LogInHandler
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.emailTextBox
import kotlinx.android.synthetic.main.activity_main.logInButton
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.blue_textbox.view.*

class MainActivity : AppCompatActivity() {
    lateinit var emailEditText: CustomComponents.EditText
    lateinit var passwordEditText: CustomComponents.EditText
    lateinit var signUp: TextView
    lateinit var forgotPassword: TextView
    lateinit var logInBttn: CustomComponents.BlueButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        emailEditText=emailTextBox
        passwordEditText=passwordTextBox
        signUp = textViewSignUp
        logInBttn = logInButton
        forgotPassword = textViewForgotPassword
        signUp(signUp)
        logIn(emailEditText,passwordEditText,logInBttn)
        forgotPassword(forgotPassword)
    }

    fun signUp(signUp: TextView) {
        signUp.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
        }
    }

    fun logIn(
        emailText: CustomComponents.EditText,
        passwordText: CustomComponents.EditText,
        logInBttn: CustomComponents.BlueButton
    ) {
            logInBttn.layout.setOnClickListener {
                if (verifyData(emailText, passwordText)) {
                var email = emailTextBox.editText.text.toString()
                var password = passwordTextBox.editText.text.toString()
                val logIn = LogInHandler(email, password, this)
                logIn.openMainInterrface()

            }
        }


    }

    fun forgotPassword(forgotPassword: TextView) {
        forgotPassword.setOnClickListener {
            val intent = Intent(this, ResetPassword::class.java)
            startActivity(intent)
        }
    }

    fun verifyData(
        emailText: CustomComponents.EditText,
        passwordText: CustomComponents.EditText
    ): Boolean {
        var textEmail: String = emailText.customEditText.text.toString()
        var textPassword: String = passwordText.customEditText.text.toString()
        var nameVerify = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if (textEmail.isEmpty()) {
            emailText.customEditText.requestFocus()
            emailText.customEditText.setError(getString(R.string.errorEmptyEmail))
            return false;

        }
        if (!(textEmail.matches(nameVerify))) {
            emailText.customEditText.requestFocus()
            emailText.customEditText.setError(getString(R.string.validEmail))
            return false;

        }
        if (textPassword.isEmpty()) {
            passwordText.customEditText.requestFocus()
            passwordText.customEditText.setError(getString(R.string.errorEmptyPassword))
            return false;

        }
        return true;
    }


}

