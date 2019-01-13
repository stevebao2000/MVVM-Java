package com.steve.MVVM.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName="node_table")
public class Node {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String email;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Node(String name, String email) {
        this.name = name;
        this.email = email;
    }


}
