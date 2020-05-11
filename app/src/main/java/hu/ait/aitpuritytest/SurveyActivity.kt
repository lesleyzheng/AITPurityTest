package hu.ait.aitpuritytest

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.quickbirdstudios.surveykit.*
import com.quickbirdstudios.surveykit.result.TaskResult
import com.quickbirdstudios.surveykit.steps.CompletionStep
import com.quickbirdstudios.surveykit.steps.InstructionStep
import com.quickbirdstudios.surveykit.steps.QuestionStep
import com.quickbirdstudios.surveykit.steps.Step
import com.quickbirdstudios.surveykit.survey.SurveyView
import hu.ait.aitpuritytest.data.Score
import kotlinx.android.synthetic.main.activity_survey.*

class SurveyActivity : AppCompatActivity() {

    private lateinit var surveyView: SurveyView
    private var surveyResults = intArrayOf(100)
    private var surveyScore = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_survey)

        surveyView = survey_view
        val uid = intent.getStringExtra("UID")
        createSurvey(uid)
    }

    private fun createSurvey(uid: String){
      
        val steps = makeSteps()
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

               Log.e("logTag", "surveyScore ${surveyScore}")
               Log.e("logTag", "surveyResult ${surveyResults}")

                var postsCollection = FirebaseFirestore.getInstance().collection(
                    "results")

                postsCollection.document(uid).set(Score(uid, surveyScore)).addOnSuccessListener {
                    Log.e("logtag", "success")
                }.addOnFailureListener{
                        Log.e("logtag", "failed")
                    }

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

    private fun makeSteps() : List<Step>{
        var startStep = InstructionStep("AIT Purity Test", "Press start to begin the test", "Start")
        var endStep = CompletionStep("You've finished!", "", "End Test")

        val steps = mutableListOf<Step>(startStep)
        for (q in resources.getStringArray(R.array.surveyQuestions)) {
            var question = QuestionStep(q, "",
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(listOf(TextChoice("yes"), TextChoice("no")))
            )
            steps.add(question)
        }
        steps.add(endStep)
        return steps
    }


}
