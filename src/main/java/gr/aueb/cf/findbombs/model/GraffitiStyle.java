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
@Table(name = "graffiti_styles")
public class GraffitiStyle extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "style_id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;

    @Lob
    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "style", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GraffitiPiece> graffitiPieces = new HashSet<>();

    public void addGraffitiPiece(GraffitiPiece piece) {
        graffitiPieces.add(piece);
        piece.setStyle(this);
    }

    public void removeGraffitiPiece(GraffitiPiece piece) {
        graffitiPieces.remove(piece);
        piece.setStyle(null);
    }
}


