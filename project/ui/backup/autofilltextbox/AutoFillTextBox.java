
package com.kesho.ui.control.autofilltextbox;


import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Control;
import javafx.scene.control.ListView;
//import javafx.scene.control.TextBox;
import javafx.scene.control.TextField;

/**
 * This class is main Control class which extends from Control <br>
 * and also implements basic functions of  the AutoFillTextBoxFactory<br>
 * 
 * You can easily utilize the AutoFillTextBox in your application<br>
 * 
 * e.g <br>
 * <pre>
 *      //..codes
 *      AutoFillTextBox autobox = new AutoFillTextBox("helo","prefix","dog","city");
 *      autobox.setLimit(7);
 *      //..add autobox to your scene then the output must be like this:
 * </pre>
 * Output:
 * <br> 
 *      <img src="http://blog.ngopal.com.np/wp-content/uploads/2011/07/screen.png" align="center"/>
 * <br>
 * 
 *    
 *
 * @author Narayan G. Maharjan
 * @see <a href="http://www.blog.ngopal.com.np"> Blog </a>
 * 
 */
public class AutoFillTextBox<T> extends Control implements AutoFillTextBoxFactory<T>{
    
    //==========
    //ATTRIBUTES
    //==========
    private TextField textbox;
    private ListView<T> listview;
    private ObservableList<T> data = FXCollections.observableArrayList();;   
    private boolean filterMode;
    private int limit;
    private T selected;

    public AutoFillTextBox(ObservableList<T> data) {
        init();
        this.data = data;
    }
    public AutoFillTextBox(){
        init();
    }
    
    /*=================================
     * Initialize the AutoFillTextBox *
     *================================*/
    private void init() {
        getStyleClass().setAll("autofill-text");
        textbox = new TextField();     
        listview = new ListView();    
        limit=5;
        filterMode=false;
        
        listen();

        o.addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println("==> invalidated");
            }
        });

        o.addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observableValue, T t, T t2) {
                System.out.println("==> changed: " + t2);
            }
        });
        
    }

    
    public void requestFocus(){
        super.requestFocus();
        textbox.requestFocus();
    }


//    public T getItem() {
//        System.out.println("!!! " + ObjectUtils.identityToString(listview));
//        return listview.getSelectionModel().getSelectedItem();
//    }
    
    public String getText() {        
        return textbox.getText();        
    }  
  
    public void addData(T data){
        this.data.add(data); 
        
    }
    
    
    
    /*--------------------
     * OVERRIDEN METHODS * 
     --------------------*/
    
    
    @Override
    public void setData(ObservableList<T> data){
        this.data = data;
    }    
    
    @Override
    public ObservableList<T> getData(){
        return data;
    }
    
    @Override
    public ListView<T> getListview() {
        return listview;
    }

    @Override
    public TextField getTextbox() {
        return textbox;
    }   
    

    @Override
    public void setListLimit(int limit) {
        
        this.limit=limit;
       
    }

    @Override
    public int getListLimit() {
        return limit;
    }

    @Override
    public void setFilterMode(boolean filter) {
        filterMode = filter;        
    }

    @Override
    public boolean getFilterMode() {
        return filterMode;
    }

    @Override
    public void setMinSize(double d, double d1) {
        super.setMinSize(d, d1);
        textbox.setMinSize(d, d1);
    }

    @Override
    public void setPrefSize(double d, double d1) {
        super.setPrefSize(d, d1);
        textbox.setPrefSize(d, d1);
    }

    @Override
    public void resize(double d, double d1) {
        super.resize(d, d1);
        textbox.resize(d, d1);
    }

    @Override
    public void setMaxSize(double d, double d1) {
        super.setMaxSize(d, d1);
        textbox.setMaxSize(d, d1);
        
    }

    private void listen(){
        this.prefHeightProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                textbox.setPrefHeight(t1.doubleValue());
            }
            
        }); 
        this.prefWidthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                textbox.setPrefWidth(t1.doubleValue());
            }
            
        }); 
        this.minHeightProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                textbox.setMinHeight(t1.doubleValue());
            }
            
        }); 
        this.maxHeightProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                textbox.setMaxHeight(t1.doubleValue());
            }
            
        }); 
        this.minWidthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                textbox.setMinWidth(t1.doubleValue());
            }
            
        }); 
        this.maxWidthProperty().addListener(new ChangeListener<Number>(){
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number t, Number t1) {
                textbox.setMaxWidth(t1.doubleValue());
            }
            
        }); 
    }

    public void setSelected(T selected) {
        System.out.println("selected:" + selected);
        this.selected = selected;
        o.setValue(selected);

    }

    public T getSelected() {
        return selected;
    }

    public SimpleObjectProperty<T> selected() {
        return o;
    }

    private SimpleObjectProperty<T> o = new SimpleObjectProperty<T>();
}
