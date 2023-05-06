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

public class Italy extends AppCompatActivity {
    private ImageButton refresh22, china22, earth22;
    private TextView total22, death22, recover22;
    private String total22A, death22A, recover22A;
    private ImageView bg21;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_italy);
        bg21 = findViewById(R.id.imageViewBg21);
        Glide.with(this).load(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.stars)).into(bg21);
        refresh22 = findViewById(R.id.imgBtnRefresh22);
        china22 = findViewById(R.id.imgBtnC);
        earth22 = findViewById(R.id.imgBtnEarth);
        total22 = findViewById(R.id.textViewTotal4);
        death22 = findViewById(R.id.textViewDeath4);
        recover22 = findViewById(R.id.textViewRecovered4);
        new doit22().execute();
        i();
    }

    private void i() {
        final SoundPool ref = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final int soundIdRef = ref.load(this, R.raw.refresh, 1);

        refresh22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.play(soundIdRef, 1, 1, 0, 0, 1);
                new doit22().execute();
            }
        });

        china22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Italy.this, Usa.class);
                startActivity(intent);
            }
        });

        earth22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Italy.this, China.class);
                startActivity(intent);
            }
        });
    }


    public class doit22 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("https://www.worldometers.info/coronavirus/country/italy/").get();
                Element first = doc.select("div.maincounter-number").first();
                Element second = doc.select("div.maincounter-number").get(1);
                Element third = doc.select("div.maincounter-number").get(2);

                total22A = first.text();
                death22A = second.text();
                recover22A = third.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            total22.setText(total22A);
            death22.setText(death22A);
            recover22.setText(recover22A);
        }
    }
}
