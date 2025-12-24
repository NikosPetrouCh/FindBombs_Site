package gr.aueb.cf.findbombs.repository;

import gr.aueb.cf.findbombs.model.GraffitiStyle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GraffitiStyleRepository extends JpaRepository<GraffitiStyle, Long> {
    Optional<GraffitiStyle> findByName(String name);
}


