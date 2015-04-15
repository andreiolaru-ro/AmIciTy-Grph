package testing;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import amicity.data.DatasetFileReader;

public class TestDataSetLocation
{
	
	@Test
	public void test() throws IOException
	{
		DatasetFileReader dataset = new DatasetFileReader(new File("resources/adlerror/p17.t1"), 3, "\t");
		if (!dataset.hasNext()) {
			fail("dataset doesn't have next.");
		}
		while (dataset.hasNext()) {
			System.out.println(dataset.get()[0]);
		}
		//fail(!dataset.hasNext());
	}
	
}
