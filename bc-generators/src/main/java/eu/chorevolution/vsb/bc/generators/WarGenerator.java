/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.chorevolution.vsb.bc.generators;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;

/**
 * @author Georgios Bouloukakis (boulouk@gmail.com)
 * 
 * WarGenerator.java Created: 27 janv. 2016 Description:
 */
public class WarGenerator {
    private static final String WEBAPP_SRC = "D:\\inria\\code\\chorevolution\\evolution-service-bus\\bc-generators\\src\\main\\webapp";
    
    public WarGenerator () {
        
    }
    
    public void generate() {
//        File[] files = Maven.resolver().loadPomFromFile("pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile();
        File[] files = Maven.resolver().loadPomFromFile("D:\\inria\\code\\chorevolution\\evolution-service-bus\\bc-generators\\pom.xml").importRuntimeDependencies().resolve().withTransitivity().asFile();

//        WebArchive archive = ShrinkWrap.create(WebArchive.class, "D:\\inria\\code\\chorevolution\\evolution-service-bus\\bc-generators\\GBindingComponent.war");
        WebArchive archive = ShrinkWrap.create(WebArchive.class, "D:\\programs\\Apache Tomcat 8.0.27\\webapps\\GBindingComponent.war");

        archive.setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"));

        //For weather
//        archive.addPackage(eu.chorevolution.vsb.bcs.weather.BCStarter.class.getPackage());
//        archive.addPackage(eu.chorevolution.vsb.bcs.weather.bc.BindingComponent.class.getPackage());

        //For DTSGoogle
        archive.addPackage(eu.chorevolution.vsb.bcs.dtsgoogle.BCStarter.class.getPackage());
        archive.addPackage(eu.chorevolution.vsb.bcs.dtsgoogle.bc.BindingComponent.class.getPackage());

        archive.addPackage(eu.chorevolution.vsb.bc.war.BCStarterServlet.class.getPackage());
        archive.addAsLibraries(files);
        archive.addAsWebResource(new File(WEBAPP_SRC, "index.jsp"));

        for (File f : new File(WEBAPP_SRC + "\\assets\\css").listFiles()) {
                archive.addAsWebResource(f, "assets/css/" + f.getName());
        }

        for (File f : new File(WEBAPP_SRC + "\\assets\\fonts").listFiles()) {
                archive.addAsWebResource(f, "assets/fonts/" + f.getName());
        }

        for (File f : new File(WEBAPP_SRC + "\\assets\\img").listFiles()) {
                archive.addAsWebResource(f, "assets/img/" + f.getName());
        }

        for (File f : new File(WEBAPP_SRC + "\\assets\\js").listFiles()) {
                archive.addAsWebResource(f, "assets/js/" + f.getName());
        }

        new ZipExporterImpl(archive).exportTo(new File(archive.getName()), true);
    }
    
    
}
