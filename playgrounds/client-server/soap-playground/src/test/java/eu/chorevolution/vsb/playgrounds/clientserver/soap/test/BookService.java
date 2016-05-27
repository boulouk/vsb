package eu.chorevolution.vsb.playgrounds.clientserver.soap.test;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService(name="BookServiceInt", serviceName="bookServiceInt")
public class BookService implements BookServiceInt {
	
	@WebMethod()
	public String getBook(Integer id)  {
		System.out.println("getBook business logic");
		return "Book"+id;
	}
	
	@WebMethod()
  public String getAuthor(Integer id) {
    System.out.println("getAuthor business logic");
    return "Author"+id;
  }
}