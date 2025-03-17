package ru.otus.dataJdbc.services;

import ru.otus.dataJdbc.dtos.CommentBookDto;
import ru.otus.dataJdbc.dtos.PageDtoPagination;
import ru.otus.dataJdbc.entities.Book;

import java.util.List;

public interface PageService {
    List<CommentBookDto> getBookRating(Long id);
    List<PageDtoPagination> pageDtoPaginationByFormat(long id);
    List<Book> getTop5();
}
