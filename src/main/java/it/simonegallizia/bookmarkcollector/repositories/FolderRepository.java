package it.simonegallizia.bookmarkcollector.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.simonegallizia.bookmarkcollector.entities.Folder;

public interface FolderRepository extends JpaRepository<Folder, Long> {
    public List<Folder> findByUserId(Long user_id);
    public List<Folder> findByUserIdAndParentId(Long user_id, Long parent_id);

}
