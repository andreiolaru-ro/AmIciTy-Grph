package amicity.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.StringReader;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;

public class DatasetFileReader {
	static final int BUFFER_SIZE=4096;
	
	private File	file;
	private String	separator;
	private int	numberOfColumns;
	FileChannel fc;
	MappedByteBuffer map;
	BufferedReader reader;

	public DatasetFileReader(File file, int numberOfColumns, String separator) throws IOException {
		this.file = file;
		this.numberOfColumns = numberOfColumns;
		this.separator = separator;
		
		// TODO(catalinb): map chunks and iterate when needed
		//fc = new RandomAccessFile(file,  "r").getChannel();
		//map = fc.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
		reader = new BufferedReader(new FileReader(file));
	}
	
	public boolean hasNext() {
		try {
			return reader.ready();
		} catch(IOException e)
		{
			return false;
		}
	}
	
	public String[] get() throws IOException {
		return reader.readLine().split(separator);
	}
}
