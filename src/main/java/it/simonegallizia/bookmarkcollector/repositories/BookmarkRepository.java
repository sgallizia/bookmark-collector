package it.simonegallizia.bookmarkcollector.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import it.simonegallizia.bookmarkcollector.entities.Bookmark;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    public Bookmark findByUrlAndUserId(String url, Long user_id);
    public List<Bookmark> findByUserId(Long user_id);
}
