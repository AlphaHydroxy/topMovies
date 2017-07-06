package com.codeclan.topmovieslist;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

public class FavouritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);

        // Got shared preference with a name FAVOURITES and made it private.
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Got a string version of our favourite movie array list from shared preferences (Serialized)
        String favouriteMovies = sharedPref.getString("MyFavourites", new ArrayList<Movie>().toString());
        Log.d("FAVOURITES", favouriteMovies);

        // Use GSON to deserialze our arrayList (Convert String format back to ArrayList)
        Gson gson = new Gson();

        // Tells GSON what to convert JSON back to. in this case ArrayList
        TypeToken<ArrayList<Movie>> movieArrayList = new TypeToken<ArrayList<Movie>>(){};

        // Gets JSON data and converts to ArrayList<Movie>
        ArrayList<Movie> myFavourites = gson.fromJson(favouriteMovies, movieArrayList.getType());

        // Create a new favourite movie object from the ListView item
        Movie newFavouriteMovie = (Movie) getIntent().getSerializableExtra("movie");

        // Add to ArrayList
        if(!myFavourites.contains(newFavouriteMovie)) {
            myFavourites.add(newFavouriteMovie);
        }
        Log.d("MY_FAVOURITES", myFavourites.toString());

        SharedPreferences.Editor editor = sharedPref.edit();


        // Convert ArrayList to JSON (String object) and save to shared preferences
        editor.putString("MyFavourites", gson.toJson(myFavourites));
        editor.apply();

        Toast.makeText(this, "Movie Added", Toast.LENGTH_SHORT).show();

            TextView list = (TextView)findViewById(R.id.favourites_list);
            String movieString = "";

            for (Movie movie : myFavourites){
                movieString += movie.getTitle() + " " + movie.getYear() + "\n";
            }

            list.setText(movieString);
        }
}















