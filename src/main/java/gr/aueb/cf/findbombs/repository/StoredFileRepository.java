package gr.aueb.cf.findbombs.repository;

import gr.aueb.cf.findbombs.model.StoredFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StoredFileRepository extends JpaRepository<StoredFile, Long> {
    Optional<StoredFile> findByIdAndIsActiveTrue(Long id);
}

