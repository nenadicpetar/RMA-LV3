package com.example.inspiringpersonroom.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.inspiringpersonroom.R
import com.example.inspiringpersonroom.room.InspiringPerson
import com.example.inspiringpersonroom.room.PersonDatabase
import kotlinx.android.synthetic.main.person_input_fragment.*

class PersonEditFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.person_input_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val bundle = arguments
        val personName = bundle?.getString("personName") ?: return
        val personDao = PersonDatabase.getInstance(context!!).personDao()
        val inspiringPerson = personDao.getPersonByName(personName)

        inputPhoto.setText(inspiringPerson.photoUrl)
        inputName.setText(inspiringPerson.name)
        inputBirthDate.setText(inspiringPerson.birthDate)
        inputDeathDate.setText(inspiringPerson.deathDate)
        inputDescription.setText(inspiringPerson.description)
        inputQuote.setText(inspiringPerson.quote)

        inputButton.setOnClickListener {
            val tmpPerson = InspiringPerson(
                inputPhoto.text.toString(),
                inputName.text.toString(),
                inputBirthDate.text.toString(),
                inputDeathDate.text.toString(),
                inputDescription.text.toString(),
                inputQuote.text.toString()
            )

            personDao.updatePerson(tmpPerson)
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