package com.rajput.aditi.echo.adapters

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.rajput.aditi.echo.R
import com.rajput.aditi.echo.Songs
import com.rajput.aditi.echo.activities.MainActivity
import com.rajput.aditi.echo.fragments.MainScreenFragment
import com.rajput.aditi.echo.fragments.SongPlayingFragment
import kotlinx.android.synthetic.*



class MainScreenAdapter(_songDetails: ArrayList<Songs>, _context: Context)
    : RecyclerView.Adapter<MainScreenAdapter.MyViewHolder>() {

    var songDetails: ArrayList<Songs>? = null
    var mContext: Context? = null

    init {
        this.songDetails = _songDetails
        this.mContext = _context
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val songObject = songDetails?.get(position)
        holder.trackTitle?.text = songObject?.songTitle
        holder.trackArtist?.text = songObject?.artist
        holder.contentHolder?.setOnClickListener({
            val songPlayingFragment = SongPlayingFragment()
            var args = Bundle()
            args.putString("songArtist", songObject?.artist)
            args.putString("path", songObject?.songData)
            args.putString("songTitle", songObject?.songTitle)
            args.putInt("SongId", songObject?.songID?.toInt() as Int)
            args.putInt("songPosition", position)
            args.putParcelableArrayList("songData", songDetails)

            songPlayingFragment.arguments = args       // linking songPlayingFragment to Bundle obj

//            println("----------------------------------------------------going to call isFragmentExists() method")
//            if (isFragmentExists("SongPlayingFragment")) {
//                (mContext as FragmentActivity).supportFragmentManager
//                        .beginTransaction()
//                        .remove(Fragment().getFragmentManager().findFragmentByTag("SongPlayingFragment"))
//                        .replace(R.id.details_fragment, songPlayingFragment, "SongPlayingFragment")
//                        .commit()
//            } else {
//                (mContext as FragmentActivity).supportFragmentManager
//                        .beginTransaction()
//                        .replace(R.id.details_fragment, songPlayingFragment, "SongPlayingFragment")
//                        .commit()
//            }

            if(SongPlayingFragment.Statified.mediaPlayer != null && SongPlayingFragment.Statified.mediaPlayer?.isPlaying as Boolean){
                SongPlayingFragment.Statified.mediaPlayer?.pause()
                SongPlayingFragment.Statified.mediaPlayer?.release()
            }

            (mContext as FragmentActivity).supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.details_fragment, songPlayingFragment, "SongPlayingFragment")
                    .addToBackStack("SongPlayingFragment")
                    .commit()
        })
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent?.context)
                .inflate(R.layout.row_custom_mainscreen_adapter, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        if (songDetails == null)
            return 0

        return (songDetails as ArrayList<Songs>).size
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var trackTitle: TextView? = null
        var trackArtist: TextView? = null
        var contentHolder: RelativeLayout? = null

        init {
            trackTitle = view.findViewById(R.id.trackTitle)
            trackArtist = view.findViewById(R.id.trackArtist)
            contentHolder = view.findViewById<RelativeLayout>(R.id.contentRow)
        }
    }}