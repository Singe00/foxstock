package kumoh.opensource.foxstock.api.repository;

import kumoh.opensource.foxstock.api.dto.OpenDartCodeDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OpenDartCodeDtoRepository extends JpaRepository<OpenDartCodeDto, String> {
}
