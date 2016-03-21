package application;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

import common.Form;
import common.NumberSpinner;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws ParseException {
		Form form = new Form();
		form.getDialogPane().getScene().getStylesheets().add("application/application.css");

		DecimalFormat nf = (DecimalFormat) NumberFormat.getCurrencyInstance();
		nf.setMinimumIntegerDigits(2);
		nf.setMinimumFractionDigits(1);
		nf.setMaximumFractionDigits(2);
		nf.setMaximumIntegerDigits(3);
		nf.setNegativePrefix(nf.getPositivePrefix()+"-");
		nf.setNegativeSuffix("");
		NumberSpinner spinner = new NumberSpinner(nf);
		spinner.setOnPropertyChange((o , old, newVal)->{
			System.out.println(newVal);
			});
		form.getDialogPane().setContent(spinner);
		form.showAndWait();
	}

	public static void main(String[] args) {
		launch(args);
	}
}
