package com.hrishikeshmishra.sb.repositories;

import com.hrishikeshmishra.sb.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by hrishikesh.mishra on 29/08/16.
 */
public interface ReadingListRepository extends JpaRepository<Book, Long> {
    List<Book> findByReader(String reader);
}
