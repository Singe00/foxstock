package kumoh.opensource.foxstock.api.repository;

import kumoh.opensource.foxstock.api.dto.CodeDto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeDtoRepository extends JpaRepository<CodeDto, String> {

}
