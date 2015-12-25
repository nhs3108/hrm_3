package com.nhs3108.fhrm.models;

import java.util.ArrayList;

/**
 * Created by hongson on 23/12/2015.
 */
public interface ModelDao<T> {
    int insert(ArrayList<T> objectList);

    int update(T object);

    int update(ArrayList<T> objectList);

    int delete(int[] ids);

    int deleteAll();

    T getById(int index);

    ArrayList<T> getAll();
}
