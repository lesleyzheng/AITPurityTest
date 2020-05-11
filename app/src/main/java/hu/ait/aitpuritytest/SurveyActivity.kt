package hu.ait.aitpuritytest

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

                taskResult.results.forEach {
                    // Log.e("logTag01", "${it.results[0]}")
                    // Log.e("logTag01", "${it.results[0].stringIdentifier}")
                    if (it.results[0].stringIdentifier == "yes") {
                        surveyScore += 1
                    }
                }

               Log.e("logTag", "surveyScore ${surveyScore}")

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
                Log.e("logTag", "discarded")
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
