package fx.controll;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import fx.property.ValueSupplier;
import io.CSVReader;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.util.Callback;
import javafx.util.Pair;

public class CharChooser extends BorderPane implements ValueSupplier<Character>{

	private static final int COL_NO = 16;

	private TableView<String[]> table = new TableView<String[]>();
	private ArrayList<NamedRange> ranges = new ArrayList<>();
	private Font font = Font.font("Consolas", FontPosture.REGULAR, 14);
	private ObjectProperty<Character> character = new SimpleObjectProperty<>();


	@Override
	public Node getGraphic() {
		return this;
	}
	@Override
	public ObjectProperty<Character> getProperty() {
		return character;
	}
	public CharChooser(BufferedReader in){
		initRanges(in);
		// create an list with default asci chars
		ObservableList<String[]> data = FXCollections.observableArrayList();
		NamedRange range = ranges.get(0);

		// Create the table
		initView();
		fill(data, range);

		// add table to the panel
		initChooser(data);
		setCenter(table);
		
	}
	public CharChooser() {
		// create an list with default asci chars
		ObservableList<String[]> data = FXCollections.observableArrayList();
		NamedRange range = ranges.get(0);

		// Create the table
		initView();
		fill(data, range);

		// add table to the panel
		initChooser(data);
		setCenter(table);
		ranges.add(new NamedRange("ASCII", 33, 126));
	}

	public void setOnChange(ChangeListener<Character> listener) {
		character.addListener(listener);
	}

	private void initChooser(ObservableList<String[]> data) {
		ObservableList<NamedRange> l = FXCollections.observableArrayList(ranges);
		ComboBox<NamedRange> box = new ComboBox<NamedRange>(l);
		box.setOnAction(a -> {
			data.clear();
			fill(data, box.getSelectionModel().getSelectedItem());
		});
		setTop(box);
	}

	private void initRanges(BufferedReader in) {
		// read unicode ranges
		CSVReader r = new CSVReader(new char[] { ',' }, StandardCharsets.UTF_8);
		try {
			String[][] read;
			read = r.read(in);
			// create list of ranges with names
			for (String[] string : read) {
				ranges.add(new NamedRange(string[2], Integer.decode(string[0]), Integer.decode(string[1])));
			}
		} catch (IOException e) {
			ranges.add(new NamedRange("ASCII", 33, 126));
		}

	}

	/**
	 * Init the view by creating the columns
	 * 
	 * @param table
	 *            to initialize
	 * @param columns
	 *            number of columns
	 */
	private void initView() {
		// create the table and columns
		for (int i = 0; i < COL_NO; i++) {
			TableColumn<String[], String> column = new TableColumn<>(Integer.toHexString(i));
			final int colNo = i;
			intiColumn(table, column, colNo);
			// define styles
			column.setPrefWidth(50);

			// add the column
			table.getColumns().add(column);
		}
		table.getSelectionModel().setCellSelectionEnabled(true);
	}

	/**
	 * Init the cell by adding the appropriate factories to create the cells
	 * 
	 * @param table
	 *            that contains the cell
	 * @param column
	 *            the column that contains the cell
	 * @param colNo
	 *            the index of the column
	 */
	private void intiColumn(TableView<String[]> table, TableColumn<String[], String> column, final int colNo) {
		// create a factory to access the cells
		column.setCellValueFactory(new Callback<CellDataFeatures<String[], String>, ObservableValue<String>>() {
			@Override
			public ObservableValue<String> call(CellDataFeatures<String[], String> p) {
				return new SimpleStringProperty((p.getValue()[colNo]));
			}
		});

		initCell(table, column);
	}

	/**
	 * Initialize the cell. Set the style and create a cell factory that makes
	 * it sensitive to changes using the default factory to create the cells
	 * 
	 * @param table
	 *            that contains the cell
	 * @param column
	 *            that contains the cell
	 */
	private void initCell(TableView<String[]> table, TableColumn<String[], String> column) {
		// create a cell factory that makes it sensitive to changes using the
		// default factory to create the cells
		Callback<TableColumn<String[], String>, TableCell<String[], String>> callback = column.getCellFactory();
		column.setCellFactory(new Callback<TableColumn<String[], String>, TableCell<String[], String>>() {

			@Override
			public TableCell<String[], String> call(TableColumn<String[], String> param) {
				// create the cell with the default factory
				TableCell<String[], String> c = callback.call(param);
				c.setFont(font);
				// listen to changes via the focus property
				c.selectedProperty().addListener(new ChangeListener<Boolean>() {
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						if (newValue){
							character.set(c.getItem().charAt(0));
						}
					}
				});
				return c;
			}

		});
	}
	/**
	 * Fill the table with the unicode characters in the given range. Each
	 * column contains
	 * 
	 * @param data
	 *            the list which will contain the data
	 * @param range
	 *            the range of the data
	 * @param columns
	 *            the columns to use
	 * @param table
	 *            the table to add the data
	 */
	private void fill(ObservableList<String[]> data, NamedRange range) {
		int i = range.lower();
		while (i <= range.upper()) {
			String[] col = new String[COL_NO];
			for (int j = 0; j < COL_NO; j++, i++) {
				char c = (char) i;
				col[j] = c + "";
			}
			data.add(col);
		}
		table.setItems(data);
	}

	static class NamedRange extends Pair<String, Pair<Integer, Integer>> {

		private static final long serialVersionUID = 1844334800163308960L;

		public NamedRange(String key, Integer lower, Integer upper) {
			super(key, new Pair<>(lower, upper));
		}

		public String name() {
			return getKey();
		}

		@Override
		public String toString() {
			return name();
		}

		public int upper() {
			return getValue().getValue();
		}

		public int lower() {
			return getValue().getKey();
		}

	}
}
