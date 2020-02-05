package com.mikesterry.objects;

public class County {
    private final String name;
    private final int id;

    public County(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj.getClass().equals(County.class)) {
            County county = (County) obj;
            return county.getId() == this.id &
                    county.getName().equals(this.name);
        }
        return false;
    }
}
