/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.chorevolution.vsb.artifact.generators;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import eu.chorevolution.vsb.bc.manager.BcManagerRestService;
import eu.chorevolution.vsb.bc.manager.VsbOutput;
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

	public VsbOutput generate(boolean isBusProtocolSoap) {
		String WEBAPP_SRC_BC = Constants.webapp_src_bc;
		String WEBAPP_SRC_ARTIFACT = Constants.webapp_src_artifact;
		archive.setWebXML(new File(WEBAPP_SRC_ARTIFACT, "WEB-INF" + File.separator + "web.xml"));

		archive.addAsWebResource(new File(WEBAPP_SRC_ARTIFACT, "index.jsp"));

		for (File f : new File(WEBAPP_SRC_BC + File.separator + "config").listFiles()) {
			archive.addAsWebResource(f, "config" + "/" + f.getName());
		}

		for (File f : new File(WEBAPP_SRC_ARTIFACT + File.separator + "assets" + File.separator + "css").listFiles()) {
			archive.addAsWebResource(f, "assets" + "/" + "css" + "/" + f.getName());
		}

		for (File f : new File(WEBAPP_SRC_ARTIFACT + File.separator + "assets" + File.separator + "fonts").listFiles()) {
			archive.addAsWebResource(f, "assets" + "/" + "fonts" + "/" + f.getName());
		}

		for (File f : new File(WEBAPP_SRC_ARTIFACT + File.separator + "assets" + File.separator + "img").listFiles()) {
			archive.addAsWebResource(f, "assets" + "/" + "img" + "/" + f.getName());
		}

		for (File f : new File(WEBAPP_SRC_ARTIFACT + File.separator + "assets" + File.separator + "js").listFiles()) {
			archive.addAsWebResource(f, "assets" + "/" + "js" + "/" + f.getName());
		}

		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		new ZipExporterImpl(archive).exportTo(bos);

		new ZipExporterImpl(archive).exportTo(new File(archive.getName()), true);

		VsbOutput vsbOutput = new VsbOutput();
		vsbOutput.war = bos.toByteArray();
		if(isBusProtocolSoap) {
			String path1 = new File(BcManagerRestService.class.getClassLoader().getResource("example.json").toExternalForm().substring(9)).getParentFile().getParentFile().getParentFile().getParentFile().getAbsolutePath();
			path1 += (File.separator + "config" + File.separator + "service.wsdl");
			Path path = Paths.get(path1);
			try {
				vsbOutput.wsdl = Files.readAllBytes(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return vsbOutput;
	}

}
