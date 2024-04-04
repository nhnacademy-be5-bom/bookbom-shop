package shop.bookbom.shop.domain.wrapper.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.bookbom.shop.domain.wrapper.entity.Wrapper;

@Repository
public interface WrapperRepository extends JpaRepository<Wrapper, Long> {


}
