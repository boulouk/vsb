package eu.chorevolution.vsb.gmdl.utils;

import java.util.ArrayList;
import java.util.List;

import eu.chorevolution.vsb.gmdl.utils.Data.Context;

public class Data<T> {

  private String name;
  private String className;
  private boolean isPrimitiveType;
  private Context context;
  private final String mediaType;
  private boolean isRequired;
  private List<Data<?>> attributes;
  private T object;

  @Override
	public String toString() {
		String s = "";
		s += ("** name: " + name);
		s += ("className: " + className);
		s += ("isPrimitiveType: " + isPrimitiveType);
		s += ("context: " + context);
		s += ("mediaType: " + mediaType);
		s += ("isRequired: " + isRequired);
		s += ("attributes: " + attributes);
		s += ("object: " + (T)object) + " **";
		return s;
	}
  
  public Data(Data<T> another) {
    this.name = another.name;
    this.className = another.className;
    this.isPrimitiveType = another.isPrimitiveType;
    this.context = another.context;
    this.mediaType = another.mediaType;
    this.isRequired = another.isRequired;
    this.attributes = another.attributes;
    this.object = another.object;
  }
  
  public Data(String name, String className, boolean isPrimitiveType, String mediaType, Context context, boolean isRequired) {
    attributes = new ArrayList<Data<?>>();
    this.name = name;
    this.className = className;
    this.isPrimitiveType = isPrimitiveType;
    this.context = context;
    this.mediaType = mediaType;
    this.isRequired = isRequired;
  }

  public Data(String name, String className, boolean isPrimitiveType, T object, Context context, boolean isRequired) {
    this(name, className, isPrimitiveType, "application/json", context, isRequired);
    this.object = object;
  }

  // Alias for passing context as a String and set isRequired = true by default
  public Data(String name, String className, boolean isPrimitiveType, T object, String context) {
    this(name, className, isPrimitiveType, object, Context.valueOf(context), true);
  }

  // Alias for default context
  public Data(String name, String className, boolean isPrimitiveType, T object) {
    this(name, className, isPrimitiveType, object, Context.BODY, true);
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setIsRequired(boolean isRequired) {
    this.isRequired = isRequired;
  }

  public String getName() {
    return this.name;
  }

  public String getClassName() {
    return this.className;
  }

  public boolean isPrimitiveType() {
    return this.isPrimitiveType;
  }

  public T getObject() {
    return this.object;
  }

  public Class<?> getInnerClass() {
    return this.object.getClass();
  }

  public Context getContext() {
    return this.context;
  }

  public void addAttribute(Data<?> attribute) {
    this.attributes.add(attribute);
  }

  public List<Data<?>> getAttributes() {
    return this.attributes;
  }

  public String getMediaTypeAsString() {
    return this.mediaType;
  }

  public boolean isRequired() {
    return this.isRequired;
  }

  public enum Context {
    BODY, PATH, QUERY, FORM, HEADER;
  }

  public void setContext(Context con) {
    context = con;
  }
  
}