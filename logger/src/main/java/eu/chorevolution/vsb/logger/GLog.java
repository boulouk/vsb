package eu.chorevolution.vsb.logger;

public class GLog {

    public static Logger log = null;

    /**
     * @return Returns the current active logger object
     */
    public static Logger initLogger() {
        if (log == null) {
            String space = "%-10s %-30s %-50s\n";
            System.out.printf(space, "Type", "Tag", "Message");
            System.out.printf(space, "--------", "--------", "--------");
            log = new JavaLogger();
            log.setLogLevel(5);
        }
        return log;
    }

}
