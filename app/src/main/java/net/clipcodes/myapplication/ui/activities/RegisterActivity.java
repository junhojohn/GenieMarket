package net.clipcodes.myapplication.ui.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import net.clipcodes.myapplication.R;

public class RegisterActivity extends AppCompatActivity {

    Toolbar toolbar;
    Button btnRegistration;
    Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        toolbar = findViewById(R.id.register_toolbar);
        setSupportActionBar(toolbar);

        btnRegistration = findViewById(R.id.btn_registration);
        btnBack = findViewById(R.id.btn_back);

        btnRegistration.setOnClickListener(clik);
        btnBack.setOnClickListener(clik);

//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("상품등록");
//        getSupportActionBar().setIcon(R.drawable.ic_back_button_24dp);
    }

    public View.OnClickListener clik = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_registration:
                    break;
                case R.id.btn_back:
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
