package com.intelness.fgmsongs.beans;

/**
 * bean containing the verses of a song
 * 
 * @author McCyrille
 * @version 1.0
 */
public class Verse {

    private String strophe;

    public Verse() {
    }

    public Verse( String strophe ) {
        this.strophe = strophe;
    }

    public String getStrophe() {
        return strophe;
    }

    public void setStrophe( String strophe ) {
        this.strophe = strophe;
    }

}
