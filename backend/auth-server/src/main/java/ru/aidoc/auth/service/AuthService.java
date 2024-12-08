package ru.aidoc.auth.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    /*private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Пользователь с таким email уже существует");
        }

        User user = new User();
        user.setUserId(UUID.randomUUID()); // Генерируем UUID
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setMiddleName(request.getMiddleName());
        user.setPhoneNumber(request.getPhoneNumber());
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());

        // Добавление роли пользователю
        Role userRole = roleRepository.findByRoleName("ROLE_USER")
                .orElseGet(() -> {
                    Role newRole = new Role("ROLE_USER");
                    return roleRepository.save(newRole);
                });
        user.getRoles().add(userRole);

        user = userRepository.save(user);

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles().stream().map(Role::getRoleName).toList());

        String token = jwtService.generateToken(claims, user.getEmail());
        return new AuthResponse(token, "Bearer", user.getEmail(), "ROLE_USER");
    }

    public AuthResponse login(AuthRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Пользователь не найден"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Неверный пароль");
        }

        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", user.getRoles().stream().map(Role::getRoleName).toList());

        String token = jwtService.generateToken(claims, user.getEmail());
        return new AuthResponse(token, "Bearer", user.getEmail(), user.getRoles().stream().map(Role::getRoleName).findFirst().orElse("ROLE_USER"));
    }

     */
}