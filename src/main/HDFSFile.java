package main;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

public class HDFSFile {
	public static void deletefile(final FileSystem filesystem,String path)
				throws IOException {
		filesystem.delete(new Path(path), true);
	}
	
	public static void downloadData(final FSDataInputStream input)
			throws IOException {
		IOUtils.copyBytes(input, System.out, input.available(), true);
	}
	
	public static void uploadData(final FileSystem filesystem ,final String path, final byte[] bys) 
			throws IOException {
		ByteArrayInputStream in = new ByteArrayInputStream(bys);
		final FSDataOutputStream out = filesystem.create(new Path(path));
		IOUtils.copyBytes(in, out, bys.length, true);
	}
	
	public static void mkdir(final FileSystem filesystem,String dir) throws IOException {
		filesystem.mkdirs(new Path(dir));
	}
}
