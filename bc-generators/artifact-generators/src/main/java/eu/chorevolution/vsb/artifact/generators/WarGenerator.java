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

import eu.chorevolution.vsb.bc.manager.BcManagerRestService;
import eu.chorevolution.vsb.gmdl.utils.Constants;

public class WarGenerator {

  private WebArchive archive = null;
  private JSONObject jsonObject = null;

  public WarGenerator () {
    String configPath = Constants.configFilePath;
    String warDestination = Constants.warDestination;
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
    String WEBAPP_SRC_BC = Constants.webapp_src_bc;
    String WEBAPP_SRC_ARTIFACT = Constants.webapp_src_artifact;
    archive.setWebXML(new File(WEBAPP_SRC_ARTIFACT, "WEB-INF" + File.separator + "web.xml"));

    archive.addAsWebResource(new File(WEBAPP_SRC_ARTIFACT, "index.jsp"));

    for (File f : new File(WEBAPP_SRC_BC + File.separator + "config").listFiles()) {
      archive.addAsWebResource(f, "config" + File.separator + f.getName());
    }
    
    for (File f : new File(WEBAPP_SRC_ARTIFACT + File.separator + "assets" + File.separator + "css").listFiles()) {
      archive.addAsWebResource(f, "assets" + File.separator + "css" + File.separator + f.getName());
    }

    for (File f : new File(WEBAPP_SRC_ARTIFACT + File.separator + "assets" + File.separator + "fonts").listFiles()) {
      archive.addAsWebResource(f, "assets" + File.separator + "fonts" + File.separator + f.getName());
    }

    for (File f : new File(WEBAPP_SRC_ARTIFACT + File.separator + "assets" + File.separator + "img").listFiles()) {
      archive.addAsWebResource(f, "assets" + File.separator + "img" + File.separator + f.getName());
    }

    for (File f : new File(WEBAPP_SRC_ARTIFACT + File.separator + "assets" + File.separator + "js").listFiles()) {
      archive.addAsWebResource(f, "assets" + File.separator + "js" + File.separator + f.getName());
    }

    new ZipExporterImpl(archive).exportTo(new File(archive.getName()), true);
  }

}
