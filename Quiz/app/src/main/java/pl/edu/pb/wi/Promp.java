package pl.edu.pb.wi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Promp extends AppCompatActivity {

    private boolean correctAnswer;
    private Button showCorrectAnswerButton;
    private TextView answerTextView;
    public static final String KEY_EXTRA_ANSWER_SHOWN="com.example.quiz2.answerShown";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promp);

        showCorrectAnswerButton=findViewById(R.id.button_promp);
        answerTextView=findViewById(R.id.pewno);

        correctAnswer = getIntent().getBooleanExtra(MainActivity.KEY_EXTRA_ANSWER,true);

        showCorrectAnswerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int answer = correctAnswer ? R.string.button_true : R.string.button_false;
                answerTextView.setText(answer);
                setAnswerShownResult(true);
            }
        });
    }
    private void setAnswerShownResult(boolean answerWasShown){
        Intent resultIntent = new Intent();
        resultIntent.putExtra(KEY_EXTRA_ANSWER_SHOWN,answerWasShown);
        setResult(RESULT_OK, resultIntent);
    }
}