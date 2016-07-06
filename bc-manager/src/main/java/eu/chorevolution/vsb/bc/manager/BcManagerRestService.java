package eu.chorevolution.vsb.bc.manager;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.annotation.Generated;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.restlet.Component;
import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.representation.Representation;
import org.restlet.representation.StringRepresentation;
import org.restlet.resource.ResourceException;
import org.restlet.resource.ServerResource;

import eu.chorevolution.vsb.bindingcomponent.generated.GeneratedFactory;
import eu.chorevolution.vsb.gm.protocols.Manageable;

public class BcManagerRestService implements Manageable {

  private Component component;
  private Server server;  
  private Boolean serverOnline = false;

  GeneratedFactory genFac = new GeneratedFactory();
  
  public BcManagerRestService(final int port) {
    this.server = new Server(Protocol.HTTP, port);
    this.component = new Component();
    this.component.getServers().add(server);
    this.component.getDefaultHost().attach("/getconfiguration", GetConfiguration.class);
    this.component.getDefaultHost().attach("/setconfiguration", SetConfiguration.class);
    
    try {
      this.component.start();
    } catch (Exception e) {
      e.printStackTrace();
    }      
  }

  public void runBC() {
    genFac.run();
  }
  
  public void pauseBC() {
    genFac.pause();
  }
  
  public static class GetConfiguration extends ServerResource {
    @Override
    protected Representation post(Representation entity) throws ResourceException {
      File dir = new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(9)).getParentFile().getParentFile().getParentFile().getParentFile();
      File dir2 = new File(dir.getAbsolutePath() + File.separator + "config");

      List<File> configFiles = Arrays.asList(dir2.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          return name.startsWith("config_block");
        }}));

      String returnMessage = "Following files copied: \n";

      for (File file : configFiles) {
        String configTemplatePath = file.getAbsolutePath();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try {
          jsonObject = (JSONObject) parser.parse(new FileReader(configTemplatePath));
        } catch (IOException | ParseException e) {
          e.printStackTrace();
        }

        try (FileWriter output_file = new FileWriter(file.getName())) {
          output_file.write(jsonObject.toJSONString());
          returnMessage += file.getName();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      returnMessage = "Configuration Complete!";
      return new StringRepresentation(returnMessage);
    }

    @Override
    protected Representation get() throws ResourceException {
      File dir = new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(9)).getParentFile().getParentFile().getParentFile().getParentFile();
      File dir2 = new File(dir.getAbsolutePath() + File.separator + "config");

      List<File> configFiles = Arrays.asList(dir2.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          return name.startsWith("config_block");
        }}));

      String returnMessage = "Following files copied: \n";

      for (File file : configFiles) {
        String configTemplatePath = file.getAbsolutePath();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try {
          jsonObject = (JSONObject) parser.parse(new FileReader(configTemplatePath));
        } catch (IOException | ParseException e) {
          e.printStackTrace();
        }

        try (FileWriter output_file = new FileWriter(file.getName())) {
          output_file.write(jsonObject.toJSONString());
          returnMessage += file.getName();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      returnMessage = "Configuration Complete!";
      return new StringRepresentation(returnMessage);
    }
  }

  public static class SetConfiguration extends ServerResource {

    @Override
    protected Representation get() throws ResourceException {

      File dir = new File(".");

      List<File> configFiles = Arrays.asList(dir.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          return name.startsWith("config_block");
        }}));

      String returnMessage = "Following files copied: \n";

      for (File file : configFiles) {
        String configTemplatePath = file.getAbsolutePath();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try {
          jsonObject = (JSONObject) parser.parse(new FileReader(configTemplatePath));
        } catch (IOException | ParseException e) {
          e.printStackTrace();
        }

        File dir2 = new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(9)).getParentFile().getParentFile().getParentFile().getParentFile();
        File dir3 = new File(dir2.getAbsolutePath() + File.separator + "config");


        List<File> configFiles2 = Arrays.asList(dir3.listFiles(new FilenameFilter() {
          @Override
          public boolean accept(File dir, String name) {
            return name.startsWith(file.getName());
          }}));

        for (File file2 : configFiles2) {
          try (FileWriter output_file = new FileWriter(file2)) {
            System.out.println("sidq: " + file2.getAbsolutePath());
            output_file.write(jsonObject.toJSONString());
            returnMessage += file.getName();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

      }
      
      returnMessage = "Configuration Complete!";
      return new StringRepresentation(returnMessage);
    }

    @Override
    protected Representation post(Representation entity) throws ResourceException {

      File dir = new File(".");

      List<File> configFiles = Arrays.asList(dir.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          return name.startsWith("config_block");
        }}));

      String returnMessage = "Following files copied: \n";

      for (File file : configFiles) {
        String configTemplatePath = file.getAbsolutePath();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try {
          jsonObject = (JSONObject) parser.parse(new FileReader(configTemplatePath));
        } catch (IOException | ParseException e) {
          e.printStackTrace();
        }

        File dir2 = new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(9)).getParentFile().getParentFile().getParentFile().getParentFile();
        File dir3 = new File(dir2.getAbsolutePath() + File.separator + "config");


        List<File> configFiles2 = Arrays.asList(dir3.listFiles(new FilenameFilter() {
          @Override
          public boolean accept(File dir, String name) {
            return name.startsWith(file.getName());
          }}));

        for (File file2 : configFiles2) {
          try (FileWriter output_file = new FileWriter(file2)) {
            output_file.write(jsonObject.toJSONString());
            returnMessage += file.getName();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

      }

      returnMessage = "Configuration Complete!";
      return new StringRepresentation(returnMessage);
    }

  }

  @Override
  public void start() {
    try {
      this.component.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void stop() {
    try {
      this.component.stop();
      this.server.stop();
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
