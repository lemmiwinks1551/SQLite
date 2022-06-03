package com.example.sqllite;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View view) {
        // Для создания или открытия новой базы данных из кода Activity в Android мы вызываем openOrCreateDatabase()
        // Аргументы:
        // * название для базы данных;
        // * числовое значение, которое определяет режим работы (как правило, в виде константы MODE_PRIVATE);
        // * необязательный параметр в виде объекта SQLiteDatabase.CursorFactory, который представляет фабрику создания курсора для работы с бд.

        SQLiteDatabase sqLiteDatabase = getBaseContext().openOrCreateDatabase("app.db", MODE_PRIVATE, null);

        // Для выполнения запроса к базе данных можно использовать метод execSQL класса SQLiteDatabase.
        // В этот метод передается SQL-выражение. Например, создание в базе данных таблицы users:
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS users (name TEXT, age INTEGER, UNIQUE(name))");

        // Добавляются два объекта в базу данных с помощью SQL-выражения INSERT.
        sqLiteDatabase.execSQL("INSERT OR IGNORE INTO users VALUES ('Tom Smith', 23), ('John Dow', 31);");

        // С помощью выражения SELECT получаем всех добавленных пользователей из базы данных в виде курсора Cursor
        Cursor query = sqLiteDatabase.rawQuery("SELECT * FROM users;", null);

        TextView textView = findViewById(R.id.textView);
        textView.setText("");

        // Пока есть следующая строка в запросе к БД, добавляем его к TextView
        while (query.moveToNext()) {
            String name = query.getString(0);
            int age = query.getInt(1);
            textView.append(String.format("Имя: %s, возраст: %d \n", name, age));
        }
        query.close();
        sqLiteDatabase.close();
    }
}