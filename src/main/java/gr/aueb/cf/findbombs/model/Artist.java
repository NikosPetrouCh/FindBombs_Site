package gr.aueb.cf.findbombs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "artists")
public class Artist extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artist_id")
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "alias", length = 100)
    private String alias;

    @Lob
    @Column(name = "bio")
    private String bio;

    @Column(name = "social_media", length = 2000)
    private String socialMedia;

    @Column(name = "is_anonymous")
    private Boolean isAnonymous = false;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GraffitiPiece> graffitiPieces = new HashSet<>();

    public void addGraffitiPiece(GraffitiPiece piece) {
        graffitiPieces.add(piece);
        piece.setArtist(this);
    }

    public void removeGraffitiPiece(GraffitiPiece piece) {
        graffitiPieces.remove(piece);
        piece.setArtist(null);
    }
}


