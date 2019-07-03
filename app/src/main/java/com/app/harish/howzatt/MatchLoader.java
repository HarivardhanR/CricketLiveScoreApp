package com.app.harish.howzatt;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


public class MatchLoader extends AsyncTaskLoader<String> {

    MatchLoader(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Nullable
    @Override
    public String loadInBackground() {

        return GetJSONClass.getJSON("http://cricapi.com/api/matches/?apikey=OX2vd2s4lWgA2BGaOplKJyRXwXJ2");
    }

}

