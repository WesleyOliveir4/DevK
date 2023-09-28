package com.example.devk.data.Message

import android.content.Context
import android.content.Intent
import com.example.devk.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_message_app.*
import java.util.*


class MessageBuilder(
    private val context: Context,
) {

    fun MessageShow(message: String) {

        val bottomSheet: BottomSheetDialog =
            BottomSheetDialog(context, R.style.BottomSheetStyle)
        bottomSheet.setContentView(R.layout.dialog_message_app)
        bottomSheet.edt_input.text = message
        bottomSheet.show()
    }


    fun MessageShowTimer(message: String, delay: Long?) {

        if (delay!! < 0) {delay == 0L  }
        val timer: Timer = Timer()

        val bottomSheet: BottomSheetDialog =
            BottomSheetDialog(context, R.style.BottomSheetStyle)
        bottomSheet.setContentView(R.layout.dialog_message_app)
        bottomSheet.edt_input.text = message
        bottomSheet.show()

        timer.schedule(object : TimerTask() {
            override fun run() {
                bottomSheet.dismiss()
            }
        }, delay)
    }


}