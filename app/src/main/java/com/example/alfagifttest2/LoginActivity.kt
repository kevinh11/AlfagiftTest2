package com.example.alfagifttest2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.firestore.FirebaseFirestore

class LoginActivity : AppCompatActivity() {

    private lateinit var userField : TextInputEditText
    private lateinit var passField : TextInputEditText
    private lateinit var errorText : TextView
    private lateinit var loginButton : Button

    private var db = FirebaseFirestore.getInstance()
    private var errorMsg = ""
//    private var navController: NavController = findNavController()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        installSplashScreen()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }



        userField = findViewById(R.id.user_field)
        passField = findViewById(R.id.pass_field)
        loginButton = findViewById(R.id.login_button)
        errorText = findViewById(R.id.error_text)


        loginButton.setOnClickListener({
            Login()
        })
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    private fun updateErrors(errorMsg : String) {
        errorText.visibility = View.VISIBLE
        errorText.text = errorMsg
    }

    private fun checkIsEmpty(): Boolean {
        val emailIsEmpty = userField.text.toString().trim().isEmpty()
        val passIsEmpty = passField.text.toString().trim().isEmpty()

        val msg = when {
            emailIsEmpty && passIsEmpty -> "Email and Password cannot be empty"
            emailIsEmpty && !passIsEmpty -> "Email cannot be empty"
            passIsEmpty && !emailIsEmpty -> "Password cannot be empty"
            else -> ""
        }

        // Only show error message if there is one
        if (msg.isNotEmpty()) {
            updateErrors(msg)
            return true
        }

        return false
    }



    private fun Login() {
        //lebih keren daripada string matching kak wkwkwk

        if (checkIsEmpty()) {
            return
        }

        db.collection("users")
            .whereEqualTo("username", userField.text.toString().trim())
            .whereEqualTo("password", passField.text.toString().trim()) //harusnya di hash kak
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (!querySnapshot.isEmpty) {
                    Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                } else {
//                    Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show()
                    updateErrors("Invalid username or password")
                }
            }
            .addOnFailureListener { error ->
                Log.d("Error", "Error getting documents: ", error)
            }


    }
}
