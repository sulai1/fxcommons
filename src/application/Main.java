package application;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;

import common.Form;
import common.attributes.NumberAttribute;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws ParseException {
		Form form = new Form();
		form.getDialogPane().getScene().getStylesheets().add("application/application.css");

		NumberAttribute na = new NumberAttribute();
		NumberFormat nf = NumberFormat.getCurrencyInstance();
		nf.setMaximumFractionDigits(2);
		nf.setMinimumFractionDigits(2);
		nf.setMaximumIntegerDigits(3);
		nf.setMinimumIntegerDigits(3);
		na.setNumberFormat(nf);
		na.setName("a");
		na.setNumber(BigDecimal.ZERO);
		na.initVisualRepresentation();
		form.getDialogPane().setContent(na.getBounds());
		form.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
