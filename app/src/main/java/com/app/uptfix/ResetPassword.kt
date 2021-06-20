package com.app.uptfix

import DatabaseHandlers.ResetPasswordHandler
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_reset_password.*
import kotlinx.android.synthetic.main.blue_button.view.*
import kotlinx.android.synthetic.main.blue_textbox.view.*

class ResetPassword : AppCompatActivity() {
    lateinit var emailText: CustomComponents.EditText
    lateinit var sendLink: CustomComponents.BlueButton
    lateinit var backButton: ImageView
    lateinit var email: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        emailText=emailTextBox
        sendLink=sendResetLinkButton
        backButton=exitButton
        resetPassword(emailText,sendLink)
        returntoLoginPage()
    }

    fun resetPassword(
        emailText: CustomComponents.EditText,
        sendLink: CustomComponents.BlueButton
    ) {
        sendLink.customBlueButtonLayout.setOnClickListener{
            if (verifyEmail(emailText)){
                email= emailText.customEditText.text.toString()
                var sendResetLin= ResetPasswordHandler(email,this)
                sendResetLin.sendResetPasswordLink()

            }



        }
    }
    fun returntoLoginPage(){
        backButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)

        }


    }
    fun verifyEmail(emailText: CustomComponents.EditText): Boolean {
        var text: String = emailText.customEditText.text.toString()
        var nameVerify = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if (text.isEmpty()) {
            emailText.customEditText.requestFocus()
            emailText.customEditText.setError(getString(R.string.errorEmptyEmail))
            return false

        }
        if (!(text.matches(nameVerify))) {
            emailText.customEditText.requestFocus()
            emailText.customEditText.setError(getString(R.string.validEmail))
            return false

        }
        return true; }
}
