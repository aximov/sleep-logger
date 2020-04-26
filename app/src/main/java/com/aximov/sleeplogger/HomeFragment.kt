package com.aximov.sleeplogger

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class HomeFragment : Fragment() {

    private lateinit var sleepViewModel: SleepViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<FloatingActionButton>(R.id.floatingActionButton_first).setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_CreateSleepRecordActivity)
        }

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerview_sleep)
        val adapter = this.context?.let { SleepListAdapter(it) }
        if (recyclerView != null) {
            recyclerView.adapter = adapter
        }
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(this.context)
        }

        sleepViewModel = ViewModelProvider(this).get(SleepViewModel::class.java)
        sleepViewModel.allSleeps.observe(viewLifecycleOwner, Observer { sleeps ->
            sleeps?.let {
                adapter?.setSleeps(it)
            }
        })
    }

    private val createSleepActivityRequestCode = 1

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == createSleepActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getIntExtra(CreateSleepRecordFragment.EXTRA_REPLY, 0)?.let {
                val sleep = Sleep(0,Date(),it)
                sleepViewModel.insert(sleep)
            }
        } else {
            Toast.makeText(
                this.activity?.applicationContext,
                "Not saved",
                Toast.LENGTH_LONG).show()
        }
    }
}
