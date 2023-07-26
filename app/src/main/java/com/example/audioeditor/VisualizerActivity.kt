package com.example.audioeditor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.audioeditor.databinding.ActivityVisualizerBinding
import java.io.File

class VisualizerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVisualizerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVisualizerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.waveformSeekBar.setSampleFrom(File("/storage/emulated/0/Download/example_audio_song.mp3"))

    }
}