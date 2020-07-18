package com.e.tapthegrey

import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    private val totalTime = 60*60*1000L
    private var currentPosition = -1
    private lateinit var cdt: CountDownTimer
    private var score = 0
    private var random = 0
    private var isTapped = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeImage()
        enableClick(false)

    }

    fun start(v: View){
        score = 0
        isTapped = true
        textView.text = "Score: ${score}"
        enableClick(true)
        cdt = object : CountDownTimer(totalTime, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                if (isTapped) {
                    currentPosition = getRandomInt()
                    changeImage()
                    isTapped = false
                    Log.d("MainActivity", "onTick + " + currentPosition);
                }else{
                    gameOver()
                }
            }

            override fun onFinish() {
                cdt.start()
            }
        }
        cdt.start()
    }

    private fun enableClick(enable: Boolean) {
        img_btn_1_1.isClickable = enable
        img_btn_1_2.isClickable = enable
        img_btn_2_1.isClickable = enable
        img_btn_2_2.isClickable = enable
        button.isEnabled = !enable
    }

    private fun changeImage() {

        img_btn_1_1.setBackgroundResource(R.color.color1_1)
        img_btn_1_2.setBackgroundResource(R.color.color1_2)
        img_btn_2_1.setBackgroundResource(R.color.color2_1)
        img_btn_2_2.setBackgroundResource(R.color.color2_2)

        when(currentPosition){
            0 -> img_btn_1_1.setBackgroundResource(R.color.grey)
            1 -> img_btn_1_2.setBackgroundResource(R.color.grey)
            2 -> img_btn_2_1.setBackgroundResource(R.color.grey)
            3 -> img_btn_2_2.setBackgroundResource(R.color.grey)
        }
    }

    private fun getRandomInt(): Int {
        val min = 0
        val max = 3

        do {
            random = Random().nextInt(max - min + 1) + min
        }while (random == currentPosition)

        return random;
    }

    fun image11Clicked(v:View){
        handleClick(0)
    }

    fun image12Clicked(v:View){
        handleClick(1)
    }

    fun image21Clicked(v:View){
        handleClick(2)
    }

    fun image22Clicked(v:View){
        handleClick(3)
    }

    private fun handleClick(pos: Int) {
        isTapped = true
        if (currentPosition == pos){
            score++
            textView.text = "Score: ${score}"
        }else{
            gameOver()
        }
    }

    private fun gameOver() {
        cdt.cancel()
        enableClick(false)
        Toast.makeText(this, "GAME OVER", Toast.LENGTH_LONG).show()
    }

    override fun onStop() {
        super.onStop()
        cdt.cancel()
    }
}