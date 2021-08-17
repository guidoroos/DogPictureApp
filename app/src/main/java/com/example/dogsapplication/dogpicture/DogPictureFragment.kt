package com.example.dogsapplication.dogpicture

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dogsapplication.R
import com.example.dogsapplication.databinding.FragmentDogPictureBinding
import org.koin.androidx.viewmodel.ext.android.viewModel


/**
 * A simple [Fragment] subclass.
 * Use the [DogPictureFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DogPictureFragment : Fragment() {

    private val args: DogPictureFragmentArgs by navArgs()
    private lateinit var binding: FragmentDogPictureBinding
    private val dogViewModel: DogViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_dog_picture, container, false)
        binding.dogViewModel = dogViewModel

        //use viewmodel to fetch new dog object and save it to the database
        binding.buttonNewPicture.setOnClickListener {
            dogViewModel.getNewDog()
        }

        //navigate to history fragment
        binding.buttonToHistory.setOnClickListener {
            val action = DogPictureFragmentDirections.actionDogPictureFragmentToDogHistoryFragment()
            requireView().findNavController().navigate(action)
        }

        //when dog object fetched, download picture from url
        dogViewModel.currentDog.observe(viewLifecycleOwner, {
            setDogImageFromUrl(it.url)
        })

        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //when fragment is navigated to with a dog as argument(from dog history fragment)
        if (args.dogId != -1L) {
            dogViewModel.updateCurrentDog(args.dogId)

        } else {
            dogViewModel.getNewDog()
        }

    }

    /**
     * Set dog image from url using glide
     *
     * @param imgUrl
     */
    private fun setDogImageFromUrl(imgUrl: String) {
        imgUrl.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(binding.imageView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.ic_baseline_photo)
                        .error(R.drawable.ic_baseline_broken_image)
                )
                .into(binding.imageView)
        }
    }
}