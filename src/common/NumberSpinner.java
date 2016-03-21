package common;

import java.text.NumberFormat;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class NumberSpinner extends BorderPane{

	private int intDigits;
	private int fracDigits;
	private NumberField numberField;

	public NumberSpinner(NumberFormat format) {
		numberField = new NumberField(format);
		intDigits = format.getMaximumIntegerDigits();
		fracDigits = format.getMaximumFractionDigits();
		HBox top = new HBox();
		HBox bot = new HBox();
		for(int i=intDigits-1;i>=-fracDigits;i--){
			int inc = i;
			
			Button buttonTop = new Button("+");
			buttonTop.setOnAction(c -> numberField.increment(inc));
			top.getChildren().add(buttonTop);
			
			Button buttonBot = new Button("-");
			buttonBot.setOnAction(c -> numberField.decrement(inc));
			bot.getChildren().add(buttonBot);
		}
		setTop(top);
		setCenter(numberField);
		setBottom(bot);
	}
	

	public void setOnPropertyChange(ChangeListener<Number> listener){
		numberField.setOnPropertyChange(listener);
	}
}
