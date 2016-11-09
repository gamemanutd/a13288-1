package com.egco428.a13288;

/**
 * Created by Thammarit on 9/11/2559.
 */
public class Fortune {

    private long id;
    private String fortune;
    private String time;
    private String prime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFortune() {
        return fortune;
    }

    public void setFortune(String fortune) {
        this.fortune = fortune;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {this.time = time;}

    public String getPrime() {
        return prime;
    }

    public void setPrime(String prime) {this.prime = prime;}


    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        return fortune;
    }
}
