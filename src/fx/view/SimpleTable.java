package fx.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import io.CSVReader;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.util.Callback;

public class SimpleTable<T> extends TableView<T> {

	private ObservableList<T> allData;
	private List<String> names;
	private List<Callback<T, String>> cbs;
	
	public SimpleTable() {
		names = new ArrayList<>();
		cbs = new ArrayList<>();
		setEditable(true);
		getSelectionModel().selectionModeProperty().set(SelectionMode.SINGLE);
		getSelectionModel().cellSelectionEnabledProperty().set(true);
		allData = FXCollections.observableArrayList();
		setItems(allData);
	}
	
	public void addColumn(String string, Callback<T, String> cb) {
		names.add(string);
		cbs.add(cb);
		int index = getColumns().size();
		TableColumn<T, String> column = new TableColumn<>(string);

		column.setCellValueFactory(createStringValueFactory(string, index, cb));
		getColumns().add(column);
	}
	
	public void addColumn(String string, Callback<T, String> cb, Comparator<String> comp) {
		names.add(string);
		cbs.add(cb);
		int index = getColumns().size();
		TableColumn<T, String> column = new TableColumn<>(string);
		column.setComparator(comp);

		column.setCellValueFactory(createStringValueFactory(string, index, cb));
		getColumns().add(column);
	}
	
	public void store(PrintStream out){
		for(String cname: names)
			out.print(cname+';');
		out.println();
		for(T row:allData){
			for(Callback<T, String> cb:cbs){
				out.print(cb.call(row)+';');
			}
			out.println();
		}
	}

	public void load(BufferedReader in,Callback<String[], T> factory) throws IOException{
		in.readLine();
		CSVReader r = new CSVReader(new char[]{';'}, StandardCharsets.UTF_8);
		String[][] read = r.read(in);
		for (int i = 0; i < read.length; i++) {
			allData.add(factory.call(read[i]));
		}
	}
	
	private Callback<CellDataFeatures<T, String>, ObservableValue<String>> createStringValueFactory(String string,
			int index, Callback<T, String> cb) {

		Callback<CellDataFeatures<T, String>, ObservableValue<String>> factory = new Callback<TableColumn.CellDataFeatures<T, String>, ObservableValue<String>>() {

			@Override
			public ObservableValue<String> call(CellDataFeatures<T, String> param) {
				return new ReadOnlyStringWrapper(cb.call(param.getValue()));
			}
		};
		return factory;
	}

	public void addRow(T value) {
		getItems().add(value);
	}

	TableView<T> getTable(){
		return this;
	}

	public void clear() {
		allData.clear();
	}
	
}
