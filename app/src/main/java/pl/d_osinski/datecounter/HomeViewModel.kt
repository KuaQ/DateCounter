package pl.d_osinski.datecounter

import android.app.Application
import androidx.lifecycle.*

class HomeViewModel(application: Application) : AndroidViewModel(application){

    private val dateText = MutableLiveData<String>()

    private val clickCount = MutableLiveData<Int>().apply {
        postValue(0)
    }
    init {
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
}
