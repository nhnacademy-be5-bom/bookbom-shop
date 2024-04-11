package shop.bookbom.shop.domain.users.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.bookbom.shop.domain.role.entity.Role;
import shop.bookbom.shop.domain.role.repository.RoleRepository;
import shop.bookbom.shop.domain.users.dto.request.ChangeRegisteredRequestDto;
import shop.bookbom.shop.domain.users.dto.request.ResetPasswordRequestDto;
import shop.bookbom.shop.domain.users.dto.request.UserRequestDto;
import shop.bookbom.shop.domain.users.entity.User;
import shop.bookbom.shop.domain.users.exception.RoleNotFoundException;
import shop.bookbom.shop.domain.users.exception.UserAlreadyExistException;
import shop.bookbom.shop.domain.users.exception.UserNotFoundException;
import shop.bookbom.shop.domain.users.repository.UserRepository;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public Long save(UserRequestDto userRequestDto) {
        Optional<Role> optionalRole = roleRepository.findByName(userRequestDto.getRoleName());
        if (optionalRole.isEmpty()) {
            throw new RoleNotFoundException();
        }
        if (checkEmailCanUse(userRequestDto.getEmail())) {
            // 오류로 인해 요청이 여러번 왔을 때를 대비, 아이디 중복 검증
            User user = userRepository.save(
                    User.builder()
                            .email(userRequestDto.getEmail())
                            .password(userRequestDto.getPassword())
                            .role(optionalRole.get())
                            .build()
            );
            return user.getId();
        } else {
            throw new UserAlreadyExistException();
        }
    }

    @Override
    public void changeRegistered(ChangeRegisteredRequestDto changeRegisteredRequestDto) {
        Optional<User> optionalUser = userRepository.findById(changeRegisteredRequestDto.getId());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        } else {
            User user = optionalUser.get();
            user.changeRegistered(changeRegisteredRequestDto.isRegistered());
            userRepository.save(user);
        }
    }

    @Override
    public void resetPassword(ResetPasswordRequestDto resetPasswordRequestDto) {
        Optional<User> optionalUser = userRepository.findById(resetPasswordRequestDto.getId());
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        } else {
            User user = optionalUser.get();
            user.resetPassword(resetPasswordRequestDto.getPassword());
            userRepository.save(user);
        }
    }

    @Override
    public boolean isRegistered(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException();
        } else {
            return optionalUser.get().isRegistered();
        }
    }
}
