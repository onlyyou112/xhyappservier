package com.xhy.xhyappserver.entries;

import java.util.List;

public class MovieEntry {
    private String id;
    private String picHref;
    private String movieDetailHref;
    private String title;
    private String note;
    private String actorPerson;
    private String scope;
    private List<MoviePlayListEntry> moviePlayListEntries;

    public MovieEntry() {
    }

    public MovieEntry(String picHref, String movieDetailHref, String title, String note, String actorPerson, String scope) {
        this.picHref = picHref;
        this.movieDetailHref = movieDetailHref;
        this.title = title;
        this.note = note;
        this.actorPerson = actorPerson;
        this.scope = scope;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<MoviePlayListEntry> getMoviePlayListEntries() {
        return moviePlayListEntries;
    }

    public void setMoviePlayListEntries(List<MoviePlayListEntry> moviePlayListEntries) {
        this.moviePlayListEntries = moviePlayListEntries;
    }

    public String getPicHref() {
        return picHref;
    }

    public void setPicHref(String picHref) {
        this.picHref = picHref;
    }

    public String getMovieDetailHref() {
        return movieDetailHref;
    }

    public void setMovieDetailHref(String movieDetailHref) {
        this.movieDetailHref = movieDetailHref;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getActorPerson() {
        return actorPerson;
    }

    public void setActorPerson(String actorPerson) {
        this.actorPerson = actorPerson;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    @Override
    public String toString() {
        return "MovieEntry{" +
                "picHref='" + picHref + '\'' +
                ", movieDetailHref='" + movieDetailHref + '\'' +
                ", title='" + title + '\'' +
                ", note='" + note + '\'' +
                ", actorPerson='" + actorPerson + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
