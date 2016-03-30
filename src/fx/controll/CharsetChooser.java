package fx.controll;

import java.nio.charset.Charset;
import java.util.SortedMap;

import fx.property.ValueSupplier;
import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;

public class CharsetChooser extends ComboBox<Charset> implements ValueSupplier<Charset>{
	public CharsetChooser() {
		super();
		SortedMap<String, Charset> availableCharsets = Charset.availableCharsets();
		ObservableList<Charset> value = FXCollections.observableArrayList();
		value.addAll(availableCharsets.values());
		setItems(value);
	}

	@Override
	public Node getGraphic() {
		return this;
	}
	@Override
	public ObjectProperty<Charset> getProperty() {
		return valueProperty();
	}
	
}
