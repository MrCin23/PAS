package pl.lodz.p.mvc.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UuidDTO {
    private String uuid;

    public UUID uuid(){
        return UUID.fromString(uuid);
    }
}
