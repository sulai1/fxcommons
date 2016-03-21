package fx.node;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public abstract class ObjectField<T> extends TextField {

	private ObjectProperty<T> property = new SimpleObjectProperty<T>();
	private TextFormatter<T> formatter;


	public ObjectField(TextFormatter<T> formatter) {
		this.formatter = formatter;
		setTextFormatter(formatter);
		property.set(getInitValue());
		textProperty().addListener((o, oldVal, newVal) -> {
			if(newVal!=null)
				property.setValue(formatter.getValueConverter().fromString(getText()));
		
		});
	}

	public abstract T getInitValue();

	public void setProperty(T val) {
		textProperty().setValue(formatter.getValueConverter().toString(val));
	}

	public T getProperty() {
		return property.getValue();
	}
	
	public String getDefaultValue(){
		return formatter.getValueConverter().toString(getInitValue());
	}

	/**
	 * add a change listener to the property
	 * 
	 * @param listener
	 */
	public void setOnPropertyChange(ChangeListener<T> listener) {
		property.addListener(listener);
	}
}
