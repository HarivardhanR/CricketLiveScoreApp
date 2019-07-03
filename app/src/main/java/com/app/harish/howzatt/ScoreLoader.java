package com.app.harish.howzatt;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class ScoreLoader extends AsyncTaskLoader<String> {

    private int matchId;
    ScoreLoader(@NonNull Context context,int matchId) {
        super(context);
        this.matchId = matchId;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {

        return GetJSONClass.getJSON("http://cricapi.com/api/cricketScore/?apikey=OX2vd2s4lWgA2BGaOplKJyRXwXJ2&unique_id="+matchId);
    }

}
