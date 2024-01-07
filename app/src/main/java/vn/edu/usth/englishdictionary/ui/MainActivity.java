package vn.edu.usth.englishdictionary.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.util.ArrayList;

import vn.edu.usth.englishdictionary.R;
import vn.edu.usth.englishdictionary.adapter.HomeView;
import vn.edu.usth.englishdictionary.utils.DataBase;

public class MainActivity extends AppCompatActivity {
    android.widget.ListView lv;
    DataBase db = new DataBase(this);
    ArrayList<String> array = null;
    String[] item = {"Tra Từ Điển Anh-Việt", "Ngữ Pháp","Luyện Tập",  "Lịch Sử Luyện Tập" , "Diễn Giải Đoạn Văn" , "Cài Đặt"};
    Integer[] icon = {R.drawable.timkiem,  R.drawable.grammar,R.drawable.practice, R.drawable.history,R.drawable.paraphrase,R.drawable.cai_dat};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            db.CopyDB2SDCard();
        } catch (IOException e) {
            e.printStackTrace();
        }
        array = new ArrayList<>();
        for (String a : item) {
            array.add(a);
        }

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
//        ActionBar ab = getSupportActionBar();
//        //set mầu cho actionBar
//        ab.setTitle("Tra Từ Điển");

        HomeView adapter = new HomeView(this, array, icon);
        lv = findViewById(R.id.List_item);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener((parent, view, position, id) -> {
            // TODO Auto-generated method stubin
            Intent in;
            switch (position) {
                case 0:
                    in = new Intent(getApplication(), SearchDictionaryAtivity.class);
                    startActivity(in);
                    break;
                    case 2:
                        in = new Intent(getApplication(), QuestionActivity.class);
                        startActivity(in);
                        break;
                        case 1:
                            in = new Intent(getApplication(), GrammarListActivity.class);
                            startActivity(in);
                            break;
                            case 3:
                                in = new Intent(getApplication(), HistoryTestActivity.class);
                                startActivity(in);
                                break;
                                case 4:
                                    in = new Intent(getApplication(), ParaphraseActivity.class);
                                    startActivity(in);
                                    break;
                                    case 5:
                                        in = new Intent(getApplication(), SettingActivity.class);
                                        startActivity(in);
                                        break;

                    }
        });
    }
}

