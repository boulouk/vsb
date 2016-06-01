package eu.chorevolution.vsb.playgrounds.tuplespace.semispace;

public interface ISpace {
	public String read(String template);
	public String take(String template);
	public void write(String template, String value, int leaseTime);
	public void register(String callbackIP, int callbackPort);
}
