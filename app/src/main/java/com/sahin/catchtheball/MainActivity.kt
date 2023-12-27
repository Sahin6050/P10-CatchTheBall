package com.sahin.catchtheball

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.sahin.catchtheball.databinding.ActivityMainBinding
import kotlin.random.Random


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    var Score = 0
    var ImageArray = ArrayList<ImageView>()
    var runnable : Runnable = Runnable {}
    var handler : Handler = Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Data -> Verilerin eklenmesi Diziye ekleme nedeni görsellerin daha rahat kullanılması.
        ImageArray.add(binding.imageView)
        ImageArray.add(binding.imageView2)
        ImageArray.add(binding.imageView3)
        ImageArray.add(binding.imageView4)
        ImageArray.add(binding.imageView5)
        ImageArray.add(binding.imageView6)
        ImageArray.add(binding.imageView7)
        ImageArray.add(binding.imageView8)
        ImageArray.add(binding.imageView9)

        // Hide Images -> Görsellerin belli aralıkla saklanıp görüntülenmesi.
        hideImages()


        // Sürenin geriye doğru akması için kullanılan CountDown Timer.
        object : CountDownTimer(15700,1000){
            override fun onTick(millisUntilFinished: Long) {
                binding.textView.text = "Time: ${millisUntilFinished / 1000}"

            }

            override fun onFinish() {
                binding.textView.text = "Time: 0"
                handler.removeCallbacks(runnable)
                for ( i in ImageArray){
                    i.visibility = View.INVISIBLE
                }

                // Oyun bittiğinde uyarı mesajının verilmesi.
                val alert = AlertDialog.Builder(this@MainActivity)
                alert.setTitle("Game Over")
                alert.setMessage("Restart the game ? ")
                alert.setPositiveButton("Yes",object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        // Kendine intent yani uygulamayı baştan başlatmak.
                        val intent = Intent(this@MainActivity,MainActivity::class.java)
                        finish()
                        startActivity(intent)

                    }

                })
                alert.setNegativeButton("No",object :DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        Toast.makeText(this@MainActivity,"Game Over",Toast.LENGTH_LONG).show()
                    }

                })
                alert.show()
            }

        }.start()


    }
    // Skoru artırmak için oluşturulan fonksiyon.
    fun IncreaseScore(view : View){
        Score = Score + 1
        binding.textView2.text = "Score: ${Score}"
    }
    // Görselleri gizlemek için oluşturulan fonksiyon.
    fun hideImages(){
        runnable = object  : Runnable{
            override fun run() {
                for ( i in ImageArray){
                    i.visibility = View.INVISIBLE
                }
                val randomNum = Random.nextInt(9)
                ImageArray[randomNum].visibility = View.VISIBLE
                handler.postDelayed(runnable,600)
            }

        }
        handler.post(runnable)


    }
}