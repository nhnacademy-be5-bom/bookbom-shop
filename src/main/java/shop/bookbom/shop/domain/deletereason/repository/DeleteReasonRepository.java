package shop.bookbom.shop.domain.deletereason.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import shop.bookbom.shop.domain.deletereason.entity.DeleteReason;

public interface DeleteReasonRepository extends JpaRepository<DeleteReason, Long> {
}
