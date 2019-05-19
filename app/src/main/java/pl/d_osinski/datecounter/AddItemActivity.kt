package pl.d_osinski.datecounter

import android.app.DatePickerDialog
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import pl.d_osinski.datecounter.databinding.ActivityAddItemBinding
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class AddItemActivity : AppCompatActivity() {

    companion object {
        const val DATE_FORMATTER = "EEEE, dd.MM.yyyy"
    }

    var countDownTimer: CountDownTimer? = null
    var isRunning: Boolean = false

    private lateinit var binding: ActivityAddItemBinding
    private val dataBind: DataBinding = DataBinding("","", "Select date")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_item)
        binding.dataBinding = dataBind
        binding.button.setOnClickListener {
            showDatePicker()
        }
    }


    private fun setSelectedDateView(formattedDate: String, dateMilis: Long) {
        binding.apply {
            dataBind.dateFormatted = formattedDate
            dataBind.buttonText = dateMilis.toString()
        }

        //tvCounter
        val startMillis = Calendar.getInstance().timeInMillis
        val totalMillis = dateMilis - startMillis

        counterMng(totalMillis)
    }

    private fun counterMng(totalMillis: Long) {
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

                var textform = ""
                if (days > 0) {
                     textform = "$days days, "
                }
                textform += timeFormatter(hours) + ":" + timeFormatter(minutes) + ":" + timeFormatter(seconds)

                isRunning = true
                binding.apply {
                    dataBind.dateCounting = textform
                    invalidateAll()
                }
            }

            override fun onFinish() {

            }
        }.start()
    }

    //TODO make function which adding '0' when hour, min and sec are 1-9 (01, 02)
    private fun timeFormatter(timeText: Long): String {
        return if (timeText < 10){
            "0$timeText"
        }else{
            timeText.toString()
        }
    }

    private fun formatDate(time: Long): String {
        return SimpleDateFormat(DATE_FORMATTER, Locale.getDefault()).format(time)
    }

    private fun showDatePicker() {
        val calendarInstance = Calendar.getInstance()
        val day = calendarInstance.get(Calendar.DAY_OF_MONTH)
        val month = calendarInstance.get(Calendar.MONTH)
        val year = calendarInstance.get(Calendar.YEAR)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, yearSel, monthOfYear, dayOfMonth ->
                calendarInstance.set(yearSel, monthOfYear, dayOfMonth, 0, 0)
                if (isRunning) {
                    countDownTimer?.cancel()
                    isRunning = false
                }
                setSelectedDateView(formatDate(calendarInstance.timeInMillis), calendarInstance.timeInMillis)

            }, year, month, day
        )
        dpd.datePicker.minDate = System.currentTimeMillis() - 1000
        dpd.show()
    }


}
