package kumoh.opensource.foxstock.api.repository;

import kumoh.opensource.foxstock.api.dto.NaverDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NaverDtoRepository extends JpaRepository<NaverDto, String> {
}
