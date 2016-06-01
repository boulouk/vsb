package eu.chorevolution.vsb.playgrounds.tuplespace.semispace;

public class Element {
	private String template;
    private String value;
    
    public String getTemplate() {
        return this.template;
    }
    
    public Element setTemplate(String name) {
        this.template = name;
        return this;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public Element setValue(String value) {
        this.value = value;
        return this;
    }
    
    @Override
    public String toString() {
    	return "{key: "+this.getTemplate()+", value: "+this.getValue()+"}";
    }
}
