package gr.aueb.cf.findbombs.service;

import gr.aueb.cf.findbombs.dto.GraffitiPieceInsertDTO;
import gr.aueb.cf.findbombs.dto.GraffitiPieceReadOnlyDTO;
import gr.aueb.cf.findbombs.model.GraffitiPiece;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IGraffitiPieceService {
    GraffitiPiece saveGraffitiPiece(GraffitiPieceInsertDTO dto);
    Page<GraffitiPieceReadOnlyDTO> getPaginatedGraffitiPieces(int page, int size);
    List<GraffitiPieceReadOnlyDTO> getGraffitiPiecesByCity(Long cityId);
    List<GraffitiPieceReadOnlyDTO> getRecentGraffitiPieces(int limit);
    GraffitiPieceReadOnlyDTO getGraffitiPieceById(Long id);
    Page<GraffitiPieceReadOnlyDTO> searchGraffitiPieces(String query, int page, int size);
}


