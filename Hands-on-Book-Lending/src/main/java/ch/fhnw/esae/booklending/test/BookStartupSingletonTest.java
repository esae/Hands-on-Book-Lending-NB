/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.fhnw.esae.booklending.test;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import ch.fhnw.esae.booklending.business.BookEJB;
import ch.fhnw.esae.booklending.business.BookLendingEJB;
import ch.fhnw.esae.booklending.business.CustomerEJB;
import ch.fhnw.esae.booklending.domain.Address;
import ch.fhnw.esae.booklending.domain.Book;
import ch.fhnw.esae.booklending.domain.BookLending;
import ch.fhnw.esae.booklending.domain.Customer;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 *
 * @author andreas.martin
 */
@Singleton
@Startup
public class BookStartupSingletonTest {
    
    @EJB
    private CustomerEJB customerEJB;
    @EJB
    private BookEJB bookEJB;
    @EJB
    private BookLendingEJB bookLendingEJB;

    @PostConstruct
    void init() {
        try {
            shouldCreateABook();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void shouldCreateABook() throws Exception {
        Book book = new Book();
        book.setTitle("HelloNew");
        book.setPrice(12.5F);
        book.setDescription("Science fiction comedy book");
        book.setIsbn("1-84023-742-2");
        book.setNbOfPage(354);
        book.setIllustrations(false);
        book = bookEJB.createBook(book);
        System.out.println("ID should not be null: " + book.getId());
        List<Book> books = bookEJB.findBooks();
    }
    
    public void testLendBook() {
        Address address = new Address("Hauptstrasse", "Olten", "4600", "Switzerland", "Business");
        Customer customer = new Customer();
        customer.setFirstName("Andreas");
        customer.setLastName("Martin");
        customer.setEmail("andreas.martin@fhnw.ch");
        customer.setDateOfBirth(new GregorianCalendar(1983, 04, 21).getTime());
        List<Address> addresses = new ArrayList<>();
        addresses.add(address);
        customer.setAddress(addresses);
        Book book = new Book();
        book.setTitle("The Java Galaxy");
        book.setPrice(12.5F);
        book.setDescription("Science fiction comedy book");
        book.setIsbn("1-84023-742-2");
        book.setNbOfPage(354);
        book.setIllustrations(false);
        customerEJB.createCustomer(customer);
        bookEJB.createBook(book);
        
        BookLending bookLending = bookLendingEJB.lendBook(book, customer);;
        bookLendingEJB.deleteBookLending(bookLending);
    }
}
