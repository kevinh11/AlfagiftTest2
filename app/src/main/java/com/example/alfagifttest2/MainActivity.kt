package com.example.alfagifttest2

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private var db = FirebaseFirestore.getInstance()
    private var studentList : MutableList<Student> = mutableListOf()
    private lateinit var studentAdapter : StudentsAdapter
    private lateinit var studentListView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        installSplashScreen()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        loadStudents(studentList)
        studentListView = findViewById<RecyclerView>(R.id.studentListView)
        val studentLayoutManager = LinearLayoutManager(this)
        studentAdapter = StudentsAdapter(studentList)
        studentListView.layoutManager = studentLayoutManager
        studentListView.adapter = studentAdapter
    }


    private fun loadStudents(studentsList : MutableList<Student>) {
        db.collection("students").get()
            .addOnSuccessListener { documents ->
                for (doc in documents) {
                    val name = doc.getString("Name")
                    val address = doc.getString("Address")
                    val img = doc.getString("imageUrl")

                    if (name == null || address == null || img == null) break
                    val newStudent = Student(
                        name,
                        address,
                        img
                    )
                    studentsList.add(newStudent)
                    Log.d("Student", "Student name: ${doc.getString("Name")}")
                }

                studentAdapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Error fetching students", exception)
            }
    }

}