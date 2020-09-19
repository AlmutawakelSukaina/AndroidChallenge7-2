package com.example.androidquestion2third


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var trueButton:Button
    private lateinit var falseButton: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        trueButton=findViewById(R.id.true_button)
        falseButton=findViewById(R.id.false_button)

        trueButton.setOnClickListener{
         //val toast=Toast.makeText( applicationContext,R.string.correct_toast, Toast.LENGTH_SHORT).show()



            val toast = Toast.makeText(applicationContext, R.string.correct_toast,  Toast.LENGTH_SHORT)
            toast.show()

            toast.setGravity(Gravity.TOP , 0, 0)





        }
        falseButton.setOnClickListener{


            val toast = Toast.makeText(applicationContext,R.string.incorrect_toast,Toast.LENGTH_SHORT)
            toast.show()

            toast.setGravity(Gravity.TOP , 0, 0)

        }


    }
}


