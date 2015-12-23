package eu.chorevolution.vsb.gmdl.utils;

import java.util.ArrayList;
import java.util.List;

public class Data<T> {
  
  private final String name;
  private final String className;
  private final boolean isPrimitiveType;
  private final Context context;
  private final String mediaType;
  private final boolean isRequired;
  private final List<Data<?>> attributes = new ArrayList<Data<?>>();
  private T object;
  
  public Data(String name, String className, boolean isPrimitiveType, String mediaType, Context context, boolean isRequired) {
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
}