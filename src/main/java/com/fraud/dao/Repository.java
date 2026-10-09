package com.fraud.dao;

import java.util.List;

public interface Repository<T> {

    void save(T object) throws Exception;

    T findById(int id) throws Exception;

    List<T> findAll() throws Exception;
}
