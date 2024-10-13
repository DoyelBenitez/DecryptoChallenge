package com.decrypto.challenge.clientMarket.services;

import com.decrypto.challenge.clientMarket.daos.interfaces.IClientDAO;
import com.decrypto.challenge.clientMarket.dtos.ClientDTO;
import com.decrypto.challenge.clientMarket.dtos.MarketDTO;
import com.decrypto.challenge.clientMarket.services.interfaces.IClientService;
import com.decrypto.challenge.clientMarket.services.interfaces.IMarketService;
import com.decrypto.challenge.common._core.exceptions.ServiceExceptionP;
import com.decrypto.challenge.common.services.AbstractService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.decrypto.challenge.common._core.utils.AppUtils.convertToTitleCase;

/**
 * @Author dbenitez
 */
@AllArgsConstructor
@Service
public class ClientService extends AbstractService implements IClientService {

    @Autowired
    private final IClientDAO clientDao;
    @Autowired
    private final IMarketService marketService;

    public void save(ClientDTO clientDto) throws ServiceExceptionP {
        this.checkNull("generic.null", clientDto);
        this.checkNull("generic.null", clientDto.getDescription());
        this.throwExceptionIf(this.clientDao.existsBy(clientDto.getDescription()), "client.alreadyExist");
        clientDto.setDescription(convertToTitleCase(clientDto.getDescription()));
        if (clientDto.getMarketCodes() != null) {
            List<MarketDTO> marketDtoList = this.marketService.findAll(clientDto.getMarketCodes());
            throwExceptionIf(clientDto.getMarketCodes().size() != marketDtoList.size(), "client.marketNotExist");
            clientDto.setMarkets(marketDtoList);
        }
        this.clientDao.save(clientDto);
    }

    public void delete(String description) throws ServiceExceptionP {
        this.checkNull("generic.null", description);
        description = convertToTitleCase(description);
        this.throwExceptionIf(!this.clientDao.existsNotDeletedBy(description), "generic.notExist");
        this.clientDao.delete(description);
    }

    public ClientDTO update(String currentDescription, ClientDTO clientDto) throws ServiceExceptionP {
        this.checkNull("generic.null", currentDescription, clientDto);
        this.checkNull("generic.null", clientDto.getDescription());
        // Verifico la existencia del cliente
        currentDescription = convertToTitleCase(currentDescription);
        this.throwExceptionIf(!this.clientDao.existsNotDeletedBy(currentDescription), "generic.notExist");

        // Verifico si hay conflicto con la nueva descripción
        clientDto.setDescription(convertToTitleCase(clientDto.getDescription()));
        if (!currentDescription.equals(clientDto.getDescription())) {
            this.throwExceptionIf(this.clientDao.existsBy(clientDto.getDescription()), "client.alreadyExist");
        }

        // Obtengo el cliente existente y asocio mercados si existen códigos
        ClientDTO clientDtoDB = this.clientDao.find(currentDescription);
        List<MarketDTO> marketDtoList = new ArrayList<>();
        if (clientDto.getMarketCodes() != null) {
            marketDtoList = this.marketService.findAll(clientDto.getMarketCodes());
            throwExceptionIf(clientDto.getMarketCodes().size() != marketDtoList.size(), "client.marketNotExist");
            clientDto.setMarkets(marketDtoList);
        }
        clientDtoDB.setMarkets(marketDtoList);
        return this.clientDao.update(currentDescription, clientDto);
    }

    public ClientDTO find(String description) throws ServiceExceptionP {
        this.checkNull("generic.null", description);
        description = convertToTitleCase(description);
        ClientDTO clientDto = this.clientDao.find(description);
        this.checkNull("generic.notExist", clientDto);
        return clientDto;
    }

    public ClientDTO find(Long id) throws ServiceExceptionP {
        this.checkNull("generic.null", id);
        return this.clientDao.find(id);
    }

    public List<ClientDTO> findAll() throws ServiceExceptionP {
        return this.clientDao.findAll();
    }

    public Boolean existsBy(String description) throws ServiceExceptionP {
        this.checkNull("generic.null", description);
        return this.clientDao.existsBy(description);
    }

    public Boolean existsBy(Long id) throws ServiceExceptionP {
        this.checkNull("generic.null", id);
        return this.clientDao.existsBy(id);
    }

    public void recover(String description) throws ServiceExceptionP {
        this.checkNull("generic.null", description);
        description = convertToTitleCase(description);
        this.throwExceptionIf(!this.clientDao.existsDeletedBy(description), "client.notFoundDeleted");
        this.clientDao.recovery(description);
    }

}
