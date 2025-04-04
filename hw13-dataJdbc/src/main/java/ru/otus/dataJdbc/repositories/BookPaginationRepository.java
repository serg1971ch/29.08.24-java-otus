package ru.otus.dataJdbc.repositories;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.otus.dataJdbc.dtos.BookDto;
import ru.otus.dataJdbc.dtos.CommentBookDto;
import ru.otus.dataJdbc.dtos.PageDtoPagination;
import ru.otus.dataJdbc.entities.Book;


import java.util.List;

@Repository
public interface BookPaginationRepository extends ListCrudRepository<Book, Long> {
    @Query("SELECT COUNT(*) FROM BOOKS")
    int countBooks();

    @Query(
            "select id, title from BOOKS"
    )
    List<Book> getAllBook();


    @Query(
            "select id, title from BOOKS where id = :id"
    )
    BookDto getBookById(Long id);

    @Query(
            "select b.title, f.size, p.count from PAGES p left join BOOKS b on b.id = p.book_id left join FORMATS f on p.page_format_id = f.id where f.id = :id"
    )
    List<PageDtoPagination> getPageByFormatId(@Param("id") long id);

    @Query(
            "select b.id, b.title, c.rate as avg_rating from BOOKS b right join COMMENTS c on b.id = c.comment_book_id where b.id = :id"
    )
    List<CommentBookDto> getBookRating(@Param("id") Long id);

    @Query(
            "select b.id, b.title  from BOOKS b right join COMMENTS c on b.id = c.comment_book_id group by b.id order by avg(c.rate) desc limit 5"
    )
    List<Book> getTop5Book();
}
