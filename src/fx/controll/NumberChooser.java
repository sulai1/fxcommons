package fx.controll;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import fx.property.ValueSupplier;
import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class NumberChooser extends VBox implements ValueSupplier<Number>{

	private static final int prefWidth = 80;
	private double spinnerSize = 14.0;
	private int intDigits;
	private int fracDigits;
	private NumberField numberField;

	public enum Alignment {
		TOP_BOT, LEFT_RIGHT, RIGHT_LEFT, LEFT, RIGHT, TOP, BOT
	}

	@Override
	public Node getGraphic() {
		return this;
	}
	@Override
	public ObjectProperty<Number> getProperty() {
		return numberField.getProperty();
	}
	public NumberChooser() {
		this(3,2);
	}

	public NumberChooser(NumberFormat format) {
		this(format,3,2);
	}
	public NumberChooser(int maxInt, int maxFrac) {
		this(NumberFormat.getInstance(),maxInt,maxFrac);
	}


	public NumberChooser(NumberFormat format, int maxInt, int maxFrac) {
		numberField = new NumberField(format);
		numberField.setPrefWidth(prefWidth);
		intDigits = maxInt;
		fracDigits = maxFrac;
		Button[] top = new Button[intDigits + fracDigits];
		Button[] bot = new Button[intDigits + fracDigits];
		int index = 0;
		for (int i = intDigits - 1; i >= -fracDigits; i--, index++) {
			int inc = i;
			DecimalFormat format2 = new DecimalFormat();
			format2.setMaximumFractionDigits(0);
			double v;
			if (i < 0) {
				int abs = Math.abs(i);
				format2.setMaximumFractionDigits(abs);
				v = 1 / Math.pow(10, abs);
			} else if (i == 0)
				v = 1;
			else
				v = Math.pow(10, i);

			Button buttonTop = new Button("+" + format2.format(v));
			buttonTop.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			buttonTop.setPrefWidth(prefWidth);
			buttonTop.setFont(Font.font(spinnerSize));
			buttonTop.setOnAction(c -> numberField.increment(inc));
			buttonTop.setFocusTraversable(false);

			Button buttonBot = new Button("-" + format2.format(v));
			buttonBot.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
			buttonBot.setPrefWidth(prefWidth);
			buttonBot.setFont(Font.font(spinnerSize));
			buttonBot.setOnAction(c -> numberField.decrement(inc));
			buttonBot.setFocusTraversable(false);

			top[index] = buttonTop;
			bot[index] = buttonBot;
		}
		getChildren().add(allign(top));
		getChildren().add(numberField);
		getChildren().add(allign(bot));
	}

	private TilePane allign(Button[] buttons) {
		TilePane grid = new TilePane(Orientation.HORIZONTAL);
		grid.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
		grid.getChildren().addAll(buttons);
		return grid;
	}

	public void setOnPropertyChange(ChangeListener<Number> listener) {
		numberField.setOnPropertyChange(listener);
	}
	public Number getNumber() {
		return numberField.getProperty().get();
	}
	public void setNumber(Number n) {
		numberField.setProperty(n);
	}
}
