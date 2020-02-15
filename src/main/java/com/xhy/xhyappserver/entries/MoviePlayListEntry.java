package com.xhy.xhyappserver.entries;

public class MoviePlayListEntry {
    private String id;
    private String movieId;
    private String title;
    private String movieDetailHref;
    private String videoSource;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMovieDetailHref() {
        return movieDetailHref;
    }

    public void setMovieDetailHref(String movieDetailHref) {
        this.movieDetailHref = movieDetailHref;
    }

    public String getVideoSource() {
        return videoSource;
    }

    public void setVideoSource(String videoSource) {
        this.videoSource = videoSource;
    }
}
