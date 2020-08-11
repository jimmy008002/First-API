package helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.codec.binary.Base64;

public class FileHelper {
	public boolean fileEncoder(String path, String filename, String base64) {
		try {
			System.out.println(path + filename);
			byte[] data = Base64.decodeBase64(base64);
			OutputStream stream = new FileOutputStream(path + filename);
			stream.write(data);
			stream.close();
			// Set up file privilege
			final File file = new File(path + filename);
			file.setReadable(true, false);
			file.setExecutable(true, false);
			file.setWritable(true, false);
		} catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
			return false;
        }	
		return true;
	}
	
}
