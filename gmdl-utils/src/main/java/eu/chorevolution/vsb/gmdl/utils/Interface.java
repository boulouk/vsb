package eu.chorevolution.vsb.gmdl.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.chorevolution.vsb.gmdl.utils.enums.RoleType;

public class Interface {

  private RoleType role;
  private Map<String, Operation> operations = new HashMap<String, Operation>();

  public Interface(RoleType role) {
    super();
    this.role = role;
  }

  public Interface(RoleType role, Map<String, Operation> operations) {
    super();
    this.role = role;
    this.operations = operations;
  }

  public RoleType getRole() {
    return role;
  }
  
  public void setRole(RoleType role) {
    this.role = role;
  }
  
  public Map<String, Operation> getOperations() {
    return operations;
  }
  
  public void setOperations(Map<String, Operation> operations) {
    this.operations = operations;
  }
  
  public void addOperation(Operation operation) {
    this.operations.put(operation.getOperationName(), operation);
  }
  
}
