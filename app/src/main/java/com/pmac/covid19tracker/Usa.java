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

public class Usa extends AppCompatActivity {
    private ImageButton refresh20, ph20, china20;
    private TextView total20, death20, recover20;
    private String total20A, death20A, recover20A;
    private ImageView bg20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usa);
        bg20 = findViewById(R.id.imageViewBg20);
        Glide.with(this).load(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.stars)).into(bg20);

        refresh20 = findViewById(R.id.imgBtnRefresh20);
        ph20 = findViewById(R.id.imgBtnP);
        china20 = findViewById(R.id.imgBtnC);
        total20 = findViewById(R.id.textViewTotal2);
        death20 = findViewById(R.id.textViewDeath2);
        recover20 = findViewById(R.id.textViewRecovered2);
        new doit20().execute();
        u();
    }

    private void u() {
        final SoundPool ref = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final int soundIdRef = ref.load(this, R.raw.refresh, 1);

        refresh20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.play(soundIdRef, 1, 1, 0, 0, 1);
                new doit20().execute();
            }
        });

        ph20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Usa.this, MainActivity.class);
                startActivity(intent);
            }
        });

        china20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Usa.this, Italy.class);
                startActivity(intent);
            }
        });
    }


    public class doit20 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("https://www.worldometers.info/coronavirus/country/us/").get();
                Element first = doc.select("div.maincounter-number").first();
                Element second = doc.select("div.maincounter-number").get(1);
                Element third = doc.select("div.maincounter-number").get(2);

                total20A = first.text();
                death20A = second.text();
                recover20A = third.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            total20.setText(total20A);
            death20.setText(death20A);
            recover20.setText(recover20A);
        }
    }
}
