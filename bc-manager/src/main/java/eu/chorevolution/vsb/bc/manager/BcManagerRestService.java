package eu.chorevolution.vsb.bc.manager;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

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

import eu.chorevolution.vsb.gm.protocols.Manageable;

public class BcManagerRestService implements Manageable {

  private Component component;
  private Server server;  
  private Boolean serverOnline = false;

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

 public static class GetConfiguration extends ServerResource {
    @Override
    protected Representation post(Representation entity) throws ResourceException {
      String packagePath = "eu.chorevolution.vsb.bindingcomponent.generated";
      packagePath = packagePath.replace(".", File.separator);

      String generatedCodePath = "BindingComponent";
      generatedCodePath = generatedCodePath + File.separator;

      File dir = new File(".." + File.separator + "vsb-manager" + File.separator + "src" +
          File.separator + "main" + File.separator + "java" + File.separator + packagePath);


      List<File> configFiles = Arrays.asList(dir.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          return name.startsWith("config_block");
        }}));

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
        } catch (IOException e) {
          e.printStackTrace();
        }

      }

      String returnMessage = "";
      returnMessage = "Configuration Complete!";
      return new StringRepresentation(returnMessage);
    }

    @Override
    protected Representation get() throws ResourceException {
      String packagePath = "eu.chorevolution.vsb.bindingcomponent.generated";
      packagePath = packagePath.replace(".", File.separator);

      String generatedCodePath = "BindingComponent";
      generatedCodePath = generatedCodePath + File.separator;

      File dir = new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(5)).getParentFile();
      
      System.out.println(dir.getAbsolutePath());
      System.out.println(new File(".").getAbsolutePath());

      List<File> configFiles = Arrays.asList(dir.listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          return name.startsWith("config_block");
        }}));

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
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      String returnMessage = "";
      returnMessage = "Configuration Complete!";
      return new StringRepresentation(returnMessage);
    }
  }

  public static class SetConfiguration extends ServerResource {
    @Override
    protected Representation post(Representation entity) throws ResourceException {
      String packagePath = "eu.chorevolution.vsb.bindingcomponent.generated";
      packagePath = packagePath.replace(".", File.separator);

      String generatedCodePath = "BindingComponent";
      generatedCodePath = generatedCodePath + File.separator;

      File dir = new File(".." + File.separator + "vsb-manager" + File.separator + "src" +
          File.separator + "main" + File.separator + "java" + File.separator + packagePath);


      List<File> configFiles = Arrays.asList(new File(".").listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          return name.startsWith("config_block");
        }}));

      for (File file : configFiles) {

        String configTemplatePath = file.getAbsolutePath();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try {
          jsonObject = (JSONObject) parser.parse(new FileReader(configTemplatePath));
        } catch (IOException | ParseException e) {
          e.printStackTrace();
        }

        try (FileWriter output_file = new FileWriter(dir.getAbsolutePath() + File.separator + file.getName())) {
          output_file.write(jsonObject.toJSONString());
        } catch (IOException e) {
          e.printStackTrace();
        }

      }

      String returnMessage = "";
      returnMessage = "Configuration Complete!";
      return new StringRepresentation(returnMessage);
    }
    
    @Override
    protected Representation get() throws ResourceException {
      String packagePath = "eu.chorevolution.vsb.bindingcomponent.generated";
      packagePath = packagePath.replace(".", File.separator);

      String generatedCodePath = "BindingComponent";
      generatedCodePath = generatedCodePath + File.separator;

      File dir = new File(".." + File.separator + "vsb-manager" + File.separator + "src" +
          File.separator + "main" + File.separator + "java" + File.separator + packagePath);


      List<File> configFiles = Arrays.asList(new File(".").listFiles(new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name) {
          return name.startsWith("config_block");
        }}));

      for (File file : configFiles) {

        String configTemplatePath = file.getAbsolutePath();
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = null;

        try {
          jsonObject = (JSONObject) parser.parse(new FileReader(configTemplatePath));
        } catch (IOException | ParseException e) {
          e.printStackTrace();
        }

        try (FileWriter output_file = new FileWriter(dir.getAbsolutePath() + File.separator + file.getName())) {
          output_file.write(jsonObject.toJSONString());
        } catch (IOException e) {
          e.printStackTrace();
        }

      }
      
      String returnMessage = "";
      returnMessage = "Configuration Complete!";
      return new StringRepresentation(returnMessage);
    }

  }

  public static void main(String[] args) {
    BcManagerRestService bcmanager = new BcManagerRestService(2222);
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
    } catch (Exception e) {
      e.printStackTrace();
    }

  }

}
