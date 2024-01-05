package vn.edu.usth.englishdictionary.ui;

import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.englishdictionary.utils.api;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import vn.edu.usth.englishdictionary.R;

public class ParaphraseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paraphrase);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        EditText send_text = findViewById(R.id.send_text_id);
        Button send_button = findViewById(R.id.send_button_id);

        send_button.setOnClickListener(v -> {
            String str = send_text.getText().toString();
            TextView textView = findViewById(R.id.received_text_id);
            String paraphrase = String.valueOf(api.apiMethod(str));
            textView.setText(paraphrase);
        });

    }
}