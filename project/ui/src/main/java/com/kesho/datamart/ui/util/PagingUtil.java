package com.kesho.datamart.ui.util;

import com.kesho.datamart.dto.Page;
import com.kesho.datamart.ui.repository.Paging;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.control.Pagination;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 2/4/14
 * Time: 7:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class PagingUtil {
    public static <T> Page<T> getPage(Paging paging, final int page, final int pageSize) {
        return paging.getPage(page, pageSize);
    }

    public static <T> void initPagination(final Paging paging, final ObservableList<T> model, final Pagination pagination) {
        Page p = getPage(paging, 0, 10);
        if(p != null) {
            model.addAll(p.getContent());
            pagination.setPageCount(p.getTotalPages() > 0 ? p.getTotalPages() : 1);
            pagination.currentPageIndexProperty().setValue(0);
        }

        pagination.currentPageIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                Page<T> p = getPage(paging, newValue.intValue(), 10);
                pagination.setPageCount(p.getTotalPages());
                model.clear();
                model.addAll(p.getContent());
            }
        });

    }
}
