package com.app.harish.howzatt;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScoreActivity extends AppCompatActivity {

    private int matchId;
    private TextView score;
    private LoaderManager.LoaderCallbacks<String> scoreLoader;
    private LoaderManager.LoaderCallbacks<String> scoreCardLoader;

    LinearLayout batsmanLinearLayout;
    LinearLayout runsLineatLayout;
    LinearLayout ballsLinearLayout;
    LinearLayout foursLinearLayout;
    LinearLayout sixesLinearLayout;
    LinearLayout srLinearLayout;

    LinearLayout bowlerLinearLayout;
    LinearLayout oversLinearLayout;
    LinearLayout maidensLinearLayout;
    LinearLayout brunsLinearLayout;
    LinearLayout wicketsLinearLayout;
    LinearLayout econLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);

        Bundle extras = getIntent().getExtras();
        TextView textView1 = findViewById(R.id.team1);
        textView1.setText(extras.getString("team1"));
        TextView textView2 = findViewById(R.id.team2);
        textView2.setText(extras.getString("team2"));
        matchId = extras.getInt("matchId");
        score = findViewById(R.id.score);


        batsmanLinearLayout = findViewById(R.id.BatsmanLinearLayout);
        runsLineatLayout = findViewById(R.id.runsLinearLayout);
        ballsLinearLayout = findViewById(R.id.ballsLinearLayout);
        foursLinearLayout = findViewById(R.id.foursLinearLayout);
        sixesLinearLayout = findViewById(R.id.sixesLinearLayout);
        srLinearLayout = findViewById(R.id.srLinearLayout);

        bowlerLinearLayout = findViewById(R.id.bowlersLinearLayout);
        oversLinearLayout = findViewById(R.id.oversLinearLayout);
        maidensLinearLayout = findViewById(R.id.maidensLinearLayout);
        brunsLinearLayout = findViewById(R.id.brunsLinearLayout);
        wicketsLinearLayout = findViewById(R.id.wicketsLinearLayout);
        econLinearLayout = findViewById(R.id.econLinearLayout);

        scoreLoader = new LoaderManager.LoaderCallbacks<String>() {
            @Override
            public Loader<String> onCreateLoader(int i, Bundle bundle) {
                return new ScoreLoader(getApplicationContext(),matchId);
            }

            @Override
            public void onLoadFinished(Loader<String> loader, String s) {

                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if(jsonObject.getBoolean("matchStarted") && jsonObject.getString("score")!=null){
                        score.setText(jsonObject.getString("score"));
                    }else{
                        score.setText(R.string.Matchnotstarted);
                        CardView c1 = findViewById(R.id.batsmenCard);
                        CardView c2 = findViewById(R.id.bowlersCard);
                        c1.setVisibility(View.GONE);
                        c2.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoaderReset(Loader<String> loader) {

            }

        };

        scoreCardLoader = new LoaderManager.LoaderCallbacks<String>() {
            @Override
            public Loader<String> onCreateLoader(int i, Bundle bundle) {
                return new ScoreCardLoader(getApplicationContext(),matchId);
            }

            @Override
            public void onLoadFinished(Loader<String> loader, String s) {

                try {

                    JSONObject jsonObject = new JSONObject(s);
                    JSONObject dataJsonObject = jsonObject.getJSONObject("data");
                    JSONArray jsonArray = dataJsonObject.getJSONArray("batting");
                    JSONArray jsonArray1 = dataJsonObject.getJSONArray("bowling");
                    for(int i = 0;i<jsonArray.length();i++){
                        JSONObject temp = jsonArray.getJSONObject(i);
                        JSONArray scores = temp.getJSONArray("scores");

                        TextView title  = new TextView(getApplicationContext());
                        title.setText(temp.getString("title"));
                        title.setAllCaps(true);
                        title.setMaxLines(1);
                        title.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
                        batsmanLinearLayout.addView(title);

                        runsLineatLayout.addView(new TextView(getApplicationContext()));
                        ballsLinearLayout.addView(new TextView(getApplicationContext()));
                        foursLinearLayout.addView(new TextView(getApplicationContext()));
                        sixesLinearLayout.addView(new TextView(getApplicationContext()));
                        srLinearLayout.addView(new TextView(getApplicationContext()));


                        for (int j = 0;j<scores.length()-1;j++){
                            JSONObject batsmenDetails = scores.getJSONObject(j);

                            TextView batsmanRowView = new TextView(getApplicationContext());
                            batsmanRowView.setMaxLines(1);
                            batsmanRowView.setText(batsmenDetails.getString("batsman"));
                            batsmanLinearLayout.addView(batsmanRowView);

                            TextView ballsRowView = new TextView(getApplicationContext());
                            ballsRowView.setText(String.valueOf(batsmenDetails.getInt("B")));
                            ballsLinearLayout.addView(ballsRowView);

                            TextView sixesRowView = new TextView(getApplicationContext());
                            sixesRowView.setText(String.valueOf(batsmenDetails.getInt("6s")));
                            sixesLinearLayout.addView(sixesRowView);

                            TextView srsRowView = new TextView(getApplicationContext());
                            srsRowView.setText(String.valueOf(batsmenDetails.getInt("SR")));
                            srLinearLayout.addView(srsRowView);

                            TextView foursRowView = new TextView(getApplicationContext());
                            foursRowView.setText(String.valueOf(batsmenDetails.getInt("4s")));
                            foursLinearLayout.addView(foursRowView);

                            TextView runsRowView = new TextView(getApplicationContext());
                            runsRowView.setText(String.valueOf(batsmenDetails.getInt("R")));
                            runsLineatLayout.addView(runsRowView);

                        }
                    }

                    for (int i = 0;i < jsonArray1.length();i++){
                        JSONObject temp = jsonArray1.getJSONObject(i);
                        JSONArray scores = temp.getJSONArray("scores");


                        for (int j = 0;j<scores.length();j++){
                            JSONObject bowlersDetails = scores.getJSONObject(j);

                            TextView bowlerRowView = new TextView(getApplicationContext());
                            bowlerRowView.setMaxLines(1);
                            bowlerRowView.setText(bowlersDetails.getString("bowler"));
                            bowlerLinearLayout.addView(bowlerRowView);

                            TextView oversRowView = new TextView(getApplicationContext());
                            oversRowView.setText(bowlersDetails.getString("O"));
                            oversLinearLayout.addView(oversRowView);

                            TextView maidensRowView = new TextView(getApplicationContext());
                            maidensRowView.setText(bowlersDetails.getString("M"));
                            maidensLinearLayout.addView(maidensRowView);

                            TextView brsRowView = new TextView(getApplicationContext());
                            brsRowView.setText(bowlersDetails.getString("R"));
                            brunsLinearLayout.addView(brsRowView);

                            TextView wicketsRowView = new TextView(getApplicationContext());
                            wicketsRowView.setText(bowlersDetails.getString("W"));
                            wicketsLinearLayout.addView(wicketsRowView);

                            TextView econsRowView = new TextView(getApplicationContext());
                            econsRowView.setText(bowlersDetails.getString("Econ"));
                            econLinearLayout.addView(econsRowView);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onLoaderReset(Loader<String> loader) {

            }
        };

        getScore();
    }

    private void getScore(){
        // Check the status of the network connection.
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }

        // If the network is available, connected, and the search field
        // is not empty, start a AsyncTask.
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(0, null, scoreLoader);
            getLoaderManager().initLoader(1, null, scoreCardLoader);
        }
    }
}
