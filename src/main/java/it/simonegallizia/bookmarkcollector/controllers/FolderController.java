package it.simonegallizia.bookmarkcollector.controllers;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import it.simonegallizia.bookmarkcollector.entities.Folder;
import it.simonegallizia.bookmarkcollector.services.FolderService;

@RestController
@RequestMapping("folders")
public class FolderController {
    @Autowired
    private FolderService folderService;

    @PostMapping
	public Folder add(@Valid @RequestBody Folder folder) {
		return folderService.add(folder);
	}

    @GetMapping
	public List<Folder> list() {
		return folderService.list();
	}
}
