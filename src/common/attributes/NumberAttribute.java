package common.attributes;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javafx.beans.property.ObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Control;
import javafx.scene.control.TextField;

public class NumberAttribute extends FormAttribute<BigDecimal> {

	private TextField tf = new TextField();
	private ObjectProperty<BigDecimal> value;
	private NumberFormat format = NumberFormat.getInstance();

	public NumberAttribute() {
		value.addListener(new ChangeListener<BigDecimal>() {
			@Override
			public void changed(ObservableValue<? extends BigDecimal> observable, BigDecimal oldValue,
					BigDecimal newValue) {

				String string = format.format(newValue.toString());
				tf.setText(string);
			}
		});
	}

	@Override
	public Control getControll() {
		return tf;
	}

	@Override
	public boolean isValid(BigDecimal value) {
		return true;
	}

	@Override
	public BigDecimal getControllValue() {
		return this.value.get();
	}

	@Override
	public void setControllValue(BigDecimal value) {
		this.value.set(value);
	}
}
