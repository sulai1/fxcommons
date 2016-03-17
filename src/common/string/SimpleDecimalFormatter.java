package common.string;

import java.math.BigDecimal;
import java.util.function.UnaryOperator;

import javafx.scene.control.TextFormatter;

public class SimpleDecimalFormatter extends TextFormatter<BigDecimal>{
	
	static class UP implements UnaryOperator<Change>{
		
		private static final int FractionDigits = 2;
		int decPos = -1;
		
		@Override
		public Change apply(Change c) {
			int start = c.getRangeStart();
			int end = c.getRangeEnd();
			System.out.println(c.getControlText()+"->"+c.getControlNewText()+" : "+start+"-"+end+"/"+decPos);
			if(c.isAdded()){
				while(start<=c.getRangeEnd()){
					char charAt = c.getControlNewText().charAt(start);
					if(!Character.isDigit(charAt)){
						if(charAt == '.' && decPos==-1){
							decPos = start;
						}else
							return null;
					}else{
						if(decPos!=-1 && start>decPos+FractionDigits)
							return null;
					}
					start++;
				}
			}else if(c.isDeleted()){
				while(start<c.getRangeEnd()){
					char charAt = c.getControlText().charAt(start);
					System.out.println(charAt);
					if(charAt == '.')
							decPos = -1;
					start++;
				}
			}else if(c.isReplaced()){
				
			}
			return c;
		}
	};
	
	public SimpleDecimalFormatter() {
		super(new UP());
	}

}
