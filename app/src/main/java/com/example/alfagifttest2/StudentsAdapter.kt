package com.example.alfagifttest2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.helper.widget.Carousel.Adapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class StudentsAdapter(private val students: MutableList<Student>) : RecyclerView.Adapter<StudentsAdapter.StudentViewHolder>() {


    inner class StudentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val idTextView: TextView = itemView.findViewById(R.id.student_id)
        val nameTextView: TextView = itemView.findViewById(R.id.student_name)
        val addressTextView: TextView = itemView.findViewById(R.id.student_address)
        val studentImageView : ImageView = itemView.findViewById(R.id.student_image)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StudentViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.student_1line, parent, false)
        return StudentViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: StudentViewHolder, position: Int) {
        // Get the current student
        val student = students[position]

//        holder.idTextView.text = student.id
        holder.nameTextView.text = student.name
        holder.addressTextView.text = student.address

        Glide.with(holder.itemView.context)
            .load(student.profileImgUrl) // Replace `imageUrl` with the actual field in your `Student` class
            .placeholder(R.drawable.baseline_person_24) // Optional: Add a placeholder image
            .error(R.drawable.baseline_person_24) // Optional: Add an error image
            .into(holder.studentImageView)


    }

    override fun getItemCount(): Int {
        return students.size
    }
}
