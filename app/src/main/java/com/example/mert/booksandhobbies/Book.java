package com.example.mert.booksandhobbies;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by mert on 17.02.2017.
 */



public class Book extends Thing implements Serializable {
    private int bookId;
    private int numOfPages = 0;
    private int pagesRead = 0;

    public Book() {
        super();
        bookId = getThingId();
    }

    public int getNumOfPages() {
        return numOfPages;
    }

    public void setNumOfPages(int numOfPages) {
        this.numOfPages = numOfPages;
    }

    public int getPagesRead() {
        return pagesRead;
    }

    public void setPagesRead(int pagesRead) {
        this.pagesRead = pagesRead;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public String toString() {
        return "Book{" +
                "name=" + getName() +
                " numOfPages=" + numOfPages +
                ", pagesRead=" + pagesRead +
                '}';
    }
}
