package eu.chorevolution.vsb.gmdl.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.chorevolution.vsb.gmdl.utils.enums.ProtocolType;

public class GmServiceRepresentation {
  private String host_address;
  private ProtocolType protocol;

  private final List<Interface> interfaces = new ArrayList<Interface>();
    private final Map<String, Operation> operations = new HashMap<String, Operation>();
  private final Map<String, Data<?>> typeDefinitions = new HashMap<String, Data<?>>();

  public void setProtocol(ProtocolType protocol) {
    this.protocol = protocol;
  }

  public List<Interface> getInterfaces() {
    return interfaces;
  }

  public String getHostAddress() {
    return host_address;
  }

  public void setHostAddress(String host_address) {
    this.host_address = host_address;
  }

  public ProtocolType getProtocol() {
    return protocol;
  }

  public void addInterface(Interface inter) {
    this.interfaces.add(inter);
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