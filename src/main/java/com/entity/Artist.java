package com.entity;

import com.annotations.Name;
import com.annotations.Verticle;

/**
 * Created by MonsterX on 16.10.2016.
 */

@Verticle
public class Artist {

    private String type;
    private String name;

    public void setType(String type) {
        this.type = type;
    }

    @Name
    public void setName(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Artist artist = (Artist) o;

        if (type != null ? !type.equals(artist.type) : artist.type != null) return false;
        return name != null ? name.equals(artist.name) : artist.name == null;

    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Artist{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
