package pl.d_osinski.datecounter

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_add_item.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit




class AddItemActivity : AppCompatActivity(){

    companion object {
        const val DATE_FORMATTER = "EEEE, dd.MM.yyyy"
    }
    var countDownTimer: CountDownTimer? = null
    var isRunning: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        button.setOnClickListener {
            showDatePicker()
        }
    }


    private fun setSelectedDateView(formattedDate: String, dateMilis: Long){
        textView.text = formattedDate
        button.text = dateMilis.toString()

        //tvCounter
        val startMillis = Calendar.getInstance().timeInMillis
        val totalMillis = dateMilis - startMillis

        counterMng(totalMillis)
    }

    private fun counterMng(totalMillis: Long){
       countDownTimer = object : CountDownTimer(totalMillis, 1000) {

            override fun onTick(millisUntilFinishedd: Long) {
                var millisUntilFinished = millisUntilFinishedd

                val days = TimeUnit.MILLISECONDS.toDays(millisUntilFinished)
                millisUntilFinished -= TimeUnit.DAYS.toMillis(days)

                val hours = TimeUnit.MILLISECONDS.toHours(millisUntilFinished)
                millisUntilFinished -= TimeUnit.HOURS.toMillis(hours)

                val minutes = TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)
                millisUntilFinished -= TimeUnit.MINUTES.toMillis(minutes)

                val seconds = TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished)

                val textform: String
                textform = if(days > 0) {
                    "$days days, $hours:$minutes:$seconds"
                }else{
                    "$hours:$minutes:$seconds"
                }
                isRunning = true
                tvCounter.text = textform
            }

            override fun onFinish() {

            }
        }.start()
    }

    private fun formatDate(time: Long): String{
        return SimpleDateFormat(DATE_FORMATTER, Locale.getDefault()).format(time)
    }

    private fun showDatePicker(){
        val calendarInstance = Calendar.getInstance()
        val day = calendarInstance.get(Calendar.DAY_OF_MONTH)
        val month = calendarInstance.get(Calendar.MONTH)
        val year = calendarInstance.get(Calendar.YEAR)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, yearSel, monthOfYear, dayOfMonth ->
                calendarInstance.set(yearSel, monthOfYear, dayOfMonth)
                if(isRunning){
                    countDownTimer?.cancel()
                }
                setSelectedDateView(formatDate(calendarInstance.timeInMillis), calendarInstance.timeInMillis)

            }, year, month, day
        )
        dpd.show()
    }


}
