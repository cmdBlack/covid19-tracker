package com.pmac.covid19tracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

public class China extends AppCompatActivity {
    private ImageButton refresh21, us21, italy21;
    private TextView total21, death21, recover21;
    private String total21A, death21A, recover21A;
    private ImageView bg22;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_china);

        bg22 = findViewById(R.id.imageViewBg22);
        Glide.with(this).load(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.stars)).into(bg22);
        refresh21 = findViewById(R.id.imgBtnRefresh21);
        us21 = findViewById(R.id.imgBtnU);
        italy21 = findViewById(R.id.imgBtnI);
        total21 = findViewById(R.id.textViewTotal3);
        death21 = findViewById(R.id.textViewDeath3);
        recover21 = findViewById(R.id.textViewRecovered3);
        new doit21().execute();
        c();
    }

    private void c() {
        final SoundPool ref = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final int soundIdRef = ref.load(this, R.raw.refresh, 1);

        refresh21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.play(soundIdRef, 1, 1, 0, 0, 1);
                new doit21().execute();
            }
        });

        us21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(China.this, Italy.class);
                startActivity(intent);
            }
        });

        italy21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(China.this, World.class);
                startActivity(intent);
            }
        });
    }


    public class doit21 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("https://www.worldometers.info/coronavirus/country/china/").get();
                Element first = doc.select("div.maincounter-number").first();
                Element second = doc.select("div.maincounter-number").get(1);
                Element third = doc.select("div.maincounter-number").get(2);

                total21A = first.text();
                death21A = second.text();
                recover21A = third.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            total21.setText(total21A);
            death21.setText(death21A);
            recover21.setText(recover21A);
        }
    }
}
