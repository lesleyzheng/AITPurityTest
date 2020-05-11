package hu.ait.aitpuritytest

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.content.ContextCompat
import com.quickbirdstudios.surveykit.*
import com.quickbirdstudios.surveykit.result.TaskResult
import com.quickbirdstudios.surveykit.steps.CompletionStep
import com.quickbirdstudios.surveykit.steps.InstructionStep
import com.quickbirdstudios.surveykit.steps.QuestionStep
import com.quickbirdstudios.surveykit.survey.SurveyView
import kotlinx.android.synthetic.main.activity_survey.*

class SurveyActivity : AppCompatActivity() {

    private lateinit var surveyView: SurveyView
    private var surveyResults = intArrayOf(100)
    private var surveyScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        surveyView = survey_view
        createSurvey()
    }

    private fun createSurvey(){
        var startStep = InstructionStep("AIT Purity Test", "Press start to begin the test", "Start")
        var endStep = CompletionStep("You've finished!", "", "End Test")

        // List of Questions
        var question1 = QuestionStep("Went to one country outside of Hungary?", "",
            answerFormat = AnswerFormat.SingleChoiceAnswerFormat(listOf(TextChoice("yes"), TextChoice("no")))
        )

        val steps = listOf(startStep, question1, endStep)
        val task = OrderedTask(steps)

        surveyView.onSurveyFinish = { taskResult: TaskResult, reason: FinishReason ->
            if (reason == FinishReason.Completed) {

                for ((index, result) in taskResult.results.get(1).results.withIndex()) {
                    // Convert string answer to int value, 1=yes, 2=no
                    var value = 0
                    if (result.stringIdentifier == "yes") {
                        value = 1
                    }
                    // Save result
                    surveyResults.set(index, value)
                    surveyScore += value
                }

//                Log.e("logTag", "surveyScore ${surveyScore}")
//                Log.e("logTag", "surveyResult ${surveyResults}")

                setResult(Activity.RESULT_OK)
                finish()
            }
            if (reason == FinishReason.Discarded){
                setResult(Activity.RESULT_OK)
                finish()
            }
        }

        val configuration = SurveyTheme(
            themeColorDark = R.color.colorPrimary,
            themeColor = R.color.colorPrimaryDark,
            textColor = Color.RED
        )

        surveyView.start(task, configuration)

    }

}
