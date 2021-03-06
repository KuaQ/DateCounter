package pl.d_osinski.datecounter

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleObserver
import pl.d_osinski.datecounter.databinding.HomeFragmentBinding
import java.util.*


class HomeFragment : Fragment(){

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Get a reference to the binding object and inflate the fragment views.
        val binding: HomeFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.home_fragment, container, false)

        val application = requireNotNull(this.activity).application

        val viewModelFactory = HomeFragmentViewModelFactory(application, this.lifecycle)

        val homeFragmentViewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(HomeViewModel::class.java)

        binding.homeViewModel = homeFragmentViewModel

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel.startTimer()
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
