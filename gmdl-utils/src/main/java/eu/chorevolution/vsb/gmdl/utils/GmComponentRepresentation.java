package eu.chorevolution.vsb.gmdl.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import eu.chorevolution.vsb.gmdl.utils.enums.Protocol;

public class GmComponentRepresentation {
  private Protocol protocol;
  private final Map<String, Operation> operations = new HashMap<String, Operation>();
  private final Map<String, Data<?>> typeDefinitions = new HashMap<String, Data<?>>();
  
  public void setProtocol(Protocol protocol) {
    this.protocol = protocol;
  }
  
  public Protocol getProtocol() {
    return protocol;
  }
  
  public void addOperation(Operation operation) {
    this.operations.put(operation.getOperationName(), operation);
  }
  
  public Collection<Operation> getOperations() {
    return this.operations.values();
  }
  
  public Operation getOperation(String operationName) {
    return this.operations.get(operationName);
  }
  
  public void addTypeDefinition(Data<?> typeDefinition) {
    this.typeDefinitions.put(typeDefinition.getClassName(), typeDefinition);
  }
  
  public Collection<Data<?>> getTypeDefinitions() {
    return this.typeDefinitions.values();
  }
  
  public Data<?> getTypeDefinition(String typeName) {
    return this.typeDefinitions.get(typeName);
  }
}