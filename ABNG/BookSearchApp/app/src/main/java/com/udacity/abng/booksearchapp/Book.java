package com.udacity.abng.booksearchapp;

import java.io.Serializable;

/**
 * Created by yul on 28.06.16.
 */
public class Book implements Serializable {

    //String resource ID for the default Book Name
    private String bookName = "unknown";
    //String resource ID for the Book Description
    private String authorName = "unknown";
    // Image resource ID for this Book
    private String imageUrl;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String BookName) {
        this.bookName = BookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String AuthorName) {
        this.authorName = AuthorName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String ImageUrl) {
        this.imageUrl = ImageUrl;
    }

    @Override
    public String toString() {
        return "Book{" +
                "bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", imageUrl=" + imageUrl +
                '}';
    }
}
