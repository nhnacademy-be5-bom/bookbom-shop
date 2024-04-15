package shop.bookbom.shop.domain.wrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.wrapper.entity.Wrapper;

public interface WrapperRepository extends JpaRepository<Wrapper, Long> {

}
