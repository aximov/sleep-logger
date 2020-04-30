package com.aximov.sleeplogger

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.DatePicker
import androidx.databinding.DataBindingUtil
import com.aximov.sleeplogger.databinding.ActivityCreateSleepRecordBinding
import java.util.*

class CreateSleepRecordActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {

    private lateinit var binding: ActivityCreateSleepRecordBinding
    private var year = 2020
    private var month = 1
    private var dayOfMonth = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_sleep_record)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_sleep_record)

        binding.apply {
            durationPicker.minValue = 0
            durationPicker.maxValue = 24
            durationPicker.value = 8
        }

        val today = Calendar.getInstance()
        year = today.get(Calendar.YEAR)
        month = today.get(Calendar.MONTH)
        dayOfMonth = today.get(Calendar.DAY_OF_MONTH)
        val todayString = String.format(Locale.US, "%d/%d/%d", year, month + 1, dayOfMonth)

        binding.textViewDate.text = todayString
        binding.textViewDate.inputType = InputType.TYPE_NULL
        binding.textViewDate.setOnClickListener {
            showDatePickerDialog(it)
        }

        binding.buttonCreateSleepRecord.setOnClickListener {
            val replyIntent = Intent()

            val duration = binding.durationPicker.value
            replyIntent.putExtra(EXTRA_REPLY_DURATION, duration)
            replyIntent.putExtra(EXTRA_REPLY_YEAR, year)
            replyIntent.putExtra(EXTRA_REPLY_MONTH, month)
            replyIntent.putExtra(EXTRA_REPLY_DAY_OF_MONTH, dayOfMonth)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }

    }

    override fun onDateSet(view: DatePicker, year: Int, monthOfYear: Int, dayOfMonth: Int) {

        this.year = year
        this.month = monthOfYear
        this.dayOfMonth = dayOfMonth
        val str = String.format(Locale.US, "%d/%d/%d", year, monthOfYear + 1, dayOfMonth)
        binding.textViewDate.text = str

    }

    private fun showDatePickerDialog(v: View) {
        val datePicker = DatePickerFragment()
        datePicker.show(supportFragmentManager, "datePicker")

    }

    companion object {
        const val EXTRA_REPLY_DURATION = "com.aximov.sleeplogger.sleeplistsql.REPLY_DURATION"
        const val EXTRA_REPLY_YEAR = "com.aximov.sleeplogger.sleeplistsql.REPLY_YEAR"
        const val EXTRA_REPLY_MONTH = "com.aximov.sleeplogger.sleeplistsql.REPLY_MONTH"
        const val EXTRA_REPLY_DAY_OF_MONTH =
            "com.aximov.sleeplogger.sleeplistsql.REPLY_DAY_OF_MONTH"
    }
}
