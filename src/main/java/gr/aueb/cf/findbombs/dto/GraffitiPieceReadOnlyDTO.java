package gr.aueb.cf.findbombs.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GraffitiPieceReadOnlyDTO {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String title;
    private String description;
    private String cityName;
    private String locationName;
    private String artistName;
    private String styleName;
    private String imageUrl;
    private LocalDate graffitiDate;
    private BigDecimal rating;
    private Integer viewCount;
}


