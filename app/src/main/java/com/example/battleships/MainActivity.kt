package com.example.battleships

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.*
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun toastMe(view: View) {
        // val myToast = Toast.makeText(this, message, duration);
        val myToast =
            Toast.makeText(this, "Попов Сергей Витальевич, ЮУрГУ ЕТ-412", Toast.LENGTH_SHORT)
        myToast.show()
    }
    fun exitAction(view:View){
        finish()
        exitProcess(0)
    }
    fun playGame(view:View){
        val gameIntent = Intent(this, Game::class.java)
        startActivity(gameIntent)
    }

}
