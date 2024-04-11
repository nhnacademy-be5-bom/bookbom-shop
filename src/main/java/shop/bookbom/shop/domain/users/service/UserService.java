package shop.bookbom.shop.domain.users.service;

import shop.bookbom.shop.domain.users.dto.request.ResetPasswordRequestDto;
import shop.bookbom.shop.domain.users.dto.request.UserRequestDto;

public interface UserService {

    Long save(UserRequestDto userRequestDto);

    void resetPassword(ResetPasswordRequestDto resetPasswordRequestDto);

}
