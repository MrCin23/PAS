package pl.lodz.p.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RentDTO {
    private UUID clientId;
    private UUID vmId;
    private LocalDateTime startTime;
}
