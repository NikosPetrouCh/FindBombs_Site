package gr.aueb.cf.findbombs.controller;

import gr.aueb.cf.findbombs.core.exceptions.EntityNotFoundException;
import gr.aueb.cf.findbombs.dto.GraffitiPieceReadOnlyDTO;
import gr.aueb.cf.findbombs.model.City;
import gr.aueb.cf.findbombs.repository.CityRepository;
import gr.aueb.cf.findbombs.service.IGraffitiPieceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final IGraffitiPieceService graffitiPieceService;
    private final CityRepository cityRepository;

    @GetMapping("/")
    public String home(Model model) {
        try {
            List<GraffitiPieceReadOnlyDTO> recentPieces = graffitiPieceService.getRecentGraffitiPieces(5);
            model.addAttribute("recentPieces", recentPieces != null ? recentPieces : List.of());
            
            // Safe cities retrieval
            List<City> cities = null;
            try {
                cities = cityRepository.findAll();
            } catch (Exception dbEx) {
                log.warn("Could not fetch cities from database, using empty list", dbEx);
                cities = List.of();
            }
            model.addAttribute("cities", cities != null ? cities : List.of());
            
            return "index";
        } catch (Exception e) {
            log.error("Error loading home page", e);
            model.addAttribute("recentPieces", List.of());
            model.addAttribute("cities", List.of());
            return "index";
        }
    }

    @GetMapping("/city/{cityId}")
    public String getCityGraffiti(@PathVariable Long cityId, Model model) {
        try {
            City city = cityRepository.findById(cityId)
                    .orElseThrow(() -> new EntityNotFoundException("City", "City not found"));
            
            List<GraffitiPieceReadOnlyDTO> pieces = graffitiPieceService.getGraffitiPiecesByCity(cityId);
            model.addAttribute("city", city);
            model.addAttribute("pieces", pieces);
            return "city-gallery";
        } catch (EntityNotFoundException e) {
            log.error("City with id={} was not found.", cityId, e);
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }

    @GetMapping("/gallery")
    public String getGallery(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        Page<GraffitiPieceReadOnlyDTO> piecesPage = graffitiPieceService.getPaginatedGraffitiPieces(page, size);
        model.addAttribute("piecesPage", piecesPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", piecesPage.getTotalPages());
        return "gallery";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(required = false, defaultValue = "") String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Model model) {
        if (q == null || q.trim().isEmpty()) {
            return "redirect:/gallery";
        }
        Page<GraffitiPieceReadOnlyDTO> piecesPage = graffitiPieceService.searchGraffitiPieces(q, page, size);
        model.addAttribute("piecesPage", piecesPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", piecesPage.getTotalPages());
        model.addAttribute("searchQuery", q);
        return "search-results";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }
}


