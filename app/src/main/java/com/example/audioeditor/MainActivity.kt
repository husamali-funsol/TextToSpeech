package com.example.audioeditor

import android.content.Intent
import android.media.MediaRecorder
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.audioeditor.databinding.ActivityMainBinding
import java.io.File
import java.io.IOException
import java.util.Locale

class MainActivity : AppCompatActivity(), TextToSpeech.OnInitListener{

    private lateinit var binding: ActivityMainBinding

    private lateinit var textToSpeech: TextToSpeech

    private lateinit var mediaRecorder: MediaRecorder


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSpeak.isEnabled = false

        textToSpeech = TextToSpeech(this,this)
        mediaRecorder = MediaRecorder()



        binding.btnSpeak.setOnClickListener {
            speak()

            // Delay recording to make sure TTS starts before starting the recorder
            binding.root.postDelayed({
                startRecording()
            }, 1000)
        }

        binding.btnActivity.setOnClickListener {
            intent = Intent(this, EditorActivity2::class.java)
            startActivity(intent)
        }


    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun speak() {
        val text = binding.etText.text.toString()
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH,null,"")

        // Delay recording to make sure TTS starts before starting the recorder
        binding.root.postDelayed({
            startRecording()
        }, 1000)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun startRecording() {
//        val outputFile = "${externalCacheDir?.absolutePath}/output_audio.mp3"

        val musicDir = getExternalFilesDir(android.os.Environment.DIRECTORY_MUSIC)
        val outputFile = File(musicDir, "my_recorded_audio.mp3")

        mediaRecorder.apply {
            reset()
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile)

            try {
                prepare()
                start()
                Toast.makeText(this@MainActivity, "recorder started", Toast.LENGTH_SHORT).show()
            } catch (e: IOException) {
                Log.e("MediaRecorder", "prepare() failed")
            }
        }
    }

    private fun stopRecording() {
        try {
            mediaRecorder.stop()
        } catch (e: RuntimeException) {
            Log.e("MediaRecorder", "stop() failed")
        } finally {
            mediaRecorder.release()
        }
    }

    override fun onInit(status: Int) {
        if(status == TextToSpeech.SUCCESS){
            val language = textToSpeech.setLanguage(Locale.ENGLISH)

            if((language == TextToSpeech.LANG_MISSING_DATA) || (language == TextToSpeech.LANG_NOT_SUPPORTED)){
                Log.e("TexttoSpeech" , "Language not supported")
            }
            else{
                binding.btnSpeak.isEnabled = true
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        textToSpeech.shutdown()
        stopRecording()
    }
}