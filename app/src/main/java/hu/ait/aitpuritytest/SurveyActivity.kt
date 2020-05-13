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
        val uid = intent.getStringExtra(getString(R.string.UID_extra))
        createSurvey(uid)
    }

    private fun createSurvey(uid: String){
      
        val steps = makeSteps()
        val task = OrderedTask(steps)

        surveyView.onSurveyFinish = { taskResult: TaskResult, reason: FinishReason ->
            if (reason == FinishReason.Completed) {

                taskResult.results.forEach {
                    if (it.results[0].stringIdentifier == getString(R.string.yes)) {
                        surveyScore += 1
                    }
                }

                surveyScore = 100-surveyScore


                var postsCollection = FirebaseFirestore.getInstance().collection(
                    getString(R.string.results))

                postsCollection.document(uid).set(Score(uid, surveyScore)).addOnSuccessListener {
                }.addOnFailureListener{
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
        var startStep = InstructionStep(getString(R.string.app_name), getString(R.string.press_start), getString(
                    R.string.start))
        var endStep = CompletionStep(getString(R.string.finish), "", getString(R.string.end_test))

        val steps = mutableListOf<Step>(startStep)
        for (q in resources.getStringArray(R.array.surveyQuestions)) {
            var question = QuestionStep(q, "",
                answerFormat = AnswerFormat.SingleChoiceAnswerFormat(listOf(TextChoice(getString(R.string.yes)), TextChoice(getString(
                                    R.string.no))))
            )
            steps.add(question)
        }
        steps.add(endStep)
        return steps
    }


}
