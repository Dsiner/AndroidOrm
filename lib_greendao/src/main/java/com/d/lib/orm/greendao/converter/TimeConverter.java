package com.d.lib.orm.greendao.converter;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.sql.Time;

public class TimeConverter implements PropertyConverter<Time, Long> {

    @Override
    public Time convertToEntityProperty(Long databaseValue) {
        return databaseValue != null ? new Time(databaseValue) : null;
    }

    @Override
    public Long convertToDatabaseValue(Time entityProperty) {
        return entityProperty != null ? entityProperty.getTime() : null;
    }
}
