package application;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringReader;
import java.text.ParseException;

import fx.view.SimpleTable;
import javafx.application.Application;
import javafx.stage.Stage;

public class TestApp2 extends Application{
		
	public static void main(String[] args) throws ParseException, IOException {
		launch(args);
	}

	@Override
	public void start(Stage arg0) throws Exception {
		SimpleTable<String[]> t = new SimpleTable<String[]>();
		t.addColumn("a", s->s[0]);
		t.addColumn("b", s->s[1]);
		t.addColumn("c", s->s[2]);
		t.addRow(new String[]{"1","2","3"});
		t.addRow(new String[]{"4","5","6"});
		t.addRow(new String[]{"7","8","9"});
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		t.store(new PrintStream(out));
		String string = new String(out.toByteArray());
		System.out.println(string);
		t.clear();
		
		t.load(new BufferedReader(new StringReader(string)), s-> s);
		t.store(System.out);
		
	}
	
}
