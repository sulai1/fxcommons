package fx.controll;

import java.math.BigDecimal;
import java.text.NumberFormat;

import javax.swing.text.NumberFormatter;

import javafx.scene.control.TextFormatter;
import javafx.util.converter.NumberStringConverter;

/**
 * A simple text field that allows only numbers as input. It uses a
 * {@link NumberFormatter} to format the given text. To get notified about valid
 * inserts add a
 * 
 * @author sascha
 *
 */
public class NumberField extends ObjectField<Number> {

	public NumberField() {
		this(NumberFormat.getInstance());
	}

	public NumberField(NumberFormat format) {
		super(new TextFormatter<Number>(new NumberStringConverter(format)));
	}

	public void increment() {
		BigDecimal val = new BigDecimal(getProperty().toString());
		setProperty(val.add(BigDecimal.ONE));
	}

	public void decrement() {
		BigDecimal val = new BigDecimal(getProperty().toString());
		setProperty(val.subtract(BigDecimal.ONE));
	}

	public void increment(int digit) {
		BigDecimal val = new BigDecimal(getProperty().get().toString());
		BigDecimal inc;
		inc = BigDecimal.ONE;
		if (digit < 0)
			inc = BigDecimal.ONE.divide(BigDecimal.TEN.pow(Math.abs(digit)));
		else
			inc = BigDecimal.TEN.pow(digit);
		BigDecimal res = val.add(inc);
		setProperty(res);
	}

	public void decrement(int digit) {
		BigDecimal val = new BigDecimal(getProperty().get().toString());
		BigDecimal inc;
		if (digit == 0)
			inc = BigDecimal.ONE;
		if (digit < 0)
			inc = BigDecimal.ONE.divide(BigDecimal.TEN.pow(Math.abs(digit)));
		else
			inc = BigDecimal.TEN.pow(digit);
		BigDecimal res = val.subtract(inc);
		setProperty(res);
	}

	@Override
	public Number getInitValue() {
		return 0;
	}
}
