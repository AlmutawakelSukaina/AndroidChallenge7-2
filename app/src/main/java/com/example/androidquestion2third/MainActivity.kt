package com.example.androidquestion2third


import android.annotation.SuppressLint
import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

private const val REQUEST_CODE_CHEAT=0
private val TAG = "MainActivity"
private const val EXTRA_ANSWER_SHOWN="com.example.androidquestion2third.answer_shown"
class MainActivity : AppCompatActivity() {
    private lateinit var trueButton: Button
    private  lateinit var falseButton: Button
    private lateinit var nextButton: ImageButton
    private lateinit var previousButton: ImageButton
    private lateinit var questionTextView: TextView
    private lateinit var cheatButton:Button
    private lateinit var resultTextView:TextView
    private lateinit var cheatToken:TextView


    private val quizViewModel:QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }



    @RequiresApi(Build.VERSION_CODES.M)
    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button)
        previousButton=findViewById(R.id.previous_button)
        questionTextView = findViewById(R.id.question_text_view)
        cheatButton=findViewById(R.id.cheat_button)
        resultTextView=findViewById(R.id.result)
        cheatToken=findViewById(R.id.cheat_token)

        trueButton.setOnClickListener {

           quizViewModel.userEnterAnswer()
            checkAnswer(true)
            isAnswered()
//            tokenCheck()



        }
        falseButton.setOnClickListener {
           quizViewModel.userEnterAnswer()
           checkAnswer(false)
            isAnswered()
//            tokenCheck()


        }
        val questionTextResId = quizViewModel.currentQuestionText

        questionTextView.setText(questionTextResId)
        resultTextView.setText(quizViewModel.getGrade)
        trueButton.isEnabled=!quizViewModel.isAnsweredQuestion
        falseButton.isEnabled=!quizViewModel.isAnsweredQuestion
        cheatButton.isEnabled=!quizViewModel.isCheater
        resultTextView.setText(quizViewModel.text+quizViewModel.getGrade)
        cheatToken.setText(quizViewModel.getRemainTextView+quizViewModel.getToken)


        nextButton.setOnClickListener {

            quizViewModel.moveToNext()
            updateQuestion()
            isAnswered()

        }

        previousButton.setOnClickListener {

           quizViewModel.moveToPrevious()
            updateQuestion()
            isAnswered()

        }

     cheatButton.setOnClickListener {view ->
         val answerIsTrue=quizViewModel.currentQuestionAnswer
         val intent=Cheating.newIntent(this,answerIsTrue)
         if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
             val options = ActivityOptions.makeClipRevealAnimation(
                 view, 0, 0,
                 view.width, view.height
             )
             startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
         }
         else
           {
             startActivityForResult(intent, REQUEST_CODE_CHEAT)
           }
         onActivityResult(REQUEST_CODE_CHEAT,Activity.RESULT_OK,intent)


       



     }


    }


    private fun checkAnswer(userAnswer: Boolean) {


        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId =when{
            quizViewModel.isCheater->R.string.judgment_toast
            userAnswer == correctAnswer->R.string.correct_toast
            else->R.string.incorrect_toast


        }

                var add=isCheater(messageResId)

                quizViewModel.grade+=add



            resultTextView.setText(quizViewModel.text+quizViewModel.getGrade)
            Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
             quizViewModel.isCheater=false
          if(quizViewModel.numberOfAnsweredQuestions==quizViewModel.size) {

              var scorePercent =Math.round(((quizViewModel.getGrade.toFloat() / quizViewModel.size.toFloat()) * 100.0f)/4.0f);

              val toast= Toast.makeText(this, "Your Final Score = ${quizViewModel.getGrade}"+"\n"+scorePercent+"%", Toast.LENGTH_LONG)
              toast.setGravity(Gravity.CENTER,0,0)
              toast.show()

          }

      }

     private fun isAnswered()
    {
             trueButton.isEnabled=!quizViewModel.isAnsweredQuestion
            falseButton.isEnabled=!quizViewModel.isAnsweredQuestion



    }

    private	fun	updateQuestion()
    {
        val	questionTextResId	=	quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }



     fun isCheater(message:Int):Int
     {
         var res=0
         if(message!=R.string.judgment_toast&&message!=R.string.incorrect_toast)
         { if(!quizViewModel.isCheater)
                res = when{

                 quizViewModel.kindOfQuestions=="easy"->2
                 quizViewModel.kindOfQuestions=="Medium"->4
                 else ->6


             }

         }
         return res

     }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode!=Activity.RESULT_OK){return}
        if(requestCode== REQUEST_CODE_CHEAT)
        {
            quizViewModel.isCheater=data?.getBooleanExtra(EXTRA_ANSWER_SHOWN,false)?:false
            if(quizViewModel.getToken<=1&&quizViewModel.isCheater)
           {
               quizViewModel.token-=1
           cheatButton.isEnabled=!quizViewModel.isCheater

           }
            else if( quizViewModel.isCheater&&quizViewModel.getToken>=1)
             quizViewModel.token-=1
            cheatToken.setText(quizViewModel.getRemainTextView+quizViewModel.getToken)


        }


    }




    override fun onStart() {
        super.onStart()
        Log.d(TAG ,"onStart() called "  + quizViewModel.currentIndex)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG ,"onPause() called "  +quizViewModel.currentIndex)
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG ,"onRestart() called " + quizViewModel.currentIndex )
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG ,"onResume() called " + quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG ,"onStop() called "  + quizViewModel.currentIndex )
    }


    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG ,"onDestroy() called" )
    }







          }




