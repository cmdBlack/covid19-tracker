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

public class World extends AppCompatActivity {
    private ImageButton refresh1, back;
    private TextView total1, death1, recovered1;
    private ImageView bg1;
    private String totalB, deathB, recoveredB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_world);
        bg1 = findViewById(R.id.imageViewBg1);
        Glide.with(this).load(Uri.parse("android.resource://"+getPackageName()+"/"+R.drawable.stars)).into(bg1);
        refresh1 = findViewById(R.id.imgBtnRefresh1);
        total1 = findViewById(R.id.textViewTotal1);
        death1 = findViewById(R.id.textViewDeath1);
        recovered1 = findViewById(R.id.textViewRecovered1);
        back = findViewById(R.id.imgBtnB);
        new doit1().execute();
        a();


    }

    private void a() {
        final SoundPool ref1 = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);
        final int soundIdRef1 = ref1.load(this, R.raw.refresh, 1);

        refresh1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref1.play(soundIdRef1, 1, 1, 0, 0, 1);
                new doit1().execute();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(World.this,China.class);
                startActivity(intent);
            }
        });
    }

    public class doit1 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                Document doc = Jsoup.connect("https://www.worldometers.info/coronavirus/").get();
                Element first = doc.select("div.maincounter-number").first();
                Element second = doc.select("div.maincounter-number").get(1);
                Element third = doc.select("div.maincounter-number").get(2);

                totalB = first.text();
                deathB = second.text();
                recoveredB = third.text();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            total1.setText(totalB);
            death1.setText(deathB);
            recovered1.setText(recoveredB);
        }
    }
}
