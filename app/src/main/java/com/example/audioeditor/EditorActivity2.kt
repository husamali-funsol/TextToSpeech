package com.example.audioeditor

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.audioeditor.databinding.ActivityEditor2Binding
import linc.com.library.AudioTool
import linc.com.library.types.Echo
import java.io.File


class EditorActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityEditor2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditor2Binding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.btnRemoveVocal.setOnClickListener {
            AudioTool.getInstance(this)
                .withAudio(File(Environment.getExternalStorageDirectory().path + "/Download/example_audio_song.mp3"))
                .removeVocal {
                    // Output file - audio without vocal
                    Toast.makeText(this, "remove vocal", Toast.LENGTH_SHORT).show()

                }
//                .applyEchoEffect(Echo.ECHO_OPEN_AIR) {
//                    // Output file - audio file with echo effect and without vocal
//                    Toast.makeText(this, "echo", Toast.LENGTH_SHORT).show()
//
//                }
                .saveCurrentTo("/storage/emulated/0/Download/NewAudio.mp3") // Audio file with echo and without vocal
                /* calls */
                .release()
        }



        binding.btnEcho.setOnClickListener {
            AudioTool.getInstance(this)
                .withAudio(File(Environment.getExternalStorageDirectory().path + "/Download/example_audio_song.mp3"))
                .applyEchoEffect(Echo.ECHO_OPEN_AIR) {
                    // Output file - audio without vocal
                    Toast.makeText(this, "echoed", Toast.LENGTH_SHORT).show()

                }
//                .applyEchoEffect(Echo.ECHO_OPEN_AIR) {
//                    // Output file - audio file with echo effect and without vocal
//                    Toast.makeText(this, "echo", Toast.LENGTH_SHORT).show()
//
//                }
                .saveCurrentTo("/storage/emulated/0/Download/NewAudio.mp3") // Audio file with echo and without vocal
                /* calls */
                .release()
        }

        binding.btnNextActivity.setOnClickListener {
            intent = Intent(this, VisualizerActivity::class.java)
            startActivity(intent)
        }

    }
}