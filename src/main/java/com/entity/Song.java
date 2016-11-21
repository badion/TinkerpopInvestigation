package com.entity;

import com.annotations.Edge;
import com.annotations.Verticle;

/**
 * Created by Vitalii on 15.10.2016.
 */

@Verticle
public class Song {

    private String type;
    private String name;
    private Integer performances;
    private String song_type;
    private Song followed_by;
    private Song sung_by;
    private Artist written_by;

    public Song(String type, String name, Integer performances, String song_type) {
        this.type = type;
        this.name = name;
        this.performances = performances;
        this.song_type = song_type;
    }

    public Song() {

    }

    public void setType(String type) {
        this.type = type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPerformances(Integer performances) {
        this.performances = performances;
    }

    public void setSong_type(String song_type) {
        this.song_type = song_type;
    }

    public void setFollowed_by(Song followed_by) {
        this.followed_by = followed_by;
    }

    public void setSung_by(Song sung_by) {
        this.sung_by = sung_by;
    }

    public void setWritten_by(Artist written_by) {
        this.written_by = written_by;
    }

    @Edge("followed_by")
    public Song getFollowed_by() {
        return followed_by;
    }

    @Edge("sung_by")
    public Song getSung_by() {
        return sung_by;
    }

    @Edge("written_by")
    public Artist getWritten_by() {
        return written_by;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Integer getPerformances() {
        return performances;
    }

    public String getSong_type() {
        return song_type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Song song = (Song) o;

        if (type != null ? !type.equals(song.type) : song.type != null) return false;
        if (name != null ? !name.equals(song.name) : song.name != null) return false;
        if (performances != null ? !performances.equals(song.performances) : song.performances != null) return false;
        if (song_type != null ? !song_type.equals(song.song_type) : song.song_type != null) return false;
        if (followed_by != null ? !followed_by.equals(song.followed_by) : song.followed_by != null) return false;
        if (sung_by != null ? !sung_by.equals(song.sung_by) : song.sung_by != null) return false;
        return written_by != null ? written_by.equals(song.written_by) : song.written_by == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (performances != null ? performances.hashCode() : 0);
        result = 31 * result + (song_type != null ? song_type.hashCode() : 0);
        result = 31 * result + (followed_by != null ? followed_by.hashCode() : 0);
        result = 31 * result + (sung_by != null ? sung_by.hashCode() : 0);
        result = 31 * result + (written_by != null ? written_by.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Song{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", performances=" + performances +
                ", song_type='" + song_type + '\'' +
                ", followed_by=" + followed_by +
                ", sung_by=" + sung_by +
                ", written_by=" + written_by +
                '}';
    }
}
