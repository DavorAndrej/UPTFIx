package com.app.uptfix

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_register_page.*
import kotlinx.android.synthetic.main.blue_button.view.*
import kotlinx.android.synthetic.main.blue_spinner.view.*
import kotlinx.android.synthetic.main.blue_textbox.view.*

class RegisterPage : AppCompatActivity() {
    lateinit var nextButton: CustomComponents.BlueButton
    lateinit var accountTypeSpinner: CustomComponents.BlueSpinner
    lateinit var nameText: CustomComponents.EditText
    lateinit var surnameText: CustomComponents.EditText
    lateinit var emailText: CustomComponents.EditText
    lateinit var name: String
    lateinit var surname: String
    lateinit var email: String
    lateinit var accountType: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_page)
        nameText = nameEditText
        surnameText=surnameEditText
        emailText=emailEditText
        nextButton = nextPageButton
        accountTypeSpinner = accountSpinner
        openPage(nameText, surnameText,emailText,nextButton, accountTypeSpinner)
    }

    fun openPage(
        nameText: CustomComponents.EditText,surnameText: CustomComponents.EditText,emailText: CustomComponents.EditText,
        button: CustomComponents.BlueButton,
        spinner: CustomComponents.BlueSpinner
    ) {
        val profileType = resources.getStringArray(R.array.Profile)
        button.customBlueButtonLayout.setOnClickListener {

            if (verifyName(nameText) && verifySurname(surnameText) && verifyEmail(emailText)) {
                name=nameText.customEditText.text.toString()
                surname=surnameText.customEditText.text.toString()
                email=emailText.customEditText.text.toString()
                accountType=spinner.customBlueSpinner.selectedItem.toString()
                if (spinner.customBlueSpinner.selectedItem.toString()
                        .equals(profileType.get(0).toString())
                ) {
                    val intent = Intent(this, RegisterStudent::class.java)
                    intent.putExtra("name",name)
                    intent.putExtra("surname",surname)
                    intent.putExtra("email",email)
                    intent.putExtra("accountType",accountType)
                    startActivity(intent)
                } else if (spinner.customBlueSpinner.selectedItem.toString()
                        .equals(profileType.get(1).toString())
                ) {
                    val intent = Intent(this, RegisterAdmin::class.java)
                    intent.putExtra("name",name)
                    intent.putExtra("surname",surname)
                    intent.putExtra("email",email)
                    intent.putExtra("accountType",accountType)
                    startActivity(intent)
                }
            }
        }
    }


    fun verifyName(nameText: CustomComponents.EditText): Boolean {
        var text: String = nameText.customEditText.text.toString()
        var nameVerify = Regex("^[A-Z][-'a-zA-Z]+")
        if (text.isEmpty()) {
            nameText.customEditText.requestFocus()
            nameText.customEditText.setError(getString(R.string.errorEmptyName))
            return false;

        }
        if (!(text.matches(nameVerify))) {
            nameText.customEditText.requestFocus()
            nameText.customEditText.setError(getString(R.string.validName))
            return false;

        }
        return true; }
    fun verifySurname(surnameText: CustomComponents.EditText): Boolean {
        var text: String = surnameText.customEditText.text.toString()
        var nameVerify = Regex("^[A-Z][-'a-zA-Z]+")
        if (text.isEmpty()) {
            surnameText.customEditText.requestFocus()
            surnameText.customEditText.setError(getString(R.string.errorEmptySurname))
            return false;

        }
        if (!(text.matches(nameVerify))) {
            surnameText.customEditText.requestFocus()
            surnameText.customEditText.setError(getString(R.string.validSurname))
            return false;

        }
        return true; }

    fun verifyEmail(emailText: CustomComponents.EditText): Boolean {
        var text: String = emailText.customEditText.text.toString()
        var nameVerify = Regex("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$")
        if (text.isEmpty()) {
            emailText.customEditText.requestFocus()
            emailText.customEditText.setError(getString(R.string.errorEmptyEmail))
            return false;

        }
        if (!(text.matches(nameVerify))) {
            emailText.customEditText.requestFocus()
            emailText.customEditText.setError(getString(R.string.validEmail))
            return false;

        }
        return true; }
}
