package shop.bookbom.shop.domain.wrapper.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shop.bookbom.shop.domain.wrapper.entity.Wrapper;

public interface WrapperRepository extends JpaRepository<Wrapper, Long> {
    @Query("SELECT w.cost from Wrapper w where w.name = :wrapperName")
    Integer getCostByName(@Param("wrapperName") String wrapperName);

    Optional<Wrapper> findByName(String wrapperName);

}
