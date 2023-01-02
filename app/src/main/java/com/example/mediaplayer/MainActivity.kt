package com.example.mediaplayer

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.SeekBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null

    private lateinit var btnPlay:FloatingActionButton

    private lateinit var btnPause:FloatingActionButton

    private lateinit var btnRewind:FloatingActionButton

    private lateinit var btnForward:FloatingActionButton

    private lateinit var seekBar: SeekBar

    private lateinit var tvCurrentTime:TextView

    private lateinit var tvTotalTime:TextView

    var totalDuration:Int = 0

    private lateinit var runnable: Runnable

    private lateinit var handler: Handler




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        handler = Handler(Looper.getMainLooper())

//      Method for find ids...
        findId()


//      Listening for Play Button
        playButtonListener()

//      Listening for Pause Button
        pauseButtonListener()


//      Listening the Seek Bar
        seekBarListener()
    }

    private fun seekBarListener() {

        seekBar.setOnSeekBarChangeListener(object :SeekBar.OnSeekBarChangeListener{
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, isChanged: Boolean) {
                if (mediaPlayer != null && isChanged){
                        mediaPlayer!!.seekTo(progress)
                        updateCurrentTime()
                    }
                }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
    }



    private fun pauseButtonListener() {

        btnPause.setOnClickListener{
            mediaPlayer?.pause()
            changeVisibility(btnPause,btnPlay)

        }
    }

    private fun changeVisibility(invisible: FloatingActionButton, visible: FloatingActionButton) {
        invisible.visibility = View.INVISIBLE
        visible.visibility = View.VISIBLE
    }


    private fun playButtonListener() {

        btnPlay.setOnClickListener{
            if (mediaPlayer == null){
                mediaPlayer = MediaPlayer.create(this,R.raw.music)
                setFinalTime()
                initalizeSeekBar()
            }

            seekBar.progress = mediaPlayer!!.currentPosition;
            mediaPlayer!!.start()
            changeVisibility(btnPlay,btnPause)
        }
    }



    private fun initalizeSeekBar() {

        runnable = Runnable {
            seekBar.progress = mediaPlayer!!.currentPosition
            handler.postDelayed(runnable,1000)
            updateCurrentTime()
        }
        handler.postDelayed(runnable,1000)

    }



    private fun updateCurrentTime() {

        var currentPosition = mediaPlayer!!.currentPosition
        val min = currentPosition / 1000 / 60
        val sec = currentPosition / 1000 % 60
        if(sec<10){
            tvCurrentTime.text = "$min:0$sec"
        }else{
            tvCurrentTime.text = "$min:$sec"
        }

    }




    private fun setFinalTime() {

        totalDuration = mediaPlayer!!.duration
        val minutes = totalDuration / 1000 / 60
        val seconds = totalDuration / 1000 % 60
        tvTotalTime.text = "$minutes:$seconds"
        seekBar.max = totalDuration

    }



    private fun findId() {

        btnPlay = findViewById(R.id.btnPlay)
        btnPause = findViewById(R.id.btnPause)
        btnRewind = findViewById(R.id.btnRewind)
        btnForward = findViewById(R.id.btnForward)
        seekBar = findViewById(R.id.sbPlaying)
        tvCurrentTime = findViewById(R.id.tvCurrentTime)
        tvTotalTime = findViewById(R.id.tvTotalTime)
    }


}