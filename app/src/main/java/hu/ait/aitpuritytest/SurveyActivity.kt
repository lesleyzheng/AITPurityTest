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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        surveyView = survey_view
        createSurvey()
    }

    private fun createSurvey(){
        var startStep = InstructionStep("AIT Purity Test", "Press start to begin the test", "Start")
        var endStep = CompletionStep("You've finished!", "", "End Test")
        var question1 = QuestionStep("Went to one country outside of Hungary?", "",
            answerFormat = AnswerFormat.SingleChoiceAnswerFormat(listOf(TextChoice("yes"), TextChoice("no")))
        )

        val steps = listOf(startStep, question1, endStep)
        val task = OrderedTask(steps)

        surveyView.onSurveyFinish = { taskResult: TaskResult, reason: FinishReason ->
            if (reason == FinishReason.Completed) {
                taskResult.results.forEach { stepResult ->
                    Log.e("logTag", "answer ${stepResult.results.firstOrNull()}")
                    setResult(Activity.RESULT_OK)
                    finish()
                }
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
