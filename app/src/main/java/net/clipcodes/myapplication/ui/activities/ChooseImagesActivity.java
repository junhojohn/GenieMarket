package net.clipcodes.myapplication.ui.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.travijuu.numberpicker.library.NumberPicker;

import net.clipcodes.myapplication.R;

public class ChooseImagesActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnFinish;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_images);

        toolbar = findViewById(R.id.choose_image_toolbar);
        setSupportActionBar(toolbar);
        setTheme(R.style.AppTheme_Cursor);

        btnFinish = findViewById(R.id.btn_finish);
        btnBack = findViewById(R.id.btn_back2);

        btnFinish.setOnClickListener(clik);
        btnBack.setOnClickListener(clik);

    }

    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_finish:
                    break;
                case R.id.btn_back2:
                    finish();
                    break;
            }


        }
    };

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        if(item.getItemId() == android.R.id.home){
//            finish();
//        }
//        return super.onOptionsItemSelected(item);
//    }
}
