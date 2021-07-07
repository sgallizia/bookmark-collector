package it.simonegallizia.bookmarkcollector.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import it.simonegallizia.bookmarkcollector.entities.Folder;
import it.simonegallizia.bookmarkcollector.entities.User;
import it.simonegallizia.bookmarkcollector.repositories.FolderRepository;

@Service
public class FolderService {
    @Autowired
    private FolderRepository folderRepository;


    public Folder add(Folder folder) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        folder.setUser(user);
        if(folderRepository.findByUserIdAndParentId(user.getId(), 4L) == null) {
            return folderRepository.save(folder);
        } else {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Bookmark already exists.");
        }

    }

    public List<Folder> list() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return folderRepository.findByUserId(user.getId());
    }

}
