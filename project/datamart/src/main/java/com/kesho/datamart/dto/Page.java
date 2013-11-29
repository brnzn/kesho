package com.kesho.datamart.dto;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/21/13
 * Time: 9:02 PM
 * To change this template use File | Settings | File Templates.
 */
public interface Page<T> {
    int getTotalPages();
    List<T> getContent();

    boolean isError();

    List<String> getErrors();

    int getSize();
}
