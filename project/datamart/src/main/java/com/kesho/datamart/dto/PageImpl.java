package com.kesho.datamart.dto;

import org.apache.commons.lang.StringUtils;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 11/21/13
 * Time: 10:15 PM
 * To change this template use File | Settings | File Templates.
 */
public class PageImpl<T> implements Page <T> {
    private int totalPages;
    private List<T> content;
    private String err = null;

    @Override
    public int getTotalPages() {
        return totalPages;
    }

    @Override
    public List<T> getContent() {
        return content;
    }

    @Override
    public boolean hasError() {
        return StringUtils.isNotBlank(err);
    }

    @Override
    public String getErrorMessage() {
        return err;
    }

    public PageImpl withErrorMessage(String msg) {
        this.err = msg;
        return this;
    }
}
