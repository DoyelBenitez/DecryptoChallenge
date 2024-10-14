package com.decrypto.challenge.clientMarket._core.configs;

import com.decrypto.challenge.clientMarket.dtos.ClientDTO;
import com.decrypto.challenge.clientMarket.dtos.MarketCountryDTO;
import com.decrypto.challenge.clientMarket.dtos.MarketDTO;
import com.decrypto.challenge.clientMarket.services.interfaces.IClientService;
import com.decrypto.challenge.clientMarket.services.interfaces.ICountryClientStrategy;
import com.decrypto.challenge.clientMarket.services.interfaces.IMarketService;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.List;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Configuration
public class ClientMarketDataInitializerConfig {

    @Autowired
    private final IMarketService marketService;
    @Autowired
    private final IClientService clientService;
    @Autowired
    private final ICountryClientStrategy countryClient;

    @Order(3)
    @Bean
    public CommandLineRunner initClientMarketDatabase() {
        return args -> {
            createMarketIfNotExists("MAE", "Argentina", "Mercado Abierto Electrónico");
            createMarketIfNotExists("ROFEX", "Argentina", "Matba Rofex Sociedad Anonima");
            createMarketIfNotExists("UFEX", "Uruguay", "Bolsa de Valores de Montevideo");

            createClientIfNotExists("user_mae01", List.of("MAE", "ROFEX"));
            createClientIfNotExists("trader_rofex", List.of("MAE", "UFEX"));
            createClientIfNotExists("investor_ufex", List.of("ROFEX", "UFEX"));
            createClientIfNotExists("market_analyst01", List.of("MAE", "ROFEX", "UFEX"));
            createClientIfNotExists("financial_guru", List.of("MAE"));
            createClientIfNotExists("equity_trader", List.of("ROFEX", "MAE"));
            createClientIfNotExists("bond_expert", List.of("UFEX"));
            createClientIfNotExists("marketwatcher", List.of("ROFEX", "UFEX", "MAE"));
            createClientIfNotExists("commodities_master", List.of("MAE", "UFEX"));
            createClientIfNotExists("forex_specialist", List.of("MAE", "ROFEX"));
            // createClientIfNotExists("futures_broker", List.of("UFEX", "ROFEX"));
        };
    }

    private void createMarketIfNotExists(String market, String countryCode, String description) throws ServiceExceptionP {
        if (!this.marketService.existsBy(market)) {
            MarketCountryDTO marketCountryDto = this.countryClient.findBy(countryCode);
            MarketDTO marketDto = new MarketDTO();
            marketDto.setCode(market);
            marketDto.setDescription(description);
            marketDto.setCountry(marketCountryDto);
            this.marketService.save(marketDto);
            System.out.println("El mercado " + market + " fue creado.");
        } else {
            System.out.println("El mercado " + market + " ya existe.");
        }
    }

    private void createClientIfNotExists(String description, List<String> marketCodes) throws ServiceExceptionP {
        if (!this.clientService.existsBy(description)) {
            ClientDTO clientDto = new ClientDTO();
            clientDto.setDescription(description);
            clientDto.setMarketCodes(marketCodes);
            this.clientService.save(clientDto);
            System.out.println("El cliente " + description + " fue creado.");
        } else {
            System.out.println("El cliente " + description + " ya existe.");
        }
    }
}
