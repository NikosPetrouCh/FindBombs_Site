package gr.aueb.cf.findbombs.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cities")
public class City extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    @Column(name = "name", unique = true, nullable = false, length = 100)
    private String name;

    @Column(name = "latitude", nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "zoom_level")
    private Integer zoomLevel = 12;

    @OneToMany(mappedBy = "city", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GraffitiPiece> graffitiPieces = new HashSet<>();

    public void addGraffitiPiece(GraffitiPiece piece) {
        graffitiPieces.add(piece);
        piece.setCity(this);
    }

    public void removeGraffitiPiece(GraffitiPiece piece) {
        graffitiPieces.remove(piece);
        piece.setCity(null);
    }
}


