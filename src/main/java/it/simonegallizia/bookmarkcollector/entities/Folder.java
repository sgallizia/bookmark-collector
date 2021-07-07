package it.simonegallizia.bookmarkcollector.entities;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
public class Folder {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    private String name;

    @ManyToOne
    private Folder parent;

    @ManyToOne
    private User user;
    
}
