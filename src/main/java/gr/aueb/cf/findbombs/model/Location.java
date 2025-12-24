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
@Table(name = "locations")
public class Location extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "city_id", nullable = false)
    private City city;

    @Column(name = "name", length = 200)
    private String name;

    @Column(name = "street_address", length = 300)
    private String streetAddress;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @Lob
    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "accessibility_level")
    private AccessibilityLevel accessibilityLevel = AccessibilityLevel.PUBLIC;

    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<GraffitiPiece> graffitiPieces = new HashSet<>();

    public enum AccessibilityLevel {
        PUBLIC, PRIVATE, RESTRICTED
    }

    public void addGraffitiPiece(GraffitiPiece piece) {
        graffitiPieces.add(piece);
        piece.setLocation(this);
    }

    public void removeGraffitiPiece(GraffitiPiece piece) {
        graffitiPieces.remove(piece);
        piece.setLocation(null);
    }
}


