package vn.edu.usth.englishdictionary.ui;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.SparseBooleanArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

import vn.edu.usth.englishdictionary.R;
import vn.edu.usth.englishdictionary.adapter.HistoryApdater;
import vn.edu.usth.englishdictionary.model.History;
import vn.edu.usth.englishdictionary.utils.DataBase;

public class HistoryTestActivity extends AppCompatActivity {
    ArrayList<History> arr = null;
    DataBase db = new DataBase(this);
    HistoryApdater adap;
    ListView lv;
    RadioButton rA, rB, rC, rD;
    QuestionActivity mainQuestion = new QuestionActivity();




    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_test);
        lv = findViewById(R.id.lvHistory);
        arr = new ArrayList<>();
        ActionBar ab = getSupportActionBar();
        //set mầu cho actionBar
        ab.setTitle("Lịch Sử Luyện tập");
        //Hiện nút back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        adap = new HistoryApdater(this, R.layout.item_history_test, arr);
        load();
        lv.setAdapter(adap);
        getView();
        lv.setOnItemClickListener((parent, view, position, id) -> {
            Intent in = new Intent(HistoryTestActivity.this, HistoryActivity.class);
            Bundle b = new Bundle();
            History lichsu = (History) adap.getItem(position);
            b.putString("ID", lichsu.getIdcauhoi());
            b.putString("Time", lichsu.getTimeThi());
            b.putString("IDCheck", lichsu.getIdtich());
            in.putExtras(b);
            startActivity(in);
        });

    }


    public void getView() {
        lv.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);//chọn nhiều itome trong ListView
        lv.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {
            @Override
            public void onItemCheckedStateChanged(android.view.ActionMode mode, int position, long id, boolean checked) {
                final int checkedCount = lv.getCheckedItemCount();
                //hiện só lượng đã chọn
                mode.setTitle(checkedCount + " /" + adap.getSelectedCount());
                adap.toggleSelection(position);//xóa item hiện trên listView
            }

            @Override
            public boolean onCreateActionMode(android.view.ActionMode mode, Menu menu) {
                mode.getMenuInflater().inflate(R.menu.menu_opption, menu);
                return true;
            }

            @Override
            public boolean onPrepareActionMode(android.view.ActionMode mode, Menu menu) {
                return false;
            }

            @Override
            public boolean onActionItemClicked(android.view.ActionMode mode, MenuItem item) {
                if (item.getItemId() == R.id.delete) {
                    SparseBooleanArray selected = adap.getSelectedIds(); // Lấy ra các vị trí đã chọn
                    for (int i = (selected.size() - 1); i >= 0; i--) {
                        if (selected.valueAt(i)) {
                            History lichsu = (History) adap.getItem(selected.keyAt(i));
                            adap.remove(lichsu); // Xóa trong mảng ArryList
                            delete(lichsu.getId()); // Xóa trong CSDL
                        }
                    }
                    mode.finish();
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onDestroyActionMode(android.view.ActionMode mode) {
            }
        });

    }

    public void load() {
        Cursor cu = db.getCursor("select * from LichSuTest");
        if (cu.moveToLast()) {
            do {
                History lichsu = new History();
                lichsu.setId(cu.getInt(0));
                lichsu.setNgay(cu.getString(1));
                lichsu.setIdcauhoi(cu.getString(2));
                lichsu.setIdtich(cu.getString(3));
                lichsu.setTimeThi(cu.getString(5));
                lichsu.setDiem(cu.getInt(4));
                arr.add(lichsu);
            } while (cu.moveToPrevious());

        }
    }
    //kiểm tra đấp án đúng  thì trả ra true ngược lại
    public boolean kiemtra(String a, String b) {
        if (a.contains(b.replaceAll("\\s+", ""))) {
            return true;
        } else {
            return false;
        }
    }
    public void delete(int id)
    {
        db.ExecuteSQL("delete from LichSuTest where idqueshis = "+id+"");
    }

}
