package pl.d_osinski.datecounter

import android.app.Application
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.*
import java.util.*
import java.util.concurrent.TimeUnit

class HomeViewModel(application: Application, lifecycle: Lifecycle) : AndroidViewModel(application), LifecycleObserver {

    private var countDownTimer: CountDownTimer? = null
    private val dateText = MutableLiveData<String>()

    private val dateToCountTimeStamp: Long = getDateTimeStamp()

    private val clickCount = MutableLiveData<Int>().apply {
        postValue(0)
    }

    private fun getDateTimeStamp() :Long{
        val calendar = Calendar.getInstance()
        calendar.set(2020, 1, 28, 0, 0)
        return calendar.timeInMillis
    }

    private val timeStampLong = MutableLiveData<Long>()

    val timeLeftString = MutableLiveData<String>().apply {
        postValue("start?")
    }


    init {
        lifecycle.addObserver(this)
        initializeDate()
    }

    private fun initializeDate() {
        dateText.value = "czesc "
    }

    val dateString: LiveData<String> = Transformations.map(dateText){
        dateText.value
    }

    fun incraseClicks(){
        clickCount.value = clickCount.value?.plus(1)
        dateText.value = "czesc po raz ${clickCount.value}"
    }


    fun startTimer(){
        Log.d("onStart", Calendar.getInstance().timeInMillis.toString())
        timeStampLong.value = dateToCountTimeStamp - Calendar.getInstance().timeInMillis
        calculateTime()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun stopTimer(){
        timeStampLong.value = dateToCountTimeStamp - Calendar.getInstance().timeInMillis
        countDownTimer?.cancel()
        Log.d("onStop", Calendar.getInstance().timeInMillis.toString())
    }

    private fun calculateTime(){

        timeStampLong.value = dateToCountTimeStamp - Calendar.getInstance().timeInMillis
        countDownTimer = object : CountDownTimer(timeStampLong.value!!, 1000) {
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
                if (days > 0) textform = "$days days, "
                textform += timeFormatter(hours) + ":" + timeFormatter(minutes) + ":" + timeFormatter(seconds)
                timeLeftString.value = textform
            }
            override fun onFinish() {
                timeLeftString.value = "finished"
            }

        }.start()
    }
    private fun timeFormatter(timeText: Long): String {
        return if (timeText < 10) {
            "0$timeText"
        } else {
            timeText.toString()
        }
    }

}
