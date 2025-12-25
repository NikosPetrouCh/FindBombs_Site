package gr.aueb.cf.findbombs.controller;

import gr.aueb.cf.findbombs.core.exceptions.EntityInvalidArgumentException;
import gr.aueb.cf.findbombs.dto.GraffitiPieceInsertDTO;
import gr.aueb.cf.findbombs.model.Artist;
import gr.aueb.cf.findbombs.model.City;
import gr.aueb.cf.findbombs.model.GraffitiStyle;
import gr.aueb.cf.findbombs.model.Location;
import gr.aueb.cf.findbombs.repository.ArtistRepository;
import gr.aueb.cf.findbombs.repository.CityRepository;
import gr.aueb.cf.findbombs.repository.GraffitiStyleRepository;
import gr.aueb.cf.findbombs.repository.LocationRepository;
import gr.aueb.cf.findbombs.service.FileStorageService;
import gr.aueb.cf.findbombs.service.IGraffitiPieceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UploadController {

    private final IGraffitiPieceService graffitiPieceService;
    private final FileStorageService fileStorageService;
    private final CityRepository cityRepository;
    private final GraffitiStyleRepository styleRepository;
    private final LocationRepository locationRepository;
    private final ArtistRepository artistRepository;

    @GetMapping("/upload")
    public String showUploadForm(Model model) {
        try {
            List<City> cities = cityRepository.findAll();
            List<GraffitiStyle> styles = styleRepository.findAll();
            List<Location> locations = locationRepository.findAll();
            List<Artist> artists = artistRepository.findAll();

            model.addAttribute("cities", cities != null ? cities : List.of());
            model.addAttribute("styles", styles != null ? styles : List.of());
            model.addAttribute("locations", locations != null ? locations : List.of());
            model.addAttribute("artists", artists != null ? artists : List.of());
            
            return "upload";
        } catch (Exception e) {
            log.error("Error loading upload form", e);
            model.addAttribute("cities", List.of());
            model.addAttribute("styles", List.of());
            model.addAttribute("locations", List.of());
            model.addAttribute("artists", List.of());
            return "upload";
        }
    }

    @PostMapping("/upload")
    public String handleUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam(value = "description", required = false) String description,
            @RequestParam("cityId") Long cityId,
            @RequestParam("styleId") Long styleId,
            @RequestParam(value = "locationId", required = false) Long locationId,
            @RequestParam(value = "artistId", required = false) Long artistId,
            @RequestParam(value = "graffitiDate", required = false) String graffitiDateStr,
            RedirectAttributes redirectAttributes) {
        
        try {
            // Validate file
            if (file == null || file.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Please select an image file");
                return "redirect:/upload";
            }

            // Save file to database and get file ID
            Long fileId = fileStorageService.saveFile(file);

            // Create DTO with file ID as URL
            GraffitiPieceInsertDTO dto = new GraffitiPieceInsertDTO();
            dto.setTitle(title);
            dto.setDescription(description);
            dto.setCityId(cityId);
            dto.setStyleId(styleId);
            dto.setLocationId(locationId);
            dto.setArtistId(artistId);
            dto.setImageUrl("/api/files/" + fileId);
            
            if (graffitiDateStr != null && !graffitiDateStr.isEmpty()) {
                try {
                    dto.setGraffitiDate(LocalDate.parse(graffitiDateStr));
                } catch (Exception e) {
                    log.warn("Invalid date format: {}", graffitiDateStr);
                }
            }

            // Save graffiti piece
            graffitiPieceService.saveGraffitiPiece(dto);
            
            redirectAttributes.addFlashAttribute("success", "Graffiti uploaded successfully!");
            return "redirect:/gallery";
            
        } catch (EntityInvalidArgumentException e) {
            log.error("Invalid data for upload", e);
            redirectAttributes.addFlashAttribute("error", "Invalid data: " + e.getMessage());
            return "redirect:/upload";
        } catch (IllegalArgumentException e) {
            log.error("File validation error", e);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/upload";
        } catch (Exception e) {
            log.error("Error uploading graffiti", e);
            redirectAttributes.addFlashAttribute("error", "Error uploading graffiti: " + e.getMessage());
            return "redirect:/upload";
        }
    }
}


