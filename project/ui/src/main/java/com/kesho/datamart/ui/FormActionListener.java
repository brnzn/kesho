package com.kesho.datamart.ui;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 2/14/14
 * Time: 10:48 PM
 * To change this template use File | Settings | File Templates.
 */
public interface FormActionListener {
    void newFired();
    void deleteFired(Long id);
}
