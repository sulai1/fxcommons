package fx.controll;

import fx.property.ValueSupplier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

public abstract class ObjectField<T> extends TextField implements ValueSupplier<T>{

	private ObjectProperty<T> property = new SimpleObjectProperty<T>();
	private TextFormatter<T> formatter;


	public ObjectField(TextFormatter<T> formatter) {
		this.formatter = formatter;
		setTextFormatter(formatter);
		property.set(getInitValue());
		textProperty().addListener((o, oldVal, newVal) -> {
			if(newVal!=null) {
				StringConverter<T> c = formatter.getValueConverter();
				T v = c.fromString(getText());
				property.setValue(v);
			}
		
		});
	}

	public abstract T getInitValue();

	public void setProperty(T val) {
		textProperty().setValue(formatter.getValueConverter().toString(val));
	}

	@Override
	public Node getGraphic() {
		return this;
	}
	public ObjectProperty<T> getProperty() {
		return property;
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
