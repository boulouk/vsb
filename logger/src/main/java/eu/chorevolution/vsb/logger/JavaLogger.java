package eu.chorevolution.vsb.logger;

public class JavaLogger extends Logger{

	String space = "%-10s %-30s %-50s\n\n";

	public void i(String tag, String message) {
		if(this.getLogLevel()>1)
			System.out.printf(space, "[Info]", tag, message);
	}

	public void e(String tag, String message) {
		if(this.getLogLevel()>2)
			System.out.printf(space, "[Error]", tag, message);
	}

	public void w(String tag, String message) {
		if(this.getLogLevel()>3)
			System.out.printf(space, "[Warning]", tag, message);
	}

	public void d(String tag, String message) {
		if(this.getLogLevel()>4)
			System.out.printf(space, "[Debug]", tag, message);
	}

}
