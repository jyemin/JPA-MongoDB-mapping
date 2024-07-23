package org.hibernate.omm.jdbc.adapter;

import org.hibernate.omm.exception.NotYetImplementedException;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Nathan Xu
 * @since 1.0.0
 */
public abstract class ArrayAdapter implements Array {
    @Override
    public String getBaseTypeName() throws SQLException {
        throw new NotYetImplementedException();
    }

    @Override
    public Object getArray(Map<String, Class<?>> map) throws SQLException {
        throw new NotYetImplementedException();
    }

    @Override
    public Object getArray(long index, int count) throws SQLException {
        throw new NotYetImplementedException();
    }

    @Override
    public Object getArray(long index, int count, Map<String, Class<?>> map) throws SQLException {
        throw new NotYetImplementedException();
    }

    @Override
    public ResultSet getResultSet() throws SQLException {
        throw new NotYetImplementedException();
    }

    @Override
    public ResultSet getResultSet(Map<String, Class<?>> map) throws SQLException {
        throw new NotYetImplementedException();
    }

    @Override
    public ResultSet getResultSet(long index, int count) throws SQLException {
        throw new NotYetImplementedException();
    }

    @Override
    public ResultSet getResultSet(long index, int count, Map<String, Class<?>> map) throws SQLException {
        throw new NotYetImplementedException();
    }

    @Override
    public void free() throws SQLException {
        throw new NotYetImplementedException();
    }
}
