package com.zlatamigas.animind.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import com.zlatamigas.animind.MainActivity
import com.zlatamigas.animind.R
import com.zlatamigas.animind.controller.api.AnimeAPIController
import com.zlatamigas.animind.databinding.FragmentCalendarBinding
import com.zlatamigas.animind.model.Anime
import com.zlatamigas.animind.model.Reminder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*


class CalendarFragment : Fragment() {

    private var _binding: FragmentCalendarBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val dbController = (activity as MainActivity).dbController
        val controller = this.context?.let { it1 -> AnimeAPIController(it1) }

        val reminders = dbController?.getReminders()

        val calendarView = binding.calendarView
        val scrollView = binding.scrollView

        calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->

            val date = Calendar.getInstance()

            date.set(Calendar.YEAR, year)
            date.set(Calendar.MONTH, month)
            date.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            val output = SimpleDateFormat("dd.MM.yyyy")
            val nowDate = output.format(date.time)

            val dateReminders = reminders?.filter { r ->
                val temp = output.format(r.reminderDate)
                temp.equals(nowDate)
            }


            scrollView.removeAllViews()


            if (dateReminders != null) {
                for (reminder in dateReminders) {

                    GlobalScope.launch {

                        var anime: Anime? = null

                        val cm =
                            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
                        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
                        if (isConnected) {
                            anime = controller?.getAnime(reminder.id)
                        } else {
                            anime = Anime(
                                reminder.id,
                                reminder.name,
                                "No description (Require Internet connection)",

                                null,
                                null,
                                null,
                                null,
                                null,
                                "h",
                                arrayListOf(),
                                ""
                            )
                        }
                        withContext(Dispatchers.Main) {
                            if (anime != null) {
                                val linear = LayoutInflater.from(activity).inflate(
                                    R.layout.scroll_view_item,
                                    scrollView,
                                    false
                                )

                                val titleTv = linear.findViewById<TextView>(R.id.idTVTitle)
                                val button = linear.findViewById<ImageView>(R.id.deleteButton)
                                val ratingTv = linear.findViewById<TextView>(R.id.idTVRating)
                                val episodesTv = linear.findViewById<TextView>(R.id.idTVEpisodes)
                                val preview = linear.findViewById<ImageView>(R.id.idIVPreview)

                                Picasso.get()
                                    .load(anime.posterImage)
                                    .error(R.drawable.ic_app_icon).fit().into(preview)

                                var rating = "--"
                                var episodeCount = "--"
                                if (anime.rating != null) {
                                    rating = anime.rating.toString()
                                }
                                if (anime.episodeCount != null) {
                                    episodeCount = anime.episodeCount.toString()
                                }
                                titleTv.text = anime.title
                                ratingTv.text = "$rating/100"
                                episodesTv.text = "$episodeCount Eps."
                                button.setOnClickListener {
                                    if (dbController.deleteReminder(reminder.rowId) == 1) {
                                        (reminders as ArrayList<Reminder>).remove(reminder)
                                    }
                                }
                                scrollView.addView(linear)
                            }
                        }
                    }
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}