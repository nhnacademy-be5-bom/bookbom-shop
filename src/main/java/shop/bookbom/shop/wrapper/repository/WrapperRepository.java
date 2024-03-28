package shop.bookbom.shop.wrapper.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import shop.bookbom.shop.wrapper.entity.Wrapper;

@Repository
public interface WrapperRepository extends JpaRepository<Wrapper, Long> {
    List<Wrapper> findAll();


}
