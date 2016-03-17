package common;

import java.util.LinkedList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;

public class Form extends Dialog<List<String>>{

	private ObservableList<String> values;
	private List<String> list = new LinkedList<String>();
	private ListView<String> view;
	private Node submit;
	
	public Form() {
		values = FXCollections.observableList(list);
		// Enable/Disable submit button depending on whether a username was entered.
		ButtonType loginButtonType = new ButtonType("Submit", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
		submit = getDialogPane().lookupButton(loginButtonType);
		submit.setDisable(true);
		setResultConverter(dialogButton -> {
		    if (dialogButton == loginButtonType) {
		        return list;
		    }
		    return null;
		});
		values.add("a");
		view = new ListView<String>(values);
		getDialogPane().setContent(view);
	}
	
	public void addAttribute(String name){
		values.add(name);
	}
	
	public boolean isValid() {
		return true;
	}
	public void add(String s) {
		list.add(s);
	}
	public ObservableList<String> getValues() {
		return values;
	}

}
