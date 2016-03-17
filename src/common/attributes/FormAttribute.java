package common.attributes;

import common.Form;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public abstract class FormAttribute<T> {

	private Label label = new Label();

	private boolean bValid = false;

	protected Form form;

	private HBox root;

	private T value = null;

	public FormAttribute() {
		
	}

	public T getValue(){
		return value;
	}


	public abstract Control getControll();

	public void setName(String name) {
		label.setText(name);
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public void initVisualRepresentation() {
		root = new HBox();
		root.getChildren().add(label);
		Control c = getControll();
		root.getChildren().add(c);
		c.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (newValue.equals(false)) {
				bValid = isValid(getControllValue());
				if(bValid){
					value = getControllValue();
					System.out.println(value);
				}else{
					setControllValue(value);
				}
			}
		});
	}

	/**
	 * @return true if the value is set properly
	 */
	public boolean isValid() {
		return bValid;
	}

	public String getName() {
		return label.getText();
	}

	public HBox getBounds() {
		return root;
	}
	
	public abstract boolean isValid(T value);

	protected abstract T getControllValue();
	
	protected abstract void setControllValue(T value);
}
