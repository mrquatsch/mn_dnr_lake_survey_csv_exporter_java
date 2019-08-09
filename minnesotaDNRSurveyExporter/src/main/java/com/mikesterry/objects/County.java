package com.mikesterry.objects;

public class County {
    private final String name;
    private final String id;

    public County(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
