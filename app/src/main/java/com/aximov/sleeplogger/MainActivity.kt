package com.aximov.sleeplogger

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import android.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var sleepViewModel: SleepViewModel
    private val createSleepRecordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerview_sleep)
        val adapter = SleepListAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        sleepViewModel = ViewModelProvider(this).get(SleepViewModel::class.java)
        sleepViewModel.allSleeps.observe(this, Observer { sleeps ->
            // Update the cached copy of the sleeps in the adapter.
            sleeps?.let { adapter.setSleeps(it) }
        })

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton_first)
        fab.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateSleepRecordActivity::class.java)
            startActivityForResult(intent, createSleepRecordActivityRequestCode)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == createSleepRecordActivityRequestCode && resultCode == Activity.RESULT_OK) {
            data?.getIntExtra(CreateSleepRecordActivity.EXTRA_REPLY, 0)?.let {
                val id = 0 // set this 0 to auto-generate id
                val sleep = Sleep(id, Date(), it)
                sleepViewModel.insert(sleep)
                Log.v("Saving", sleep.length.toString())
            }
        } else {
            Toast.makeText(
                applicationContext,
                "Not saved",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}
