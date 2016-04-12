package io;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class CSVReader {

	private char[] fileSeparator;
	private Charset charset;

	public CSVReader(char[] fileSeparator, Charset charset) {
		this.fileSeparator = fileSeparator;
		this.charset = charset;
	}

	public String[][] read(String filename) throws IOException {
		InputStreamReader isr = new InputStreamReader(new FileInputStream(filename), charset.newDecoder());
		BufferedReader in = new BufferedReader(isr);
		return read(in);
	}


	public String[][] read(BufferedReader in) throws IOException {
		ArrayList<String[]> table = new ArrayList<>();
		// check for exceptions while reading
		try {
			// read all rows
			String line = in.readLine();
			if (line!=null){
				int columns = getColumns(line);
				while (line!=null) {
					ArrayList<Integer> indices = getSeperators(line);
					String[] elements = new String[columns];
					int start = 0;
					// read the columns of a row
					int i = 0;
					for (; i < indices.size(); i++) {
						Integer end = indices.get(i);
						elements[i] = line.substring(start, end);
						start = end+1;
					}
					if(start!=line.length())
						elements[i] = line.substring(start);
					//add Elements to table and read new line
					table.add(elements);
					if(in.ready())
						line = in.readLine();
					else
						line = null;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// close the reader in every cases
			in.close();
		}
		return table.toArray(new String[table.size()][]);
	}
	private int getColumns(String line) {
		return getSeperators(line).size()+1;
	}

	private ArrayList<Integer> getSeperators(String line) {
		ArrayList<Integer> indices = new ArrayList<>();
		for (int i = 0; i < fileSeparator.length; i++) {
			char c = fileSeparator[i];
			int index = line.indexOf(c);
			while (index != -1) {
				indices.add(index);
				index = line.indexOf(c, index + 1);
			}
		}
		indices.sort((a, b) -> Integer.compare(a, b));
		return indices;
	}

}
