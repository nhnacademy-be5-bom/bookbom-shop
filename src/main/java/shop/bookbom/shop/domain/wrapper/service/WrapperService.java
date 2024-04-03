package shop.bookbom.shop.domain.wrapper.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import shop.bookbom.shop.domain.wrapper.entity.Wrapper;
import shop.bookbom.shop.domain.wrapper.repository.WrapperRepository;


@Service
@RequiredArgsConstructor
public class WrapperService {
    private final WrapperRepository wrapperRepository;

    public List<Wrapper> getAllWrapper() {
        return wrapperRepository.findAll();
    }
}
