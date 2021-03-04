package com.example.kaptain.ui

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kaptain.R
import com.example.kaptain.data.Review
import com.example.kaptain.viewModel.ReviewViewModel

class ReviewListFragment : Fragment(R.layout.reviews_list_fragment) {

    private val args: ReviewListFragmentArgs by navArgs()

    inner class ReviewItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val reviewerNameView = itemView.findViewById<TextView>(R.id.reviewer_name)
        private val reviewTitleView = itemView.findViewById<TextView>(R.id.review_title)
        private val reviewerTextView = itemView.findViewById<TextView>(R.id.review_text)
        private val reviewerRatingView = itemView.findViewById<RatingBar>(R.id.review_rating_view)
        private val datePostedView = itemView.findViewById<TextView>(R.id.review_date_posted)

        fun bind(review: Review) {
            review.let {
                reviewerNameView.text = it.reviewerName
                reviewTitleView.text = it.reviewTitle
                reviewerTextView.text = it.reviewText
                reviewerRatingView.rating = it.rating.toFloat()
                datePostedView.text = it.dateAdded
            }
        }
    }

    inner class ReviewAdapter : RecyclerView.Adapter<ReviewItemViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewItemViewHolder {
            return ReviewItemViewHolder(
                    layoutInflater.inflate(R.layout.review_list_item, parent, false)
            )
        }

        override fun getItemCount(): Int = reviews.size

        override fun onBindViewHolder(holder: ReviewItemViewHolder, position: Int) {
            reviews.getOrNull(position)?.let {
                holder.bind(it)
            }
        }
    }

    private var reviews= listOf<Review>()
    private val viewModel: ReviewViewModel by viewModels()
    private val adapter =  ReviewAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<RecyclerView>(R.id.review_list).apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@ReviewListFragment.adapter
        }

        viewModel.getReviewsForPoi(args.poiId).observe(viewLifecycleOwner, Observer {
            reviews = it
            adapter.notifyDataSetChanged()
        })
    }
}