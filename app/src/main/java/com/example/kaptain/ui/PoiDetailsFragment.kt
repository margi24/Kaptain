package com.example.kaptain.ui

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.kaptain.R
import com.example.kaptain.TAG
import com.example.kaptain.data.poiList

class PoiDetailsFragment : Fragment() {

    private val args: PoiDetailsFragmentArgs by navArgs()

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

        val poiId = args.poiId
        val poi = poiList.find { it.id == poiId }
        poi?.let {
            view.apply {
                findViewById<TextView>(R.id.poi_name_view).text = poi.name
                findViewById<TextView>(R.id.poi_type_view).text = poi.poiType
                findViewById<RatingBar>(R.id.poi_rating_view).rating =
                        poi.reviewSummary.averageRating.toFloat()
                findViewById<TextView>(R.id.poi_num_reviews_view).text =
                        getString(R.string.label_num_reviews, poi.reviewSummary.numberOfReviews)
                findViewById<Button>(R.id.poi_view_reviews_button).isEnabled =
                        poi.reviewSummary.numberOfReviews > 0
                findViewById<Button>(R.id.poi_view_reviews_button).setOnClickListener {
                    findNavController().navigate(
                            PoiDetailsFragmentDirections.actionPoiDetailsFragmentToReviewListFragment(poiId)
                    )
                }
            }
        }
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