package eu.chorevolution.vsb.logger;
public abstract class Logger {

    private int _logLevel = 0;

    /**
     * Error Logging
     *
     * @param tag The tag to indicate the source of the message
     * @param message The message
     */
    public abstract void e(String tag, String message);

    /**
     * Warning Logging
     *
     * @param tag The tag to indicate the source of the message
     * @param message The message
     */
    public abstract void w(String tag, String message);

    /**
     * Info Logging
     *
     * @param tag The tag to indicate the source of the message
     * @param message The message
     */
    public abstract void i(String tag, String message);

    /**
     * Debug Logging
     *
     * @param tag The tag to indicate the source of the message
     * @param message The message
     */
    public abstract void d(String tag, String message);

    /**
     * Set the log level
     *
     * @param level 0: No messages<br/>
     * 1: Just the Info<br/>
     * 2: Info - Errors<br/>
     * 3: Info - Errors - Warnings<br/>
     * 4: Info - Errors - Warnings - Debug 5: All
     *
     */
    public void setLogLevel(int level) {
        _logLevel = level;
    }

    public int getLogLevel() {
        return _logLevel;
    }
}
