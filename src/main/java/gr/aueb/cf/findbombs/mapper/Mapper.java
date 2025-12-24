package gr.aueb.cf.findbombs.mapper;

import gr.aueb.cf.findbombs.dto.GraffitiPieceInsertDTO;
import gr.aueb.cf.findbombs.dto.GraffitiPieceReadOnlyDTO;
import gr.aueb.cf.findbombs.model.GraffitiPiece;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public GraffitiPieceReadOnlyDTO mapToGraffitiPieceReadOnlyDTO(GraffitiPiece piece) {
        if (piece == null) return null;

        GraffitiPieceReadOnlyDTO dto = new GraffitiPieceReadOnlyDTO();
        dto.setId(piece.getId());
        dto.setCreatedAt(piece.getCreatedAt());
        dto.setUpdatedAt(piece.getUpdatedAt());
        dto.setTitle(piece.getTitle() != null ? piece.getTitle() : "Untitled");
        dto.setDescription(piece.getDescription());
        dto.setCityName(piece.getCity() != null ? piece.getCity().getName() : "Unknown");
        dto.setLocationName(piece.getLocation() != null ? piece.getLocation().getName() : null);
        dto.setArtistName(piece.getArtist() != null ? piece.getArtist().getName() : "Anonymous");
        dto.setStyleName(piece.getStyle() != null ? piece.getStyle().getName() : "Unknown");
        dto.setImageUrl(piece.getImageUrl() != null ? "/" + piece.getImageUrl() : "/images/sample-photo.jpg");
        dto.setGraffitiDate(piece.getGraffitiDate());
        dto.setRating(piece.getRating() != null ? piece.getRating() : java.math.BigDecimal.ZERO);
        dto.setViewCount(piece.getViewCount() != null ? piece.getViewCount() : 0);
        
        return dto;
    }

    public GraffitiPiece mapToGraffitiPieceEntity(GraffitiPieceInsertDTO dto) {
        if (dto == null) return null;

        GraffitiPiece piece = new GraffitiPiece();
        piece.setTitle(dto.getTitle());
        piece.setDescription(dto.getDescription());
        piece.setImageUrl(dto.getImageUrl());
        piece.setGraffitiDate(dto.getGraffitiDate());
        piece.setIsActive(true);
        piece.setViewCount(0);
        piece.setRating(java.math.BigDecimal.ZERO);
        
        return piece;
    }
}


