package fx.property;

import javafx.beans.property.ObjectProperty;
import javafx.scene.Node;

public interface ValueSupplier<T> {

	public ObjectProperty<T> getProperty();
	
	public Node getGraphic();
}
