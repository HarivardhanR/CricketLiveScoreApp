package com.app.harish.howzatt;

public class Matches {
    String team1;
    String team2;
    String type;
    Boolean matchStarted;
    int matchid;

    int getMatchid() {
        return matchid;
    }

    void setMatchid(int matchid) {
        this.matchid = matchid;
    }

    Matches(){

    }

    String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    Boolean getMatchStarted() {
        return matchStarted;
    }

    void setMatchStarted(Boolean matchStarted) {
        this.matchStarted = matchStarted;
    }

     Matches(String team1, String team2, String type, Boolean matchStarted,int matchid) {
        this.team1 = team1;
        this.team2 = team2;
        this.type = type;
        this.matchStarted = matchStarted;
        this.matchid = matchid;
    }

    String getTeam1() {
        return team1;
    }

    void setTeam1(String team1) {
        this.team1 = team1;
    }

    String getTeam2() {
        return team2;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }
}
