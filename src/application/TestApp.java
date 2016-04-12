package application;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;

import fx.controll.Form;
import fx.controll.NumberChooser;
import fx.property.Calculator;
import fx.property.Person;
import fx.property.ValueSupplier;
import fx.view.SimpleTable;
import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TestApp extends Application {
	
	@Override
	public void start(Stage primaryStage) throws ParseException, IOException {
		Dialog<Object> dialog = new Dialog<>();
		dialog.getDialogPane().setContent(new Calculator().getNode());
		dialog.showAndWait();

		Form<Person> form = new Form<>();
		form.getDialogPane().getScene().getStylesheets().add("application/application.css");
		form.setResizable(true);
		form.disableSubmit(true);
		
		NumberChooser id = new NumberChooser();
		form.addField("id", id);
		TextField name = new TextField();
		TextField surname = new TextField();
		form.addField("name", new ValueSupplier<String>() {

			@Override
			public ObjectProperty<String> getProperty() {
				ObjectProperty<String> res = new SimpleObjectProperty<>();
				res.bind(name.textProperty());
				return res ;
			}

			@Override
			public Node getGraphic() {
				return name;
			}
		});
		form.addField("surname", new ValueSupplier<String>() {

			@Override
			public ObjectProperty<String> getProperty() {
				ObjectProperty<String> res = new SimpleObjectProperty<>();
				res.bind(surname.textProperty());
				return res ;
			}

			@Override
			public Node getGraphic() {
				return surname;
			}
		});
		
		DatePicker datePicker = new DatePicker();
		ValueSupplier<LocalDate> vs = new ValueSupplier<LocalDate>() {

			@Override
			public ObjectProperty<LocalDate> getProperty() {
				return datePicker.valueProperty();
			}

			@Override
			public Node getGraphic() {
				return datePicker;
			}
		};
		form.setResultConverter(cp -> new Person(id.getNumber().intValue(), name.getText(), surname.getText(), datePicker.getValue()));
		form.addField("date", vs);
		form.showAndWait();
		System.out.println(form.getResult());
		form.getDialogPane().requestLayout();;
	}

	private Node getTable() {
		Person p = new Person(0, "sad", "safa", LocalDate.now());
		SimpleTable<Person> table = new SimpleTable<>();

		table.addColumn("id", cb -> "" + cb.getId());
		table.addColumn("name", cb -> "" + cb.getName());
		table.addColumn("surname", cb -> "" + cb.getSurname());
		table.addColumn("date", cb -> "" + cb.getTimeStamp());

		table.getItems().add(p);
		return table;
	}

	private NumberChooser getSpinner() {
		DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance();
		nf.setNegativePrefix(nf.getPositivePrefix() + "-");
		nf.setNegativeSuffix("");
		NumberChooser spinner = new NumberChooser(nf);
		spinner.setOnPropertyChange((o, old, newVal) -> {
			System.out.println(newVal);
		});
		return spinner;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
