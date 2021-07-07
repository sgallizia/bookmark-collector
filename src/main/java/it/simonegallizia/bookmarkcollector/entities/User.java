package it.simonegallizia.bookmarkcollector.entities;

import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import it.simonegallizia.bookmarkcollector.models.ExtUser;
import lombok.Data;

@Data
@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String lastName;
    private String user;
    private String password;
    @Email(message = "Email is mandatory")
    private String email;
    @CreatedDate
    private Date created;
    @LastModifiedDate
    private Date lastModified;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Bookmark> bookmarks;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Folder> folders;


    public static User fromModel(ExtUser user) {
        User u = new User();
        u.setName(user.getName());
        u.setLastName(user.getLastName());
        u.setEmail(user.getEmail());
        u.setUser(user.getUser());
        u.setPassword(user.getPassword());
        return u;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       return new LinkedList<GrantedAuthority>();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
