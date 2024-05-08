package shop.bookbom.shop.domain.users.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.bookbom.shop.common.exception.BaseException;
import shop.bookbom.shop.common.exception.ErrorCode;
import shop.bookbom.shop.domain.order.dto.response.OrderInfoResponse;
import shop.bookbom.shop.domain.role.entity.Role;
import shop.bookbom.shop.domain.role.repository.RoleRepository;
import shop.bookbom.shop.domain.users.dto.OrderDateCondition;
import shop.bookbom.shop.domain.users.dto.request.EmailPasswordDto;
import shop.bookbom.shop.domain.users.dto.request.ResetPasswordRequestDto;
import shop.bookbom.shop.domain.users.dto.request.UserRequestDto;
import shop.bookbom.shop.domain.users.dto.response.UserIdRole;
import shop.bookbom.shop.domain.users.entity.User;
import shop.bookbom.shop.domain.users.exception.RoleNotFoundException;
import shop.bookbom.shop.domain.users.exception.UserAlreadyExistException;
import shop.bookbom.shop.domain.users.exception.UserNotFoundException;
import shop.bookbom.shop.domain.users.repository.UserRepository;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional
    public Long save(UserRequestDto userRequestDto) {
        Role role = roleRepository.findByName(userRequestDto.getRoleName())
                .orElseThrow(RoleNotFoundException::new);

        if (checkEmailCanUse(userRequestDto.getEmail())) {
            // 오류로 인해 요청이 여러번 왔을 때를 대비, 아이디 중복 검증
            return userRepository.save(
                    User.builder()
                            .email(userRequestDto.getEmail())
                            .password(userRequestDto.getPassword())
                            .role(role)
                            .build()
            ).getId();

        } else {
            throw new UserAlreadyExistException();
        }
    }

    @Override
    public boolean checkEmailCanUse(String email) {
        return !userRepository.existsUserByEmail(email);
    }

    @Override
    public boolean confirm(EmailPasswordDto emailPasswordDto) {
        User user = userRepository.findByEmail(emailPasswordDto.getEmail()).orElseThrow(UserNotFoundException::new);
        return user.getPassword().equals(emailPasswordDto.getPassword());
    }

    @Override
    @Transactional
    public UserIdRole getIdRole(EmailPasswordDto emailPasswordDto) {
        User user = userRepository.findByEmail(emailPasswordDto.getEmail()).orElseThrow(UserNotFoundException::new);
        if (!user.getPassword().equals(emailPasswordDto.getPassword())) {
            throw new BaseException(ErrorCode.USER_NOT_VALIDATE);
        }
        return UserIdRole.builder().userId(user.getId()).role(user.getRole().getName()).build();
    }


    @Override
    public void changeRegistered(Long id, boolean registered) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        user.changeRegistered(registered);
        userRepository.save(user);
    }

    @Override
    public void resetPassword(ResetPasswordRequestDto resetPasswordRequestDto) {
        User user = userRepository.findById(resetPasswordRequestDto.getId())
                .orElseThrow(UserNotFoundException::new);
        user.resetPassword(resetPasswordRequestDto.getPassword());
        userRepository.save(user);
    }

    @Override
    public boolean isRegistered(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        return user.isRegistered();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OrderInfoResponse> getOrderInfos(
            Long userId,
            Pageable pageable,
            OrderDateCondition condition
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        return userRepository.getOrders(user, pageable, condition);
    }

}
