package byrd.syrch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.Result
import kotlin.collections.ArrayList

class MainActivity2 : AppCompatActivity() {

    lateinit var questionsList:ArrayList<Questions>
    private var index:Int = 0
    lateinit var Questions : Questions
    private var correctAnswerCount:Int=0
    private var wrongAnswerCount:Int=0
    lateinit var countDown: TextView
    lateinit var questions: TextView
    lateinit var option1: Button
    lateinit var option2: Button
    lateinit var option3: Button
    lateinit var option4: Button
    private var backPressedTime: Long = 0
    private var backToast: Toast? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        supportActionBar?.hide()

        countDown=findViewById(R.id.countdown)
        questions=findViewById(R.id.questions)
        option1=findViewById(R.id.option1)
        option2=findViewById(R.id.option2)
        option3=findViewById(R.id.option3)
        option4=findViewById(R.id.option4)

        questionsList= ArrayList()
        questionsList.add(Questions("What is the worlds smallest bird?","A Hawk","A Humming Bird","A Dove"," A Parrot","A Humming Bird"))
        questionsList.add(Questions("What is the worlds tallest bird?","A Flamingo","A Ostrich","A Stalk","A Crow","A Ostrich"))
        questionsList.add(Questions("What body parts do not exist in birds?","Teeth","Toes","Spines","Arms","Teeth"))
        questionsList.add(Questions("Which bird is characterised by a yellow bill?","Toucan","Puffin","Ibis","Swan","Toucan"))
        questionsList.add(Questions("What is a group of ravens called?","A Flock","A School","A Spook","A Murder","A Murder"))

        //questionsList.shuffle()
        Questions= questionsList[index]
        setAllQuestions()
        countdown()
    }

    fun countdown(){
        var duration:Long= TimeUnit.SECONDS.toMillis(10)

        object : CountDownTimer(duration, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                var sDuration:String= String.format(
                    Locale.ENGLISH,
                    "%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(
                        TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))

                countDown.text = sDuration
            }
            override fun onFinish() {
                index++
                if (index<questionsList.size){
                    Questions=questionsList[index]
                    setAllQuestions()
                    resetBackground()
                    enableButton()
                    countdown()
                }
                else{
                    gameResult()
                }
            }
        }.start()
    }

    private fun correctAns(option: Button){
        option.background=getDrawable(R.drawable.right)

        correctAnswerCount++
    }

    private fun wrongAns(option: Button){
        option.background=resources.getDrawable(R.drawable.wrong)
        wrongAnswerCount++

    }

    private fun gameResult(){
        var intent= Intent(this, Result::class.java)
        intent.putExtra("correct",correctAnswerCount.toString())
        intent.putExtra("total",questionsList.size.toString())
        startActivity(intent)
    }

    private fun setAllQuestions() {
        questions.text=Questions.question
        option1.text=Questions.option1
        option2.text=Questions.option2
        option3.text=Questions.option3
        option4.text=Questions.option4
    }
    private fun enableButton(){
        option1.isClickable=true
        option2.isClickable=true
        option3.isClickable=true
        option4.isClickable=true
    }
    private fun disableButton(){
        option1.isClickable=false
        option2.isClickable=false
        option3.isClickable=false
        option4.isClickable=false
    }
    private fun resetBackground(){
        option1.background=resources.getDrawable(R.drawable.bg)
        option2.background=resources.getDrawable(R.drawable.bg)
        option3.background=resources.getDrawable(R.drawable.bg)
        option4.background=resources.getDrawable(R.drawable.bg)
    }
    fun option1Clicked(view: View){
        disableButton()
        if(Questions.option1==Questions.answer){
            option1.background=resources.getDrawable(R.drawable.right)
            correctAns(option1)
        }
        else{
            wrongAns(option1)
        }
    }

    fun option2Clicked(view: View){
        disableButton()
        if(Questions.option2==Questions.answer){
            option2.background=resources.getDrawable(R.drawable.right)
            correctAns(option2)
        }
        else{
            wrongAns(option2)
        }
    }
    fun option3Clicked(view: View){
        disableButton()
        if(Questions.option3==Questions.answer){
            option3.background=resources.getDrawable(R.drawable.right)
            correctAns(option3)
        }
        else{
            wrongAns(option3)
        }
    }
    fun option4Clicked(view: View){
        disableButton()
        if(Questions.option4==Questions.answer){
            option4.background=resources.getDrawable(R.drawable.right)
            correctAns(option4)
        }
        else{
            wrongAns(option4)
        }
    }

    override fun onBackPressed() {

        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            backToast?.cancel()
            finish()
        }

        else {
            backToast = Toast.makeText(baseContext, "DOUBLE PRESS TO QUIT GAME", Toast.LENGTH_SHORT)
            backToast?.show()
        }
        backPressedTime = System.currentTimeMillis()
    }
}