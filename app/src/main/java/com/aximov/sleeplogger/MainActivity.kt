package com.aximov.sleeplogger

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aximov.sleeplogger.databinding.ActivityMainBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var sleepViewModel: SleepViewModel
    private val createSleepRecordActivityRequestCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)

        val adapter = SleepListAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        binding.apply {
            recyclerviewSleep.adapter = adapter
            recyclerviewSleep.layoutManager = layoutManager
        }

        sleepViewModel = ViewModelProvider(this).get(SleepViewModel::class.java)
        sleepViewModel.allSleeps.observe(this, Observer { sleeps ->
            // Update the cached copy of the sleeps in the adapter.
            sleeps?.let { adapter.setSleeps(it) }
        })
        sleepViewModel.sleepLengthSum.observe(this, Observer { sleepSum ->
            sleepSum?.let { binding.sleepSum.text = it.toString() }
        })

        val chart = binding.chart
        chart.xAxis.setDrawGridLines(false)
        chart.axisLeft.apply {
            axisMinimum = 0f
            axisMaximum = 12f
            labelCount = 6
            setDrawTopYLabelEntry(true)

        }
        chart.axisRight.apply {
            setDrawLabels(false)
            setDrawGridLines(false)
            setDrawZeroLine(false)
            setDrawTopYLabelEntry(true)
        }
        chart.apply {
            setDrawValueAboveBar(true)
            description.isEnabled = false
            isClickable = true
            legend.isEnabled = false //凡例
            setScaleEnabled(false)
            animateY(2400, Easing.Linear)
        }

        val entries = ArrayList<BarEntry>().apply {
            add(BarEntry(5f, 10f))
            add(BarEntry(6f, 18f))
            add(BarEntry(17f, 3f))
        }

        val dataSet = BarDataSet(entries, "Series 1")
        val lines = ArrayList<IBarDataSet>()
        lines.add(dataSet)
        chart.data = BarData(lines)


        binding.floatingActionButtonFirst.setOnClickListener {
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
            val id = 0 // set this 0 to auto-generate id
            var duration = 0
            var year = 2020
            var month = 0
            var dayOfMonth = 1
            data?.getIntExtra(CreateSleepRecordActivity.EXTRA_REPLY_DURATION, 0)?.let {
                duration = it
            }
            data?.getIntExtra(CreateSleepRecordActivity.EXTRA_REPLY_YEAR, 2020)?.let {
                year = it
            }
            data?.getIntExtra(CreateSleepRecordActivity.EXTRA_REPLY_MONTH, 0)?.let {
                month = it
            }
            data?.getIntExtra(CreateSleepRecordActivity.EXTRA_REPLY_DAY_OF_MONTH, 1)?.let {
                dayOfMonth = it
            }
            val calendar = Calendar.getInstance()
            calendar.clear()
            calendar.set(year + 1900, month, dayOfMonth)
            val sleep = Sleep(id, calendar.time, duration)
            sleepViewModel.insert(sleep)
            Toast.makeText(
                applicationContext,
                "Saved!",
                Toast.LENGTH_LONG
            ).show()
        } else {
            Toast.makeText(
                applicationContext,
                "Not saved",
                Toast.LENGTH_LONG
            ).show()
        }
    }

}
