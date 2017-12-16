package com.d.lib.orm.greendao.converter;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.sql.Date;

public class DateConverter implements PropertyConverter<Date, Long> {
    @Override
    public Date convertToEntityProperty(Long databaseValue) {
        return databaseValue != null ? new Date(databaseValue) : null;
    }

    @Override
    public Long convertToDatabaseValue(Date entityProperty) {
        return entityProperty != null ? entityProperty.getTime() : null;
    }
}
