package eu.chorevolution.vsb.gmdl.utils;

import java.util.ArrayList;
import java.util.List;

import eu.chorevolution.vsb.gmdl.utils.enums.OperationType;

public class Operation {
  
  private final String operationID;
  private final OperationType type;
  private final Scope scope;
  private List<Data<?>> getDatas = new ArrayList<Data<?>>();
  private Data<?> postData = null;
  
  public Operation (String operationID, Scope scope, OperationType type) {
    this.operationID = operationID;
    this.scope = scope;
    this.type = type;
  }
  
  public String getOperationID() {
    return operationID;
  }
  
  public String getOperationName() {
    return this.scope.getName();
  }
  
  public Scope getScope() {
    return scope;
  }
  
  public OperationType getOperationType() {
    return type;
  }
  
  public List<Data<?>> getGetDatas() {
    return getDatas;
  }
  
  public void addGetData(Data<?> data) {
    this.getDatas.add(data);
  }
  
  public Data<?> getPostData() {
    return postData;
  }
  
  public void setPostData(Data<?> data) {
    this.postData = data;
  }

  public OperationType getType() {
    return type;
  }
}