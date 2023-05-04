package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailsDestination extends AppCompatActivity {
    private TextView etTitle, etLocation, etDescription;
    private ImageView etImage;
    private RatingBar etRating;

    private Button btnUlasanView, btnUlasanAdd;
    private Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_destination);

        etTitle = findViewById(R.id.title_details);
        etLocation = findViewById(R.id.location_details);
        etDescription = findViewById(R.id.description_details);
        etImage = findViewById(R.id.image_details);
        etRating = findViewById(R.id.rating_details);

        etRating.setRating(getIntent().getFloatExtra("rating",0));

        etTitle.setText(getIntent().getStringExtra("title"));
        etLocation.setText(getIntent().getStringExtra("location"));
        etDescription.setText(getIntent().getStringExtra("description"));

        Picasso.get().load(getIntent().getStringExtra("image")).resize(1080, 1980 ).placeholder(R.mipmap.ic_launcher).into(etImage);

        btnUlasanView = findViewById(R.id.btn_view_ulas);
        btnUlasanView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DetailsDestination.this, UlasanDestination.class);
                intent.putExtra("id", getIntent().getStringExtra("id"));
                startActivity(intent);
            }
        });

        TokenManager tokenManager = new TokenManager(this);
        String token = tokenManager.getToken();

        btnUlasanAdd = findViewById(R.id.btn_ulasan_add);
        btnUlasanAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initCostumeDialogAddUlasan(token);
                dialog.show();
            }
        });

    }

    private void initCostumeDialogAddUlasan(String token){
        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_ulasan);
        dialog.setCancelable(true);

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);

        RatingBar ratingBar = dialog.findViewById(R.id.rating_val);
        EditText message = dialog.findViewById(R.id.message_ulasan);

        Button btnOK = dialog.findViewById(R.id.btn_confirm_ulasan);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                String ulasMessage = message.getText().toString();

                AddUlasanTask task = new AddUlasanTask(DetailsDestination.this, token, getIntent().getStringExtra("id"), ulasMessage, rating);
                task.execute();

                dialog.dismiss();
            }
        });

    }
}