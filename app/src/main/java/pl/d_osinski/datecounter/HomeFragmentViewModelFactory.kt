package pl.d_osinski.datecounter

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class HomeFragmentViewModelFactory (
    private val application: Application, private val lifecycle: Lifecycle) : ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(application, lifecycle) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}