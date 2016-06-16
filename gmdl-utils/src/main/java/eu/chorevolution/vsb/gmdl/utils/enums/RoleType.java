package eu.chorevolution.vsb.gmdl.utils.enums;

public enum RoleType {
  CLIENT("CLIENT"), 
  SERVER("SERVER");
  
  private final String name;       

  private RoleType(String s) {
      name = s;
  }

  public boolean equalsName(String otherName) {
      return (otherName == null) ? false : otherName.equals(name);
  }

  public String toString() {
     return this.name;
  }
}
