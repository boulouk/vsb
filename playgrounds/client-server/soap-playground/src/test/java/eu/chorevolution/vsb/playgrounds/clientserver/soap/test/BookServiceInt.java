package eu.chorevolution.vsb.playgrounds.clientserver.soap.test;
import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface BookServiceInt {
  @WebMethod()
  public String getBook(Integer id);
  
  @WebMethod()
  public String getAuthor(Integer id);
}

