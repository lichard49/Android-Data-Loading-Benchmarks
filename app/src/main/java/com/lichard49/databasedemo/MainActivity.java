package com.lichard49.databasedemo;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadDatabase.run();
                loadCSV.run();
            }
        }).start();
    }

    private Runnable loadDatabase = new Runnable() {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();

            final String DATABASE_FILE = "/storage/emulated/0/data.db";

            List<Float> xCoordinates = new LinkedList<>();
            List<Float> yCoordinates = new LinkedList<>();
            List<Float> zCoordinates = new LinkedList<>();

            SQLiteDatabase database = SQLiteDatabase.openDatabase(DATABASE_FILE, null, SQLiteDatabase.OPEN_READONLY);
            String getAllQuery = "SELECT * FROM coordinates";
            Cursor databaseCursor = database .rawQuery(getAllQuery, null);
            if (databaseCursor.moveToFirst()) {
                do {
                    xCoordinates.add(databaseCursor.getFloat(0));
                    yCoordinates.add(databaseCursor.getFloat(1));
                    zCoordinates.add(databaseCursor.getFloat(2));
                } while (databaseCursor.moveToNext());
            }

            long endTime = System.currentTimeMillis();
            System.out.println(">>> SQLite loaded " + xCoordinates.size() + ", " + yCoordinates.size() + ", " + zCoordinates.size() + " elements in " + (endTime - startTime) + " milliseconds");
        }
    };

    private Runnable loadCSV = new Runnable() {
        @Override
        public void run() {
            long startTime = System.currentTimeMillis();

            final String CSV_FILE = "/storage/emulated/0/data.csv";

            List<Float> xCoordinates = new LinkedList<>();
            List<Float> yCoordinates = new LinkedList<>();
            List<Float> zCoordinates = new LinkedList<>();

            try {
                BufferedReader reader = new BufferedReader(new FileReader(new File(CSV_FILE)));
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] lineParts = line.split(", ");
                    xCoordinates.add(Float.parseFloat(lineParts[0]));
                    yCoordinates.add(Float.parseFloat(lineParts[1]));
                    zCoordinates.add(Float.parseFloat(lineParts[2]));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            long endTime = System.currentTimeMillis();
            System.out.println(">>> CSV loaded " + xCoordinates.size() + ", " + yCoordinates.size() + ", " + zCoordinates.size() + " in " + (endTime - startTime) + " milliseconds");
        }
    };
}
