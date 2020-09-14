package com.d.lib.orm.sqlite.internal;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * TableStatements
 * Created by D on 2020/9/11.
 */
public class TableStatements {
    private final LinkedHashMap<String, Object> map = new LinkedHashMap<>();

    public TableStatements() {
    }

    public void put(String key, Object value) {
        if (value != null) {
            map.put(key, value);
        }
    }

    public String[] getColumns() {
        List<String> columns = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            columns.add(entry.getKey());
        }
        return columns.toArray(new String[columns.size()]);
    }

    public Object[] getBindArgs() {
        List<Object> args = new ArrayList<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            args.add(entry.getValue());
        }
        return args.toArray(new Object[args.size()]);
    }
}
