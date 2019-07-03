package com.app.harish.howzatt;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/*for list of matches
 *http://cricapi.com/api/matches/?apikey=OX2vd2s4lWgA2BGaOplKJyRXwXJ2
 * for score of a paricular match
 *http://cricapi.com/api/cricketScore/?apikey=OX2vd2s4lWgA2BGaOplKJyRXwXJ2&unique_id=1144520
 */
public class ScrollingActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String>, MatchAdapter.RecyclerViewClickListener
{
    //private static final String apiKey = "OX2vd2s4lWgA2BGaOplKJyRXwXJ2";
    private RecyclerView matchList;
    List<Matches> matchesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        matchList = findViewById(R.id.recycler);
        matchList.setHasFixedSize(true);
        matchList.setLayoutManager(new LinearLayoutManager(this));

        getMatches();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getMatches();
                Snackbar.make(view, "Refreshing...", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getMatches(){
        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // If the network is available, connected, and the search field
        // is not empty, start a MatchLoader AsyncTask.
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(0, null, this);

        }
    }

    @Override
    public Loader<String> onCreateLoader(int i, Bundle bundle) {
        return new MatchLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String s) {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray matchesArray = jsonObject.getJSONArray("matches");

            for (int i = 0; i < matchesArray.length(); i++) {
                Matches matches = new Matches();
                JSONObject temp = matchesArray.getJSONObject(i);
                matches.team1 = temp.getString("team-1");
                matches.team2 = temp.getString("team-2");
                matches.type = temp.getString("type");
                matches.matchStarted = temp.getBoolean("matchStarted");
                matches.matchid = temp.getInt("unique_id");
                matchesList.add(matches);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MatchAdapter matchAdapter = new MatchAdapter(matchesList,this);
        matchList.setAdapter(matchAdapter);

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {

    }

    @Override
    public void onItemClick(int position) {
        Matches matches =  matchesList.get(position);
        Intent intent = new Intent(this,ScoreActivity.class);
        intent.putExtra("team1",matches.getTeam1());
        intent.putExtra("team2",matches.getTeam2());
        intent.putExtra("matchStarted",matches.getMatchStarted());
        intent.putExtra("matchId",matches.getMatchid());
        startActivity(intent);


    }
}
