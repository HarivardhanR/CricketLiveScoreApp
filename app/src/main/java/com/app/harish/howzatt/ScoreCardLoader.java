package com.app.harish.howzatt;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class ScoreCardLoader extends AsyncTaskLoader<String> {

    private int matchId;
    ScoreCardLoader(Context context,int matchId) {
        super(context);
        this.matchId = matchId;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        return GetJSONClass.getJSON("http://cricapi.com/api/fantasySummary/?apikey=OX2vd2s4lWgA2BGaOplKJyRXwXJ2&unique_id=" + matchId);
    }
}
