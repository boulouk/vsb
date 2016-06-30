package eu.chorevolution.vsb.artifact.generators;

import java.io.File;
import java.io.FileInputStream;
 
public class ConvertToByteArray {
  public static byte[] convert(String pathToFile) {
    FileInputStream fileInputStream = null;
    File file = new File(pathToFile);
 
    byte[] fileByteArray = new byte[(int) file.length()];
 
    try {
      fileInputStream = new FileInputStream(file);
      fileInputStream.read(fileByteArray);
      fileInputStream.close();
    } catch (Exception e) {
      System.out.println("Exception" + e);
    }
    
    return fileByteArray;
  }
}