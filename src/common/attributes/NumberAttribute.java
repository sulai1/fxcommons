package common.attributes;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.function.UnaryOperator;

import javafx.scene.control.Control;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextFormatter.Change;

public class NumberAttribute extends FormAttribute<BigDecimal> {

	private BigDecimal number;
	private TextField tf = new TextField();
	private NumberFormat nf = NumberFormat.getInstance();

	public NumberAttribute() {
		tf.setTextFormatter(new TextFormatter<BigDecimal>(new UnaryOperator<Change>() {
			
			@Override
			public Change apply(Change c) {
				try {
					if (c.isAdded()){
						int pos = c.getCaretPosition();
						String text = c.getControlNewText();
						if(!c.getControlText().isEmpty())
							text = text.substring(0, pos)+text.substring(pos+1);
						c.setText(text);
					}else if (c.isReplaced()) 
						c.setText(c.getControlNewText());
					else if(c.isDeleted())
						return null;
					else
						return c;

					Number parse = nf.parse(c.getText());
					if (parse == null)
						return null;
					number = new BigDecimal(parse.toString());
					
				} catch (ParseException e) {
					return null;
				}
				return c;
			}
		}));

	}

	public BigDecimal getNumber() {
		return number;
	}

	public void setNumber(BigDecimal number) {
		tf.setText(nf.format(number));
		this.number = number;
	}

	public void setNumberFormat(NumberFormat nf) {
		this.nf = nf;
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
	protected BigDecimal getControllValue() {
		return number;
	}

	@Override
	protected void setControllValue(BigDecimal value) {
		tf.setText(value.toString());
	}

}
