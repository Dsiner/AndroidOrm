package com.d.lib.orm.sqlite.property;

public class Property {
    public int index;
    public Class<?> clz;
    public String name;
    public boolean pKey;
    public String field;

    public Property(int index, Class<?> clz, String name, boolean pKey, String field) {
        this.index = index;
        this.clz = clz;
        this.name = name;
        this.pKey = pKey;
        this.field = field;
    }
}
