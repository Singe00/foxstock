package kumoh.opensource.foxstock.Search;

import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Stock,String> {

    Page<Stock> findAllByNameContaining(String searchString, Pageable pageable);

}
