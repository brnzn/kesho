package com.kesho.datamart.ui.controller;

import com.kesho.datamart.dto.FamilyDto;
import com.kesho.datamart.ui.repository.FamilyRepository;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;

/**
 */
public abstract class SelectorController<T> {

    @FXML
    protected TextField textField;

    @FXML
    protected Button okButton;

    private boolean okClicked = false;
	private Stage dialogStage;
    private AutoCompletionBinding<T> binding;
    private SimpleObjectProperty<T> selected = new SimpleObjectProperty<>();

    public abstract Collection<T> getData();

	@FXML
	protected void initialize() {
        selected.set(null);
        okClicked = false;
        okButton.setDisable(true);
        binding = TextFields.bindAutoCompletion(textField, FXCollections.observableArrayList(getData()));

        binding.setOnAutoCompleted(new EventHandler<AutoCompletionBinding.AutoCompletionEvent<T>>() {
            @Override
            public void handle(AutoCompletionBinding.AutoCompletionEvent<T> autoCompletionEvent) {
                selected.set(autoCompletionEvent.getCompletion());
            }
        });

        selected.addListener(new ChangeListener<T>() {
            @Override
            public void changed(ObservableValue<? extends T> observableValue, T dto, T dto2) {
                if(dto2 == null) {
                    okButton.setDisable(true);
                } else {
                    okButton.setDisable(false);
                }
            }
        });
    }
	
	/**
	 * Sets the stage of this dialog.
	 * @param dialogStage
	 */
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

    public T getSelected() {
       // return family.getSelected();
        return selected.getValue();
    }
	/**
	 * Called when the user clicks ok.
	 */
	@FXML
	protected void handleOk() {
        dialogStage.close();
        return;
	}
	
	/**
	 * Called when the user clicks cancel.
	 */
	@FXML
	protected void handleCancel() {
		dialogStage.close();
	}
	
}
