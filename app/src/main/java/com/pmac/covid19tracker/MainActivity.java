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
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ImageButton refresh, forward;
    private TextView total, death, recovered;
    private   ImageView bg;
    private String totalA, deathA, recoveredA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bg = findViewById(R.id.imageViewBg);
        Glide.with(this).load(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.stars)).into(bg);
        refresh = findViewById(R.id.imgBtnRefresh);
        total = findViewById(R.id.textViewTotal);
        death = findViewById(R.id.textViewDeath);
        recovered = findViewById(R.id.textViewRecovered);
        forward = findViewById(R.id.imgBtnF);
        new doit().execute();
        g();
    }

    private void g() {
        final SoundPool ref = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final int soundIdRef = ref.load(this, R.raw.refresh, 1);

         refresh.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ref.play(soundIdRef, 1, 1, 0, 0, 1);
                 new doit().execute();
             }
         });

         forward.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent = new Intent(MainActivity.this, Usa.class);
                 startActivity(intent);
             }
         });
    }

    public class doit extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("https://www.worldometers.info/coronavirus/country/philippines/").get();
                Element first = doc.select("div.maincounter-number").first();
                Element second = doc.select("div.maincounter-number").get(1);
                Element third = doc.select("div.maincounter-number").get(2);

                totalA = first.text();
                deathA = second.text();
                recoveredA = third.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            total.setText(totalA);
            death.setText(deathA);
            recovered.setText(recoveredA);
        }
    }
}
