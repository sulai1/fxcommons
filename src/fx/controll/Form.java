package fx.controll;

import java.util.ArrayList;
import java.util.List;

import fx.property.ValueSupplier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

/**
 * The form is a dialog
 * 
 * @author sascha
 *
 * @param <S>
 * @param <T>
 */
public class Form<T> extends Dialog<T> implements ValueSupplier<T> {

	private List<Boolean> mandatory = new ArrayList<>();
	private ListView<Field<?>> view;
	private Node submit;
	private List<ValueSupplier<?>> values = new ArrayList<>();

	public Form() {
		// Enable/Disable submit button depending on whether a username was
		// entered.

		ButtonType loginButtonType = new ButtonType("Submit", ButtonData.OK_DONE);
		getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);
		submit = getDialogPane().lookupButton(loginButtonType);
		view = new ListView<Field<?>>();
		view.setFocusTraversable(false);
		setResizable(true);
		getDialogPane().setPrefWidth(400);
		getDialogPane().setContent(view);
	}

	public <V> void addField(String name, ValueSupplier<V> val, boolean bMandatory) {
		if (bMandatory) {
			int index = mandatory.size();
			mandatory.add(false);
			val.getProperty().addListener(l -> {
				mandatory.set(index, true);
				checkValid();
			});
		}
		this.values.add(val);
		view.getItems().add(new Field<>(name, val));
	}

	public <V> void addField(String name, Node n, ObjectProperty<V> property, boolean bMandatory) {
		ValueSupplier<V> val = new ValueSupplier<V>() {

			@Override
			public ObjectProperty<V> getProperty() {
				return property;
			}

			@Override
			public Node getGraphic() {
				return n;
			}
		};
		addField(name, val, bMandatory);
	}

	public <V> void addField(String name, ValueSupplier<V> val) {
		addField(name, val, false);
	}

	public <V> void addField(String name, Node n, ObjectProperty<V> property) {
		addField(name, n, property, false);
	}
	/**
	 * Disables the submit button until all the properties are set at least once
	 * 
	 * @param disable
	 */
	public void disableSubmit(boolean disable) {
		submit.setDisable(disable);
	}

	public boolean isValid() {
		return true;
	}

	public class Field<S> extends HBox {
		private boolean bValid = false;

		public Field(String name, Node n, ObjectProperty<S> property) {
			Node label = new Label(name);
			getChildren().add(label);
			getChildren().add(n);
			property.addListener(new ChangeListener<S>() {

				@Override
				public void changed(ObservableValue<? extends S> observable, S oldValue, S newValue) {
					setValid(true);
					for (Field<?> field : view.getItems()) {
						if (field.bValid == false)
							return;
					}
					disableSubmit(false);
				}
			});
		}

		public Field(String name, ValueSupplier<S> val) {
			this(name, val.getGraphic(), val.getProperty());
		}

		public Field(String name, Callback<String, String> cb) {
			Node label = new Label(name);
			getChildren().add(label);
			TextField tf = new TextField();
			getChildren().add(tf);
			tf.textProperty().addListener(new ChangeListener<String>() {

				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					setValid(true);
					cb.call(newValue);
					for (Field<?> field : view.getItems()) {
						if (field.bValid == false)
							return;
					}
					disableSubmit(false);
				}
			});
		}

		public boolean isValid() {
			return bValid;
		}

		private void setValid(boolean bValid) {
			this.bValid = bValid;
		}
	}

	private void checkValid() {
		for (Boolean b : mandatory) {
			if (!b)
				return;
		}
		disableSubmit(false);
	}

	@Override
	public ObjectProperty<T> getProperty() {
		return resultProperty();
	}

}
