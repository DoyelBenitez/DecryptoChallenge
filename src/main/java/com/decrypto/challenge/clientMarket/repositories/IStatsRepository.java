package com.decrypto.challenge.clientMarket.repositories;

import com.decrypto.challenge.clientMarket.entities.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

/**
 * @Author dbenitez
 */
public interface IStatsRepository extends JpaRepository<Market, Long> {

    @Query(value = """
        SELECT
            m.code AS code,
            m.id_pais AS country,
            ROUND((COUNT(cm.clients_id) * 100.0 / (SELECT COUNT(*) FROM client_markets)), 2) AS percentage
        FROM client_markets cm
        INNER JOIN client c ON cm.clients_id = c.id
        INNER JOIN market m ON cm.markets_id = m.id
        WHERE c.deleted = false
        GROUP BY cm.markets_id, m.id_pais, m.code
        ORDER BY percentage DESC
        """, nativeQuery = true)
    Optional<List<Object[]>> findMarketStatistics();
}
