package com.example.dogsapplication.doghistory

import android.app.AlertDialog
import android.app.NotificationManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.dogsapplication.R
import com.example.dogsapplication.database.Dog
import com.example.dogsapplication.databinding.FragmentDogHistoryBinding
import com.example.dogsapplication.notifications.createChannel
import com.example.dogsapplication.notifications.sendNotification
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 * Use the [DogHistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DogHistoryFragment : Fragment() {

    private lateinit var binding: FragmentDogHistoryBinding
    private val viewModel: DogHistoryViewModel by viewModel()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * create adapter for recyclerview and set behavior for clicklistener and longclicklistener for all items
         */
        val adapter = DogAdapter(DogListener { dog ->
            val dogId = dog.dogId
            val action = DogHistoryFragmentDirections.actionDogHistoryFragmentToDogPictureFragment()
                .setDogId(dogId)
            requireView().findNavController().navigate(action)

        }, DogLongClickListener { dog ->
            showDeleteAlertDialog(dog)
            true
        })

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dog_history, container, false)

        binding.dogHistoryViewModel = viewModel
        binding.recyclerView.adapter = adapter

        viewModel.dogList.observe(viewLifecycleOwner, {
            it?.let {
                adapter.submitList(it)
            }
        })
        return binding.root
    }

    /**
     * Show delete alert dialog to confirm deleting picture
     *
     * @param dog
     */
    private fun showDeleteAlertDialog(dog: Dog) {
        val myAlertBuilder = AlertDialog.Builder(this.requireContext())

        myAlertBuilder.apply {

            setMessage(getString(R.string.delete_picture_message))
            setPositiveButton(getString(R.string.delete)
            ) { _, _ ->
                viewModel.deleteDog(dog)
            }
            setNegativeButton(getString(R.string.cancel)
            ) { _, _ ->
            }
        }
        myAlertBuilder.show()
    }

}