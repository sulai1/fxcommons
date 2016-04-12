package fx.controll;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import fx.property.ValueSupplier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class TextSupplier implements ValueSupplier<String> {

	private HBox b = new HBox();
	private TextField textField;
	private SimpleObjectProperty<String> val;
	private CharChooser charChooser;

	public TextSupplier() {
		textField = new TextField();
		b.getChildren().add(textField);
		try {
			BufferedReader in = new BufferedReader(
					new FileReader(new File("E:\\workspace\\fxcommons\\res\\unicode.csv")));
			charChooser = new CharChooser(in);
		} catch (FileNotFoundException e) {
			charChooser = new CharChooser();
		}
		Button button = new Button("+");
		button.setOnAction(c -> {
			Form<Character> form = new Form<Character>();
			form.addField("character", charChooser);
			Callback<ButtonType, Character> value = new Callback<ButtonType, Character>() {

				@Override
				public Character call(ButtonType arg0) {
					return charChooser.getProperty().get();
				}
			};
			form.setResultConverter(value);
			form.showAndWait();
			Character result = form.getResult();
			if (result != null)
				textField.appendText("" + result);
		});
		b.getChildren().add(button);
		val = new SimpleObjectProperty<>();
		textField.textProperty().bindBidirectional(val);
	}

	@Override
	public ObjectProperty<String> getProperty() {
		return val;
	}

	@Override
	public Node getGraphic() {
		return b;
	}
	
	protected TextField getTextField() {
		return textField;
	}

}
