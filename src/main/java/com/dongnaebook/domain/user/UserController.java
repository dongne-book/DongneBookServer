package com.dongnaebook.domain.user;

import com.dongnaebook.domain.user.DTO.PasswordDTO;
import com.dongnaebook.domain.user.DTO.UserRequestDTO;
import com.dongnaebook.domain.user.DTO.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/my")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserResponseDTO> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
        String userEmail = userDetails.getUsername();
        return ResponseEntity.ok(userService.getByEmail(userEmail));
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserResponseDTO> setProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                      @RequestBody UserRequestDTO userRequestDTO) {
        String userEmail = userDetails.getUsername();
        return ResponseEntity.ok(userService.setUser(userEmail, userRequestDTO));
    }

    @PostMapping("/password")
    public ResponseEntity<Boolean> setPassword(@AuthenticationPrincipal UserDetails userDetails,
                                               @RequestBody PasswordDTO passwordDTO) {
        String userEmail = userDetails.getUsername();
        return ResponseEntity.ok(userService.setPassword(userEmail, passwordDTO));
    }
}
