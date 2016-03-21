package fx.node;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParsePosition;

import javax.swing.text.NumberFormatter;

import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;

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
		super(new TextFormatter<Number>(new StringConverter<Number>() {

			@Override
			public Number fromString(String string) {
				ParsePosition pos = new ParsePosition(0);
				Number number = format.parse(string, pos);
				if (pos.getErrorIndex()!=-1)
					return null;
				return number;
			}

			@Override
			public String toString(Number object) {
				try {
					return format.format(object);
				} catch (IllegalArgumentException e) {
					return format.format(0);
				}
			}
		}));
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
		if (digit == 0) {
			increment();
			return;
		}
		BigDecimal val = new BigDecimal(getProperty().toString());
		BigDecimal inc;
		if (digit < 0)
			inc = BigDecimal.ONE.divide(BigDecimal.TEN.pow(Math.abs(digit)));
		else
			inc = BigDecimal.TEN.pow(digit);
		BigDecimal res = val.add(inc);
		setProperty(res);
	}

	public void decrement(int digit) {
		if (digit == 0) {
			decrement();
			return;
		}
		BigDecimal val = new BigDecimal(getProperty().toString());
		BigDecimal inc;
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
