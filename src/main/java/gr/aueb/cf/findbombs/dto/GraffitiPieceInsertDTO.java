package gr.aueb.cf.findbombs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GraffitiPieceInsertDTO {
    
    @NotBlank(message = "Title is required")
    private String title;
    
    private String description;
    
    @NotNull(message = "City ID is required")
    private Long cityId;
    
    private Long locationId;
    
    private Long artistId;
    
    @NotNull(message = "Style ID is required")
    private Long styleId;
    
    @NotBlank(message = "Image URL is required")
    private String imageUrl;
    
    private LocalDate graffitiDate;
}


