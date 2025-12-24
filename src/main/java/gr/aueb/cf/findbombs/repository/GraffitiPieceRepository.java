package gr.aueb.cf.findbombs.repository;

import gr.aueb.cf.findbombs.model.GraffitiPiece;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GraffitiPieceRepository extends JpaRepository<GraffitiPiece, Long> {
    
    List<GraffitiPiece> findByCityIdAndIsActiveTrue(Long cityId);
    
    List<GraffitiPiece> findByStyleIdAndIsActiveTrue(Long styleId);
    
    List<GraffitiPiece> findByArtistIdAndIsActiveTrue(Long artistId);
    
    Optional<GraffitiPiece> findByIdAndIsActiveTrue(Long id);
    
    @Query("SELECT g FROM GraffitiPiece g WHERE g.isActive = true ORDER BY g.viewCount DESC")
    Page<GraffitiPiece> findTopViewedPieces(Pageable pageable);
    
    @Query("SELECT g FROM GraffitiPiece g WHERE g.isActive = true ORDER BY g.createdAt DESC")
    Page<GraffitiPiece> findRecentPieces(Pageable pageable);
    
    @Query("SELECT g FROM GraffitiPiece g WHERE g.isActive = true AND " +
           "LOWER(g.title) LIKE LOWER(CONCAT('%', :query, '%'))")
    Page<GraffitiPiece> searchByQuery(@Param("query") String query, Pageable pageable);
}


