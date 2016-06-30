/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.chorevolution.vsb.artifact.generators;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import eu.chorevolution.vsb.bc.manager.BcManager;

public class WarGenerator {

  private WebArchive archive = null;
  private JSONObject jsonObject = null;

  public WarGenerator () {
    String configPath = BcManager.class.getClassLoader().getResource("config.json").toExternalForm().substring(5);
    JSONParser parser = new JSONParser();

    try {
      jsonObject = (JSONObject) parser.parse(new FileReader(configPath));
    } catch (IOException | ParseException e) {
      e.printStackTrace();
    }

    String warDestination = (String) jsonObject.get("warDestination");
    archive = ShrinkWrap.create(WebArchive.class, warDestination);
  }

  public void addPackage(Package pack) {
    archive.addPackage(pack);
  }

  public void addDependencyFiles(String pathToPom) {
    File[] files = Maven.resolver().loadPomFromFile(pathToPom).importRuntimeDependencies().resolve().withTransitivity().asFile();
    archive.addAsLibraries(files);
  }

  public void generate() {

    String WEBAPP_SRC = (String) jsonObject.get("webapp_src");

    archive.setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"));

    //For DTSGoogle
    //        addPackage(eu.chorevolution.vsb.bcs.dtsgoogle.BCStarter.class.getPackage());
    //        archive.addPackage(eu.chorevolution.vsb.bcs.dtsgoogle.bc.BindingComponent.class.getPackage());
    //
    //        archive.addPackage(eu.chorevolution.vsb.artifact.war.BCStarterServlet.class.getPackage());

    archive.addAsWebResource(new File(WEBAPP_SRC, "index.jsp"));

    for (File f : new File(WEBAPP_SRC + "/assets/css").listFiles()) {
      archive.addAsWebResource(f, "assets/css/" + f.getName());
    }

    for (File f : new File(WEBAPP_SRC + "/assets/fonts").listFiles()) {
      archive.addAsWebResource(f, "assets/fonts/" + f.getName());
    }

    for (File f : new File(WEBAPP_SRC + "/assets/img").listFiles()) {
      archive.addAsWebResource(f, "assets/img/" + f.getName());
    }

    for (File f : new File(WEBAPP_SRC + "/assets/js").listFiles()) {
      archive.addAsWebResource(f, "assets/js/" + f.getName());
    }

    new ZipExporterImpl(archive).exportTo(new File(archive.getName()), true);
  }

}
