package com.example.squaretake_homeproject.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.squaretake_homeproject.R
import com.example.squaretake_homeproject.data.model.Employee

class EmployeeDirectoryListAdapter(
    var employeesList: List<Employee>
) : RecyclerView.Adapter<EmployeeDirectoryListAdapter.EmployeeDirectoryListViewHolder>() {

    class EmployeeDirectoryListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val pictureView = itemView.findViewById<AppCompatImageView>(R.id.employee_picture)
        private val nameText = itemView.findViewById<AppCompatTextView>(R.id.employee_name)
        private val phoneText = itemView.findViewById<AppCompatTextView>(R.id.employee_phone)
        private val emailText = itemView.findViewById<AppCompatTextView>(R.id.employee_email)
        private val teamNameText = itemView.findViewById<AppCompatTextView>(R.id.employee_team_name)
        private val summaryText = itemView.findViewById<AppCompatTextView>(R.id.employee_summary)

        fun bind(employee: Employee) {
            employee.apply {
                Glide.with(itemView)
                    .load(employee.photoSmall)
                    .placeholder(R.drawable.ic_employee_picture_placeholder)
                    .into(pictureView)

                nameText.text = name
                phoneText.text = phoneNumber
                emailText.text = email
                teamNameText.text = teamName
                summaryText.text = summary
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmployeeDirectoryListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.employee_list_item, parent, false)

        return EmployeeDirectoryListViewHolder(view)
    }

    override fun onBindViewHolder(holder: EmployeeDirectoryListViewHolder, position: Int) {
        holder.bind(employeesList[position])
    }

    override fun getItemCount(): Int {
        return employeesList.size
    }
}