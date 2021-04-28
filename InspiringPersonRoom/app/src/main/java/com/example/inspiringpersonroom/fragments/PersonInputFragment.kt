package com.example.inspiringpersonroom.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.inspiringpersonroom.R
import com.example.inspiringpersonroom.fragments.PersonListFragment
import com.example.inspiringpersonroom.room.InspiringPerson
import com.example.inspiringpersonroom.room.PersonDatabase
import kotlinx.android.synthetic.main.person_input_fragment.*

class PersonInputFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.person_input_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        inputButton.setOnClickListener {
            val newPerson = InspiringPerson(
                inputPhoto.text.toString(),
                inputName.text.toString(),
                inputBirthDate.text.toString(),
                inputDeathDate.text.toString(),
                inputDescription.text.toString(),
                inputQuote.text.toString()
            )

            PersonDatabase.getInstance(context!!).personDao().insertPerson(newPerson)

            inputPhoto.text.clear()
            inputName.text.clear()
            inputBirthDate.text.clear()
            inputDeathDate.text.clear()
            inputDescription.text.clear()
            inputQuote.text.clear()

            Toast.makeText(
                view.context,
                "Spremili ste osobu u repozitorij.",
                Toast.LENGTH_SHORT
            ).show()
        }

        peopleListActivity.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.container, PersonListFragment())
                ?.commit()
        }
    }
}