package vn.edu.usth.englishdictionary.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import vn.edu.usth.englishdictionary.R;
import vn.edu.usth.englishdictionary.utils.DataBase;

public class GrammarActivity extends AppCompatActivity {

    DataBase db=new DataBase(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grammar);

    }


}
