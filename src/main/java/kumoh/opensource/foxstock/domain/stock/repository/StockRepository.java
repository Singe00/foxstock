package kumoh.opensource.foxstock.domain.stock.repository;

import kumoh.opensource.foxstock.domain.stock.domain.Stock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
    Page<Stock> findAllByMrktCtg(String mrktCtg, Pageable pageable);

    @Transactional
    void deleteAllByMrktCtg(String mrktCtg);

}
