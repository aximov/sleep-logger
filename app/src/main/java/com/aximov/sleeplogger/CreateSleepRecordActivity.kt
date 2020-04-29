package com.aximov.sleeplogger

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.NumberPicker
import androidx.databinding.DataBindingUtil
import com.aximov.sleeplogger.databinding.ActivityCreateSleepRecordBinding
import kotlinx.android.synthetic.main.fragment_create_sleep_record.*
import java.lang.NumberFormatException

class CreateSleepRecordActivity : AppCompatActivity() {

    lateinit var binding: ActivityCreateSleepRecordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_sleep_record)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_sleep_record)

        binding.apply {
            durationPicker.minValue = 0
            durationPicker.maxValue = 24
            durationPicker.value = 8
        }

        binding.buttonCreateSleepRecord.setOnClickListener {
            val replyIntent = Intent()
            val duration = durationPicker.value
            replyIntent.putExtra(EXTRA_REPLY, duration)
            setResult(Activity.RESULT_OK, replyIntent)
            finish()
        }
    }

    companion object {
        const val EXTRA_REPLY = "com.aximov.sleeplogger.sleeplistsql.REPLY"
    }
}
