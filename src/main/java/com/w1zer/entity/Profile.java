package com.w1zer.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.*;

import static com.w1zer.constants.EntityConstants.EMAIL_LENGTH;
import static com.w1zer.constants.EntityConstants.LOGIN_LENGTH;

@SuppressWarnings("JpaDataSourceORMInspection")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Profile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = EMAIL_LENGTH, nullable = false, unique = true)
    private String email;

    @Column(length = LOGIN_LENGTH, nullable = false, unique = true)
    private String login;

    @Column(nullable = false, columnDefinition = "TEXT")
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE})
    @JoinTable(name = "profiles_roles", joinColumns = @JoinColumn(name = "id_profile"),
            inverseJoinColumns = @JoinColumn(name = "id_role"))
    @ToString.Exclude
    private Set<Role> roles = new HashSet<>();

    @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @JsonIgnore
    private List<Quote> quotes = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "liked_quotes", joinColumns = @JoinColumn(name = "id_profile"),
            inverseJoinColumns = @JoinColumn(name = "id_quote"))
    @ToString.Exclude
    @JsonIgnore
    private Set<Quote> likedQuotes = new HashSet<>();

    public void addLikedQuote(Quote quote) {
        this.likedQuotes.add(quote);
        quote.getWhoLiked().add(this);
    }

    public void removeLikedQuote(Quote quote) {
        this.likedQuotes.remove(quote);
        quote.getWhoLiked().remove(this);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ?
                ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Profile profile = (Profile) o;
        return getId() != null && Objects.equals(getId(), profile.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ?
                ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() :
                getClass().hashCode();
    }
}
