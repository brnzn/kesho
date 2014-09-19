package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.StudentDto;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/13/13
 * Time: 5:12 PM
 * To change this template use File | Settings | File Templates.
 * @deprecated
 */
public interface Selectable<T> {
    /**
     * @deprecated
      * @return
     */
 //   T getSelectedItem();

    /**
     * @deprecated
     * should use event listner for that
     */
    void refresh();
}
