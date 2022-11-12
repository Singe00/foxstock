package kumoh.opensource.foxstock.api.repository;

import kumoh.opensource.foxstock.api.dto.PriceDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceDtoRepository extends JpaRepository<PriceDto, String> {
}
