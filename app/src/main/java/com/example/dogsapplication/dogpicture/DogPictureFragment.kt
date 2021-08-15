package com.example.dogsapplication.dogpicture

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dogsapplication.R
import com.example.dogsapplication.databinding.FragmentDogPictureBinding
import com.example.dogsapplication.doghistory.DogHistoryFragmentDirections
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 * Use the [DogPictureFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DogPictureFragment : Fragment() {

    private val args:DogPictureFragmentArgs by navArgs()
    private lateinit var binding:FragmentDogPictureBinding
    private val dogViewModel: DogViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_dog_picture, container, false)


        binding.dogViewModel = dogViewModel

        binding.buttonNewPicture.setOnClickListener {
            dogViewModel.getNewDog()
        }

        binding.buttonToHistory.setOnClickListener {
            val action = DogPictureFragmentDirections.actionDogPictureFragmentToDogHistoryFragment()
            requireView().findNavController().navigate(action)
        }

        dogViewModel.currentDog.observe(viewLifecycleOwner, {
            setDogImageFromUrl(it.url)
        })



        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (args.dogId != -1L) {
            dogViewModel.updateCurrentDog(args.dogId)

        }

        else {
            dogViewModel.getNewDog ()
        }

    }

    fun setDogImageFromUrl(imgUrl: String) {
        imgUrl.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(binding.imageView.context)
                .load(imgUri)
                //between load and into: add placeholder and error state image/animation
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_baseline_photo)
                        .error(R.drawable.ic_baseline_broken_image)
                )
                .into(binding.imageView)
        }
    }



}