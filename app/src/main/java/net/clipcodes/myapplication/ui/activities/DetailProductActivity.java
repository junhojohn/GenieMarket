package net.clipcodes.myapplication.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import net.clipcodes.myapplication.R;

import java.util.ArrayList;

public class DetailProductActivity extends AppCompatActivity {

    ImageView mFlower;
    TextView mDescription;
    ArrayList<Integer> imageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mFlower = findViewById(R.id.ivImage);
        mDescription = findViewById(R.id.tvDescription);

        Bundle mBundle = getIntent().getExtras();
        if (mBundle != null) {
//            mFlower.setImageResource(mBundle.getInt("Image"));
            imageList = mBundle.getIntegerArrayList("ImageList");
            mFlower.setImageResource(imageList.get(0));
            mDescription.setText(mBundle.getString("Description"));
        }
    }
}