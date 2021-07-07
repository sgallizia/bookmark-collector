package it.simonegallizia.bookmarkcollector.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.simonegallizia.bookmarkcollector.entities.Bookmark;
import it.simonegallizia.bookmarkcollector.entities.User;
import it.simonegallizia.bookmarkcollector.repositories.BookmarkRepository;

@Service
public class BookmarkService {
    @Autowired
    private BookmarkRepository bookmarkRepository;


    public Bookmark add(Bookmark bookmark) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        bookmark.setUser(user);
        if(bookmarkRepository.findByUrlAndUserId(bookmark.getUrl(), user.getId()) == null) {
            return bookmarkRepository.save(bookmark);
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Bookmark already exists.");
        }

    }

    public List<Bookmark> list() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return bookmarkRepository.findByUserId(user.getId());
    }

}
