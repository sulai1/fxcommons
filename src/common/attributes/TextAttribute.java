package common.attributes;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;

public class TextAttribute extends FormAttribute<String> {

	private TextField tf = new TextField();

	public TextInputControl getTextControll() {
		return tf;
	}

	public TextAttribute() {
		
	}
	
	public TextAttribute(String name){
		this.setName(name);
	}
	
	@Override
	public boolean isValid(String val) {
		return true;
	}

	@Override
	public Control getControll() {
		return tf;
	}


	@Override
	protected String getControllValue() {
		return tf.getText();
	}

	@Override
	protected void setControllValue(String value) {
		tf.setText(value);
	}


}
