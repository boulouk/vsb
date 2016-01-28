package eu.chorevolution.vsb.bc.generators;

import java.io.File;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.impl.base.exporter.zip.ZipExporterImpl;

/* TODO
 * @see http://stackoverflow.com/questions/8522443/generate-jar-file-during-runtime
 * @see http://www.hostettler.net/blog/2012/04/05/programmatically-build-web-archives-using-shrinkwrap/
 * @see https://github.com/shrinkwrap
 * @see http://arquillian.org/guides/shrinkwrap_introduction/
 * */
public class WarGenerator {
	
	private static final String WEBAPP_SRC = "src/main/webapp";
	
	public static void main(String[] args) {
		
		WebArchive archive = ShrinkWrap.create(WebArchive.class, "test.war");
		
		archive.setWebXML(new File(WEBAPP_SRC, "WEB-INF/web.xml"));
//		archive.addClasses(eu.chorevolution.vsb.bcs.weather.bc.BindingComponent.class);
		archive.addPackage(eu.chorevolution.vsb.bcs.weather.BCStarter.class.getPackage());
		archive.addPackage(eu.chorevolution.vsb.bcs.weather.bc.BindingComponent.class.getPackage());
//		archive.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
//		archive.addAsWebResource(new File(WEBAPP_SRC, "login.xhtml"));
                                 
		//.addPackage(java.lang.Package.getPackage("eu.chorevolution.vsb.bcs.weather"))
	//.addClasses(eu.chorevolution.vsb.bcs.weather.BCStarter.class)
		
		new ZipExporterImpl(archive).exportTo(new File(archive.getName()), true);
		
	}

}
