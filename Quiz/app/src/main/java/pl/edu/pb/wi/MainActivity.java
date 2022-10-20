package pl.edu.pb.wi;

import static pl.edu.pb.wi.R.id.hint_button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button trueButton;
    private Button falseButton;
    private Button nextButton;
    private Button hintButton;
    private boolean answerWasShown;
    private TextView questionTextView;
    private static final String KEY_CURRENT_INDEX = "currentIndex";
    public static final String KEY_EXTRA_ANSWER = "com.example.quiz2.correctAnswer";
    private static final int REQUEST_CODE_PROMPT=0;

    private Question[] questions = new Question[]{
            new Question(R.string.q_madryt, true),
            new Question(R.string.q_polska, false),
            new Question(R.string.q_tajwan, true),
            new Question(R.string.q_cypr, false)
    };

    private int currentIndex = 0;


    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Activity", "Wywołana metoda onStart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Activity", "Wywołana metoda onDestroy()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Activity", "Wywołana metoda onStop()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Activity", "Wywołana metoda onPause()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Activity", "Wywołana metoda onResume()");
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("Activity", "Wywołana metoda: onSaveInstanceState()");
        outState.putInt(KEY_CURRENT_INDEX, currentIndex);
    }

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Activity", "Wywołana metoda onCreate()");
        setContentView(R.layout.activity_main);
        if (savedInstanceState != null){
            currentIndex = savedInstanceState.getInt(KEY_CURRENT_INDEX);
        }
        trueButton = findViewById(R.id.true_button);
        falseButton = findViewById(R.id.false_button);
        nextButton = findViewById(R.id.next_button);
        hintButton = findViewById(R.id.hint_button);
        questionTextView = findViewById(R.id.question_text_view);

        trueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V){
                checkAnswerCorrectness(true);
            }
        });
        falseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V){
                checkAnswerCorrectness(false);
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V){
                currentIndex = (currentIndex + 1)%questions.length;
                answerWasShown = false;
                setNextQuestion();
            }
        });
        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View V){
                Intent intent = new Intent (MainActivity.this, Promp.class);
                boolean correctAnswer = questions[currentIndex].isTrueAnswer();
                intent.putExtra(KEY_EXTRA_ANSWER,correctAnswer);
                startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE_PROMPT);
            }
        });
        setNextQuestion();
    }
    private void checkAnswerCorrectness(boolean userAnswer){
        boolean correctAnswer = questions[currentIndex].isTrueAnswer();
        int resultMessageId = 0;
        if (answerWasShown){
            resultMessageId = R.string.answer_was_shown;
        }
        if (userAnswer == correctAnswer) {
            resultMessageId = R.string.correct_answer;
        } else{
            resultMessageId = R.string.incorrect_answer;
        }
        Toast.makeText(this, resultMessageId, Toast.LENGTH_SHORT).show();
    }
    private void setNextQuestion(){
        questionTextView.setText(questions[currentIndex].getQuestionID());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode!=RESULT_OK){
            return;
        }
        if(requestCode==REQUEST_CODE_PROMPT){
            if(data==null){
                return;
            }
            answerWasShown=data.getBooleanExtra(Promp.KEY_EXTRA_ANSWER_SHOWN,false);
        }
    }
}