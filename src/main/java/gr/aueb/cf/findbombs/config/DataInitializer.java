package gr.aueb.cf.findbombs.config;

import org.springframework.context.annotation.DependsOn;
import gr.aueb.cf.findbombs.model.*;
import gr.aueb.cf.findbombs.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
@Profile("dev")
@DependsOn("entityManagerFactory")
public class DataInitializer implements CommandLineRunner {

    private final CityRepository cityRepository;
    private final GraffitiStyleRepository styleRepository;
    private final GraffitiPieceRepository graffitiPieceRepository;
    private final ArtistRepository artistRepository;
    private final LocationRepository locationRepository;

    @Override
    @Transactional
    public void run(String... args) {
        if (cityRepository.count() > 0) {
            log.info("Data already initialized, skipping...");
            return;
        }

        log.info("Initializing sample data...");

        // Create Cities
        City athens = createCity("Athens", new BigDecimal("37.9838"), new BigDecimal("23.7275"), 12);
        City thessaloniki = createCity("Thessaloniki", new BigDecimal("40.6401"), new BigDecimal("22.9444"), 12);
        City iraklion = createCity("Iraklion", new BigDecimal("35.3387"), new BigDecimal("25.1442"), 12);

        // Create Graffiti Styles
        GraffitiStyle wildstyle = createStyle("Wildstyle", "Complex, interlocking letterforms with decorative elements");
        GraffitiStyle tag = createStyle("Tag", "Simple, quick signatures or names");
        GraffitiStyle bomb = createStyle("Bomb", "Large, bold lettering often done quickly");
        GraffitiStyle mural = createStyle("Mural", "Large-scale artistic paintings on walls");

        // Create Artists
        Artist artist1 = createArtist("Street Artist One", "SA1", false);
        Artist artist2 = createArtist("Urban Creator", "UC", false);
        Artist anonymous = createArtist("Anonymous", null, true);

        // Create Locations
        Location athensLocation1 = createLocation(athens, "Ermou Street", "Ermou Street, Athens", 
            new BigDecimal("37.9850"), new BigDecimal("23.7300"));
        Location athensLocation2 = createLocation(athens, "Kolonaki Square", "Kolonaki Square, Athens",
            new BigDecimal("37.9815"), new BigDecimal("23.7250"));
        Location thessalonikiLocation1 = createLocation(thessaloniki, "Aristotelous Square", "Aristotelous Square, Thessaloniki",
            new BigDecimal("40.6450"), new BigDecimal("22.9400"));
        Location iraklionLocation1 = createLocation(iraklion, "Knossos Avenue", "Knossos Avenue, Iraklion",
            new BigDecimal("35.3400"), new BigDecimal("25.1450"));

        // Create Graffiti Pieces for Athens
        createGraffitiPiece("Athens Wildstyle Masterpiece", "Amazing wildstyle piece in the heart of Athens", 
            athens, athensLocation1, artist1, wildstyle, "images/Aimages/Aimage1.jpg", LocalDate.now().minusDays(10));
        createGraffitiPiece("Athens Tag Collection", "Collection of tags from local artists", 
            athens, athensLocation2, artist2, tag, "images/Aimages/Aimage2.jpg", LocalDate.now().minusDays(5));
        createGraffitiPiece("Athens Mural Art", "Beautiful mural showcasing urban culture", 
            athens, athensLocation1, anonymous, mural, "images/Aimages/Aimage3.jpg", LocalDate.now().minusDays(2));

        // Create Graffiti Pieces for Thessaloniki
        createGraffitiPiece("Thessaloniki Street Art", "Vibrant street art in Thessaloniki", 
            thessaloniki, thessalonikiLocation1, artist1, wildstyle, "images/TImages/Timage1.jpg", LocalDate.now().minusDays(8));
        createGraffitiPiece("Thessaloniki Bomb", "Bold bomb style graffiti", 
            thessaloniki, thessalonikiLocation1, artist2, bomb, "images/TImages/Timage2.jpg", LocalDate.now().minusDays(3));
        createGraffitiPiece("Thessaloniki Mural", "Large scale mural art", 
            thessaloniki, thessalonikiLocation1, anonymous, mural, "images/TImages/Timage3.jpg", LocalDate.now().minusDays(1));

        // Create Graffiti Pieces for Iraklion
        createGraffitiPiece("Iraklion Wildstyle", "Complex wildstyle piece in Iraklion", 
            iraklion, iraklionLocation1, artist1, wildstyle, "images/IImages/Iimage1.jpg", LocalDate.now().minusDays(7));
        createGraffitiPiece("Iraklion Tag", "Simple tag from local artist", 
            iraklion, iraklionLocation1, artist2, tag, "images/IImages/Iimage2.jpg", LocalDate.now().minusDays(4));
        createGraffitiPiece("Iraklion Bomb", "Bold bomb style in Iraklion", 
            iraklion, iraklionLocation1, anonymous, bomb, "images/IImages/Iimage3.jpg", LocalDate.now().minusDays(1));

        log.info("Sample data initialized successfully!");
    }

    private City createCity(String name, BigDecimal lat, BigDecimal lng, Integer zoom) {
        City city = new City();
        city.setName(name);
        city.setLatitude(lat);
        city.setLongitude(lng);
        city.setZoomLevel(zoom);
        return cityRepository.save(city);
    }

    private GraffitiStyle createStyle(String name, String description) {
        GraffitiStyle style = new GraffitiStyle();
        style.setName(name);
        style.setDescription(description);
        return styleRepository.save(style);
    }

    private Artist createArtist(String name, String alias, Boolean anonymous) {
        Artist artist = new Artist();
        artist.setName(name);
        artist.setAlias(alias);
        artist.setIsAnonymous(anonymous);
        return artistRepository.save(artist);
    }

    private Location createLocation(City city, String name, String address, BigDecimal lat, BigDecimal lng) {
        Location location = new Location();
        location.setCity(city);
        location.setName(name);
        location.setStreetAddress(address);
        location.setLatitude(lat);
        location.setLongitude(lng);
        location.setAccessibilityLevel(Location.AccessibilityLevel.PUBLIC);
        return locationRepository.save(location);
    }

    private void createGraffitiPiece(String title, String description, City city, Location location, 
                                    Artist artist, GraffitiStyle style, String imageUrl, LocalDate graffitiDate) {
        try {
            GraffitiPiece piece = new GraffitiPiece();
            piece.setTitle(title);
            piece.setDescription(description);
            piece.setCity(city);
            piece.setLocation(location);
            piece.setArtist(artist);
            piece.setStyle(style);
            piece.setImageUrl(imageUrl);
            piece.setGraffitiDate(graffitiDate);
            piece.setIsActive(true);
            piece.setRating(new BigDecimal("4.5"));
            piece.setViewCount(0);
            
            // Save the piece first
            graffitiPieceRepository.save(piece);
            
            // Then update relationships (if needed)
            // The relationships are already set via setters above
        } catch (Exception e) {
            log.error("Error creating graffiti piece: {}", title, e);
        }
    }
}

