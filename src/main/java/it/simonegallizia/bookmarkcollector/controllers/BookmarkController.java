package it.simonegallizia.bookmarkcollector.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.simonegallizia.bookmarkcollector.entities.Bookmark;
import it.simonegallizia.bookmarkcollector.services.BookmarkService;

@RestController
@RequestMapping("bookmarks")
public class BookmarkController {
    @Autowired
    private BookmarkService bookmarkService;

    @PostMapping
	public Bookmark add(@Valid @RequestBody Bookmark bookmark) {
		return bookmarkService.add(bookmark);
	}

    @GetMapping
	public List<Bookmark> list() {
		return bookmarkService.list();
	}
}
