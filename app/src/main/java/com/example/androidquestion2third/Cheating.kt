package com.example.androidquestion2third

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val EXTRA_ANSWER_IS_TRUE="com.example.androidquestion2third.answer_is_True"
private const val EXTRA_ANSWER_SHOWN="com.example.androidquestion2third.answer_shown"

class Cheating : AppCompatActivity() {
    private var answerIsTrue=false

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private lateinit var  apiLevel:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheating)
       answerIsTrue=intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE,false)

        answerTextView=findViewById(R.id.answer_TextView)
        showAnswerButton=findViewById(R.id.show_answer_button)
        apiLevel=findViewById(R.id.API);
        apiLevel.setText("API Level  "+Build.VERSION.SDK_INT);

        showAnswerButton.setOnClickListener {

            val answerText=when{
               answerIsTrue->R.string.true_button
                else->R.string.false_button

            }
            answerTextView.setText(answerText)
            setAnswerShownResult(true)
        }




    }

    private fun setAnswerShownResult(isAnswerShown:Boolean)
    {
        val data=Intent().apply { putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown) }
        setResult(Activity.RESULT_OK,data)

    }



    companion object{
        fun newIntent(PackageContext: Context, answerIsTrue:Boolean):Intent
        {
            return Intent(PackageContext,Cheating::class.java).apply {

                putExtra(EXTRA_ANSWER_IS_TRUE,answerIsTrue)

            }
        }



    }
}