package gr.aueb.cf.findbombs.service;

import gr.aueb.cf.findbombs.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.findbombs.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.findbombs.dto.GraffitiPieceInsertDTO;
import gr.aueb.cf.findbombs.dto.GraffitiPieceReadOnlyDTO;
import gr.aueb.cf.findbombs.mapper.Mapper;
import gr.aueb.cf.findbombs.model.*;
import gr.aueb.cf.findbombs.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GraffitiPieceService implements IGraffitiPieceService {

    private final GraffitiPieceRepository graffitiPieceRepository;
    private final CityRepository cityRepository;
    private final gr.aueb.cf.findbombs.repository.LocationRepository locationRepository;
    private final ArtistRepository artistRepository;
    private final GraffitiStyleRepository styleRepository;
    private final Mapper mapper;

    @Override
    @Transactional(rollbackOn = Exception.class)
    public GraffitiPiece saveGraffitiPiece(GraffitiPieceInsertDTO dto) throws EntityInvalidArgumentException {
        try {
            City city = cityRepository.findById(dto.getCityId())
                    .orElseThrow(() -> new EntityInvalidArgumentException("City", "Invalid city id"));

            GraffitiStyle style = styleRepository.findById(dto.getStyleId())
                    .orElseThrow(() -> new EntityInvalidArgumentException("Style", "Invalid style id"));

            GraffitiPiece piece = mapper.mapToGraffitiPieceEntity(dto);
            piece.setCity(city);
            piece.setStyle(style);

            if (dto.getLocationId() != null) {
                Location location = locationRepository.findById(dto.getLocationId())
                        .orElse(null);
                piece.setLocation(location);
            }

            if (dto.getArtistId() != null) {
                Artist artist = artistRepository.findById(dto.getArtistId())
                        .orElse(null);
                piece.setArtist(artist);
            }

            city.addGraffitiPiece(piece);
            style.addGraffitiPiece(piece);

            graffitiPieceRepository.save(piece);
            log.info("Graffiti piece with title={} saved.", dto.getTitle());
            return piece;

        } catch (EntityInvalidArgumentException e) {
            log.error("Save failed for graffiti piece with title={}. {}", dto.getTitle(), e.getMessage(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public Page<GraffitiPieceReadOnlyDTO> getPaginatedGraffitiPieces(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GraffitiPiece> piecesPage = graffitiPieceRepository.findRecentPieces(pageable);
        return piecesPage.map(mapper::mapToGraffitiPieceReadOnlyDTO);
    }

    @Override
    @Transactional
    public List<GraffitiPieceReadOnlyDTO> getGraffitiPiecesByCity(Long cityId) {
        List<GraffitiPiece> pieces = graffitiPieceRepository.findByCityIdAndIsActiveTrue(cityId);
        return pieces.stream()
                .map(mapper::mapToGraffitiPieceReadOnlyDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<GraffitiPieceReadOnlyDTO> getRecentGraffitiPieces(int limit) {
        try {
            Pageable pageable = PageRequest.of(0, limit);
            Page<GraffitiPiece> piecesPage = graffitiPieceRepository.findTopViewedPieces(pageable);
            return piecesPage.getContent().stream()
                    .map(mapper::mapToGraffitiPieceReadOnlyDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.warn("Error getting recent pieces: {}", e.getMessage());
            return List.of(); // Return empty list if error
        }
    }

    @Override
    @Transactional
    public GraffitiPieceReadOnlyDTO getGraffitiPieceById(Long id) throws EntityNotFoundException {
        GraffitiPiece piece = graffitiPieceRepository.findByIdAndIsActiveTrue(id)
                .orElseThrow(() -> new EntityNotFoundException("GraffitiPiece", "Graffiti piece with id " + id + " not found"));
        
        // Increment view count
        piece.setViewCount(piece.getViewCount() + 1);
        graffitiPieceRepository.save(piece);
        
        return mapper.mapToGraffitiPieceReadOnlyDTO(piece);
    }

    @Override
    @Transactional
    public Page<GraffitiPieceReadOnlyDTO> searchGraffitiPieces(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GraffitiPiece> piecesPage = graffitiPieceRepository.searchByQuery(query, pageable);
        return piecesPage.map(mapper::mapToGraffitiPieceReadOnlyDTO);
    }
}

