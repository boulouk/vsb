package eu.chorevolution.vsb.artifact.generators;

import eu.chorevolution.vsb.gm.protocols.generators.BcSubcomponentGenerator;

/* TODO
 * @see http://stackoverflow.com/questions/8522443/generate-jar-file-during-runtime
 * @see http://www.hostettler.net/blog/2012/04/05/programmatically-build-web-archives-using-shrinkwrap/
 * @see https://github.com/shrinkwrap
 * @see http://arquillian.org/guides/shrinkwrap_introduction/
 * */
public class JarGenerator {

  public static void generateBc(BcSubcomponentGenerator componentGenerator) {
    componentGenerator.generateBc();
    // TODO: package as an executable jar
  }
}