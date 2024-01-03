package vn.edu.usth.englishdictionary.ui;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.Locale;
import vn.edu.usth.englishdictionary.utils.DataBase;
import vn.edu.usth.englishdictionary.R;


public class SearchActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    TextView txt;
    TextToSpeech t1;
    String worl;
    String mean;
    DataBase db = new DataBase(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        txt = findViewById(R.id.tvhientratu);
        Bundle b = getIntent().getExtras();
        worl = b.getString("key_Word");
        mean = b.getString("key_Mean");
        Lichsu(worl);
        txt.setText(mean);
        //hiện tiêu đề;
        ActionBar ab = getSupportActionBar();
        //set mầu cho actionBar
        ab.setTitle(worl);
        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        t1 = new TextToSpeech(getApplicationContext(), status -> {
            if (status != TextToSpeech.ERROR) {
                t1.setLanguage(Locale.ENGLISH);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item_tratu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_search) {
            Intent in = new Intent(getApplicationContext(), SearchDictionaryAtivity.class);
            finish();
            startActivity(in);
            return true;}
        else if (item.getItemId() == R.id.itemdoc) {
            t1.speak(worl, TextToSpeech.QUEUE_FLUSH, null);
            Toast.makeText(this, "Đang đọc", Toast.LENGTH_SHORT).show();
            return true;}
        else if (item.getItemId() == R.id.itemdoc) {

        } else if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }





//    private void startSpeechToText() {
//        try {
//            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
//            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
//            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
//            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say something...");
//
//            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
//        } catch (ActivityNotFoundException e) {
//            Toast.makeText(getApplicationContext(), "Speech to text not supported on your device", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == REQ_CODE_SPEECH_INPUT) {
//            if (resultCode == RESULT_OK && data != null) {
//                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
//                if (result != null && result.size() > 0) {
//                    String spokenText = result.get(0);
//                    // You can perform a search with the recognized text here
//                    // For example, txt.setText(spokenText);
//                    edtSearch.setText(spokenText);
//                }
//            }
//        }
//    }


    public void Lichsu(String a) {
        Cursor c = db.getCursor("select * from LichSuTraTu");
        int leng = c.getCount();
        if (leng < 50) {
            setLS(a, leng);
        } else {
            db.ExecuteSQL("delete from LichSuTraTu where ID = 1");// xoa lich sử
            setLS(a, leng);
        }
    }

    //kiểm tra trùng lặp
    public void setLS(String a, int i) {
        Cursor c = db.getCursor("select * from LichSuTraTu where work = '" + a + "'");
        int leng = c.getCount();
        if (leng == 0) {
            db.ExecuteSQL("insert into LichSuTraTu values(" + i +1 + ",\"" + a + "\")");
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
