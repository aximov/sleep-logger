package com.aximov.sleeplogger

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.NumberPicker

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"

/**
 * A simple [Fragment] subclass.
 * Use the [CreateSleepRecordFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CreateSleepRecordFragment : Fragment() {
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_create_sleep_record, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val durationPicker = view.findViewById<NumberPicker>(R.id.durationPicker)
        durationPicker.minValue = 0
        durationPicker.maxValue = 24
        durationPicker.value = 8

        val button = view.findViewById<Button>(R.id.button_create_sleep_record)
        button.setOnClickListener {
            val replyIntent = Intent()
            val duration = durationPicker.value
            Log.v("Debug", duration.toString())
            replyIntent.putExtra(EXTRA_REPLY, duration)
            this.activity?.setResult(Activity.RESULT_OK, replyIntent)
            this.activity?.finish()
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @return A new instance of fragment CreateSleepRecordFragment.
         */
        @JvmStatic
        fun newInstance(param1: String) =
            CreateSleepRecordFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }

        const val EXTRA_REPLY = "com.aximov.sleeplogger.sleeplistsql.REPLY"
    }
}
