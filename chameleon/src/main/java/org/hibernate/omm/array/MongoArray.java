package org.hibernate.omm.array;

import org.hibernate.omm.type.MongoSqlType;
import org.hibernate.omm.jdbc.adapter.ArrayAdapter;

import java.util.Map;

public class MongoArray implements ArrayAdapter {

    private final String baseTypeName;
    private final Object elements;

    private int baseType = MongoSqlType.UNKNOWN;

    public MongoArray(String baseTypeName, Object elements) {
        this.baseTypeName = baseTypeName;
        this.elements = elements;
    }

    public void setBaseType(Integer baseType) {
        this.baseType = baseType;
    }

    @Override
    public int getBaseType() {
        return baseType;
    }

    @Override
    public Object getArray() {
        return elements;
    }

    @Override
    public String getBaseTypeName() {
        return baseTypeName;
    }

    @Override
    public Object getArray(Map<String, Class<?>> map) {
        return elements;
    }

    @Override
    public Object getArray(long index, int count) {
        return elements;
    }

    @Override
    public Object getArray(long index, int count, Map<String, Class<?>> map) {
        return elements;
    }
}
