package UMC.campusNote.user.entity;

import jakarta.persistence.GeneratedValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public enum Role {
    USER("USER"),
    VIP("VIP"),
    ADMIN("ADMIN");
    private final String role;


}
