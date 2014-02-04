package com.kesho.datamart.ui.repository;

import com.kesho.datamart.dto.Page;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 2/4/14
 * Time: 7:50 AM
 * To change this template use File | Settings | File Templates.
 */
public interface Paging {
    <T> Page<T> getPage(int page, int pageSize);
}
