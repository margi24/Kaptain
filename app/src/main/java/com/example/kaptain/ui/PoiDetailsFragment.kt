package com.example.kaptain.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.*
import android.view.ViewGroup
import android.widget.*
import androidx.constraintlayout.widget.Group
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kaptain.EmptyResultException
import com.example.kaptain.KaptainException
import com.example.kaptain.R
import com.example.kaptain.TAG
import com.example.kaptain.data.Resource
import com.example.kaptain.data.ReviewSummary
import com.example.kaptain.viewModel.PoiViewModel

class PoiDetailsFragment : Fragment() {

    private lateinit var nameTextView: TextView
    private lateinit var typeTextView: TextView
    private lateinit var ratingTextView: RatingBar
    private lateinit var numReviewsTextView: TextView
    private lateinit var reviewsButton: Button
    private lateinit var groupDetails: Group
    private lateinit var progressBar: ProgressBar
    private lateinit var emptyTextView: TextView

    private val args: PoiDetailsFragmentArgs by navArgs()
    private val viewModel: PoiViewModel by viewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(TAG, "onAttach() called")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate() called")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(TAG, "onCreateView: called")
        return inflater.inflate(R.layout.poi_details_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d(TAG, "onViewCreated: called")

        view.apply {
            nameTextView = findViewById(R.id.poi_name_view)
            typeTextView = findViewById(R.id.poi_type_view)
            ratingTextView = findViewById(R.id.poi_rating_view)
            numReviewsTextView = findViewById(R.id.poi_num_reviews_view)
            reviewsButton = findViewById(R.id.poi_view_reviews_button)
            groupDetails = findViewById(R.id.poi_details_group)
            progressBar = findViewById(R.id.poi_progress)
            emptyTextView = findViewById(R.id.poi_empty_text)
        }

        val poiId = args.poiId
        val poiName = args.poiName
        val poiType = args.poiType
        viewModel.getPoiSummary(poiId).observe(viewLifecycleOwner, Observer { resource ->
            resource.let {
                when (resource) {
                    is Resource.Error -> {
                        hideProgress()
                        if(resource.type is EmptyResultException) {
                            emptyTextView.visibility = VISIBLE
                        } else {
                            Toast.makeText(requireContext(), "Error", Toast.LENGTH_SHORT).show()
                        }
                    }
                    is Resource.Loading -> {
                        showProgress()
                    }
                    is Resource.Success -> {
                        hideProgress()
                        resource.data.let { reviewSummary ->
                            reviewSummary?.let {
                                emptyTextView.visibility = GONE
                                groupDetails.visibility = VISIBLE
                                nameTextView.text = poiName
                                typeTextView.text = poiType
                                ratingTextView.rating = reviewSummary.averageRating.toFloat()
                                numReviewsTextView.text =
                                    getString(
                                        R.string.label_num_reviews,
                                        reviewSummary.numberOfReviews
                                    )
                                reviewsButton.isEnabled = reviewSummary.numberOfReviews > 0
                            }
                        }
                    }
                }
            }
        }
        )
        reviewsButton.setOnClickListener {
            findNavController().navigate(
                PoiDetailsFragmentDirections.actionPoiDetailsFragmentToReviewListFragment(
                    args.poiId
                )
            )
        }
    }

    private fun showProgress() {
        progressBar.visibility = VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = GONE
    }

override fun onStart() {
    super.onStart()
    Log.d(TAG, "onStart() called")
}

override fun onResume() {
    super.onResume()
    Log.d(TAG, "onResume() called")
}

override fun onPause() {
    super.onPause()
    Log.d(TAG, "onPause() called")
}

override fun onStop() {
    super.onStop()
    Log.d(TAG, "onStop() called")
}

override fun onDestroyView() {
    super.onDestroyView()
    Log.d(TAG, "onDestroyView() called")
}

override fun onDestroy() {
    super.onDestroy()
    Log.d(TAG, "onDestroy() called")
}

override fun onDetach() {
    super.onDetach()
    Log.d(TAG, "onDetach() called")
}
}