package com.zlatamigas.animind.controller.recyclerview

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.zlatamigas.animind.AnimeActivity
import com.zlatamigas.animind.R
import java.util.*

class AnimeRVAdapter(
    private val context: Activity,
    private val animeRVModalArrayList: ArrayList<AnimeRVModal>
) :
    RecyclerView.Adapter<AnimeRVAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.anime_rv_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val modal = animeRVModalArrayList[position]

        holder.idTV.text = modal.id.toString()

        holder.ratingTV.text = "${modal.rating}/100"
        holder.titleTV.text = modal.title
        holder.episodesTV.text = "${modal.episodes}  Eps."

        Picasso.get()
            .load(modal.preview.toString())
            .error(R.drawable.ic_app_icon)
            .fit().into(holder.previewIV)
    }

    override fun getItemCount(): Int {
        return animeRVModalArrayList.size
    }

    inner class ViewHolder : RecyclerView.ViewHolder {

        val idTV: TextView = itemView.findViewById(R.id.idTVId)

        val titleTV: TextView = itemView.findViewById(R.id.idTVTitle)
        val ratingTV: TextView = itemView.findViewById(R.id.idTVRating)
        val episodesTV: TextView = itemView.findViewById(R.id.idTVEpisodes)
        val previewIV: ImageView = itemView.findViewById(R.id.idIVPreview)

        constructor(itemView: View) : super(itemView) {
            itemView.setOnClickListener {
                val intent = Intent(context, AnimeActivity::class.java)
                var idNum: Int = -1

                try {
                    idNum = idTV.text.toString().toInt()
                } catch (e: NumberFormatException) {
                    Log.d("Id error!", "Incorrect id: ${e.message}")
                }
                intent.putExtra("idAnime", idNum)

                context.startActivity(intent)
            }
        }
    }
}