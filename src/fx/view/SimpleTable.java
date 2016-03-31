package fx.view;

import java.util.ArrayList;
import java.util.List;

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

	private ObservableList<T> allData = FXCollections.observableArrayList();
	private List<String> names = new ArrayList<>();

	public SimpleTable() {
		setEditable(true);
		getSelectionModel().selectionModeProperty().set(SelectionMode.SINGLE);
		getSelectionModel().cellSelectionEnabledProperty().set(true);
		setItems(allData);
	}
	
	public void addColumn(String string, Callback<T, String> cb) {
		names.add(string);
		int index = getColumns().size();
		TableColumn<T, String> column = new TableColumn<>(string);

		column.setCellValueFactory(createStringValueFactory(string, index, cb));
		getColumns().add(column);
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
	
}
