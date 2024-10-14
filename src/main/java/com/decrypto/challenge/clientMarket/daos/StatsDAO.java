package com.decrypto.challenge.clientMarket.daos;

import com.decrypto.challenge.clientMarket.daos.interfaces.IStatsDAO;
import com.decrypto.challenge.clientMarket.repositories.IStatsRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Repository
public class StatsDAO implements IStatsDAO {

    @Autowired
    private final IStatsRepository statsRepository;

    public List<Object[]> getStatsData() {
        return this.statsRepository.findMarketStatistics()
                .orElse(new ArrayList<>());
    }
}
