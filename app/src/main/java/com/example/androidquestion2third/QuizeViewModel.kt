package com.example.androidquestion2third

import androidx.lifecycle.ViewModel

class QuizViewModel:ViewModel()
{
    var currentIndex =0
    private var numAnswer=0
    var isCheater=false
    var grade=0
    var text="Your Current Score = "
    var remainText=" Remain Token : "
    var token=3


    private val easyQuestion= listOf(
        Question(R.string.questiom_E_Yemne, false,"easy"),
        Question(R.string.question_E_Cairo, false,"easy"),
        Question(R.string.question_E_Europe, true,"easy"),
        Question(R.string.question_E_Istanbul, true,"easy"),
        Question(R.string.question_E_Japan, false,"easy"),
        Question(R.string.question_E_Turkey, false,"easy")

    )
    private val mediumQuestion= listOf(
        Question(R.string.question_M_seas, true,"Medium"),
        Question(R.string.question_M_city, true,"Medium"),
        Question(R.string.question_M_island, false,"Medium"),
        Question(R.string.question_M_mountain, false,"Medium"),
        Question(R.string.question_M_reserve, false,"Medium"),
        Question(R.string.question_M_waterFall, true,"Medium"))

    private val difficultQuestion= listOf( Question(R.string.question_australia, true,"easy"),
        Question(R.string.question_oceans, true,"difficult"),
        Question(R.string.question_mideast, false,"difficult"),
        Question(R.string.question_africa, false,"difficult"),
        Question(R.string.question_americas, true,"difficult"),
        Question(R.string.question_asia, true,"difficult"))

    private val questionBank = listOf(
        easyQuestion.random(),easyQuestion.random(),mediumQuestion.random(),mediumQuestion.random()
        ,difficultQuestion.random(),difficultQuestion.random()

    )
    private val answerBank= listOf(CheckAnsweredQuestions(0,false),
        CheckAnsweredQuestions(1,false),
        CheckAnsweredQuestions(2,false),
        CheckAnsweredQuestions(3,false),
        CheckAnsweredQuestions(4,false),
        CheckAnsweredQuestions(5,false)

    )


    val currentQuestionAnswer:Boolean
    get()=questionBank[currentIndex].answer

    val currentQuestionText:Int
    get()=questionBank[currentIndex].textResId

    val size:Int
    get()=questionBank.size

    val numberOfAnsweredQuestions
    get()=numAnswer

    val isAnsweredQuestion:Boolean
    get()=answerBank[currentIndex].answered

    val kindOfQuestions:String
    get()=questionBank[currentIndex].kind

    val getGrade:String
    get()=grade.toString()

    val getToken:Int
    get()=token

    val getRemainTextView
    get()=remainText


    fun moveToNext()
    {
        currentIndex = (currentIndex + 1) % questionBank.size

    }

   fun moveToPrevious()
   {
       currentIndex = (currentIndex - 1) % questionBank.size
       if(currentIndex<0)
           currentIndex=questionBank.size-1

   }
   fun userEnterAnswer()
   {
       numAnswer++
       answerBank[currentIndex].answered=true
       if(isCheater)
           token-=1


   }






}