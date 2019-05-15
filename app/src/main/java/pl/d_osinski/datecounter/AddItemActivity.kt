package pl.d_osinski.datecounter

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_item.*
import java.util.*
import java.util.concurrent.TimeUnit


class AddItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        button.setOnClickListener{
            val c = Calendar.getInstance()
            var day = c.get(Calendar.DAY_OF_MONTH)
            var month = c.get(Calendar.MONTH)
            var year = c.get(Calendar.YEAR)

            val dpd = DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, yearSel, monthOfYear, dayOfMonth ->
                    day = dayOfMonth
                    month = monthOfYear + 1
                    year = yearSel
                    c.set(year, month, day)

                    button.text = c.timeInMillis.toString()
                    startCountDown(c.timeInMillis)
                }, year, month, day
            )

            dpd.show()
        }
         //textView.text = calculate(time)
    }

    private fun calculate(end: Long): String{
        return (end - System.currentTimeMillis()).toString()
    }

    private fun startCountDown(endMilis: Long) {
        val startCalendar = Calendar.getInstance()
        val startMillis = startCalendar.timeInMillis//get the start time in milliseconds
        val totalMillis = endMilis - startMillis //total time in milliseconds
        //start countDown
        val countDownTimer = object : CountDownTimer(totalMillis, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                var millisUntilFinished = millisUntilFinished
                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours)

                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes)

                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)

                textView.text = ("$hours  $minutes  $seconds")
            }

            override fun onFinish() {

            }

        }
        countDownTimer.start()
    }
}
