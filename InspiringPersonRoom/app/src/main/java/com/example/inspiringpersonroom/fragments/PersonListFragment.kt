package com.example.inspiringpersonroom.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.inspiringpersonroom.R
import com.example.inspiringpersonroom.adapters.ItemClickType
import com.example.inspiringpersonroom.adapters.PersonListAdapter
import com.example.inspiringpersonroom.room.PersonDatabase
import kotlinx.android.synthetic.main.person_list_fragment.*

class PersonListFragment : Fragment(), PersonListAdapter.ContentListener {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.person_list_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val personData = context?.let { PersonDatabase.getInstance(it).personDao().getPersons() }

        personList.layoutManager = LinearLayoutManager(view.context)
        personList.adapter = PersonListAdapter(personData?.toMutableList(), this)

        newPeopleButton.setOnClickListener {
            fragmentManager?.beginTransaction()?.replace(R.id.container, PersonInputFragment())?.commit()
        }
    }

    override fun onItemClick(id: Int, name: String, clickType: ItemClickType) {
        if (clickType == ItemClickType.EDIT) {
            val bundle = Bundle()
            bundle.putString("personName", name)

            val editFragment = PersonEditFragment()
            editFragment.arguments = bundle

            fragmentManager?.beginTransaction()?.replace(R.id.container, editFragment)?.commit()
        }
        else if (clickType == ItemClickType.REMOVE) {
            context?.let { PersonDatabase.getInstance(it).personDao().deletePerson(name) }
            (personList.adapter as PersonListAdapter).removeItem(id)
        }
    }
}