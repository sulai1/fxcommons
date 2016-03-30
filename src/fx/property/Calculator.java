package fx.property;

import java.text.NumberFormat;

import fx.controll.NumberChooser;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.layout.HBox;

public class Calculator{

	private HBox box = new HBox();
	private NumberChooser left;
	private NumberChooser right;
	
	Object sender = null;
	public Calculator() {
		right = new NumberChooser(NumberFormat.getCurrencyInstance());
		left = new NumberChooser();
		box.getChildren().add(left);
		box.getChildren().add(right);

		ChangeListener<Number> r = new ChangeListener<Number>() {
			
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(sender == left)
					sender = null;
				else{
					sender = right;
					left.setNumber(newValue.doubleValue()*0.8);
				}
			}
		};

		right.getProperty().addListener(r);
		ChangeListener<Number> l = new ChangeListener<Number>() {
			
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				if(sender == right)
					sender = null;
				else{
					sender = left;
					right.setNumber(newValue.doubleValue()*1.25);
				}
			}
		};
		left.getProperty().addListener(l);
	}
	public Node getNode() {
		return box;
	}
}
