package it.simonegallizia.bookmarkcollector.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
public class Bookmark {
    @Id
    @GeneratedValue
    private Long id;
    
    @JsonIgnore
    @ManyToOne
    private User user;

    @JsonIgnore
    @ManyToOne
    private Folder folder;

    @NotEmpty
    private String name;

    @NotEmpty
    private String url;
}
