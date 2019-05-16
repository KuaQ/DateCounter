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


class AddItemActivity : AppCompatActivity() {

    companion object {
        const val DATE_FORMATTER = "dd.MM.yyyy"
    }
    lateinit var countDownTimer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)
        button.setOnClickListener {
            showDatePicker()
        }

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
                calendarInstance.set(dayOfMonth, monthOfYear, yearSel)
                formatDate(calendarInstance.timeInMillis)
            }, year, month, day
        )
        dpd.show()
    }


}
