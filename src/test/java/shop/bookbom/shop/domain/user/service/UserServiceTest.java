package shop.bookbom.shop.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import shop.bookbom.shop.domain.role.entity.Role;
import shop.bookbom.shop.domain.role.repository.RoleRepository;
import shop.bookbom.shop.domain.users.dto.request.ResetPasswordRequestDto;
import shop.bookbom.shop.domain.users.dto.request.UserRequestDto;
import shop.bookbom.shop.domain.users.entity.User;
import shop.bookbom.shop.domain.users.exception.RoleNotFoundException;
import shop.bookbom.shop.domain.users.exception.UserAlreadyExistException;
import shop.bookbom.shop.domain.users.exception.UserNotFoundException;
import shop.bookbom.shop.domain.users.repository.UserRepository;
import shop.bookbom.shop.domain.users.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    @DisplayName("Save user test - success")
    void saveUserSuccessTest() {
        UserRequestDto userRequestDto =
                UserRequestDto.builder().email("test@email.com").password("password").roleName("ROLE_USER").build();

        Role role = Role.builder()
                .id(1L)
                .name("ROLE_USER")
                .build();

        User user = User.builder()
                .id(1L)
                .email("test@email.com")
                .password("password")
                .role(role)
                .registered(true)
                .build();

        when(roleRepository.findByName(anyString())).thenReturn(Optional.of(role));
        when(userRepository.existsUserByEmail(any())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(user);

        Long userId = userService.save(userRequestDto);

        verify(roleRepository).findByName(anyString());
        // Then
        assertNotNull(userId);
        assertEquals(1L, userId);
    }

    @Test
    @DisplayName("Save user test - duplicate email")
    void saveUserDuplicateEmailTest() {
        UserRequestDto userRequestDto =
                UserRequestDto.builder().email("test@email.com").password("password").roleName("ROLE_USER").build();

        Role role = Role.builder()
                .name("ROLE_USER")
                .build();

        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(userRepository.existsUserByEmail("test@email.com")).thenReturn(true);

        assertThrows(UserAlreadyExistException.class, () -> userService.save(userRequestDto));
    }

    @Test
    @DisplayName("Save user test - role not found")
    void saveUserRoleNotFoundTest() {
        UserRequestDto userRequestDto =
                UserRequestDto.builder().email("test@email.com").password("password").roleName("INVALID_ROLE").build();

        when(roleRepository.findByName("INVALID_ROLE")).thenReturn(Optional.empty());

        assertThrows(RoleNotFoundException.class, () -> userService.save(userRequestDto));
    }

    @Test
    @DisplayName("check email - true : new email")
    void checkEmailCanUseTrueTest() {
        when(userRepository.existsUserByEmail(anyString())).thenReturn(false);

        assertEquals(true, userService.checkEmailCanUse("test@email.com"));
    }

    @Test
    @DisplayName("check email - false : duplicate email")
    void checkEmailCanUseFalseTest() {
        when(userRepository.existsUserByEmail(anyString())).thenReturn(true);

        assertEquals(false, userService.checkEmailCanUse("test@email.com"));
    }

    @Test
    @DisplayName("reset password - success")
    void resetPasswordSuccessTest() {

        ResetPasswordRequestDto resetPasswordRequestDto = ResetPasswordRequestDto.builder()
                .id(1L)
                .password("1")
                .build();

        Role role = Role.builder()
                .id(1L)
                .name("ROLE_USER")
                .build();

        User user = User.builder()
                .id(1L)
                .email("test@email.com")
                .password("password")
                .role(role)
                .registered(true)
                .build();
        when(userRepository.findById(any())).thenReturn(Optional.of(user));
        userService.resetPassword(resetPasswordRequestDto);

        verify(userRepository, times(1)).save(user);
    }

    @Test
    @DisplayName("reset password - user not found")
    void resetPasswordUserNotFoundTest() {

        ResetPasswordRequestDto resetPasswordRequestDto = ResetPasswordRequestDto.builder()
                .id(1L)
                .password("1")
                .build();

        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.resetPassword(resetPasswordRequestDto));
    }

    @Test
    @DisplayName("is registered - success")
    void isRegisteredSuccessTest() {
        Role role = Role.builder()
                .id(1L)
                .name("ROLE_USER")
                .build();

        User user = User.builder()
                .id(1L)
                .email("test@email.com")
                .password("password")
                .role(role)
                .registered(true)
                .build();

        when(userRepository.findById(any())).thenReturn(Optional.of(user));

        assertEquals(true, userService.isRegistered(1L));
    }

    @Test
    @DisplayName("is registered - user not found")
    void isRegisteredUserNotFoundTest() {
        when(userRepository.findById(any())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.isRegistered(1L));
    }
}
