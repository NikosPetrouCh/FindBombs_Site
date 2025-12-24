package gr.aueb.cf.findbombs.repository;

import gr.aueb.cf.findbombs.model.Artist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
    Optional<Artist> findByName(String name);
    Optional<Artist> findByAlias(String alias);
}


