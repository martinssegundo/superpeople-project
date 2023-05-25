package br.com.mrmaia.superpeope.storage.apis.api;

import br.com.mrmaia.superpeope.storage.adapters.SuperPeopleAdapter;
import br.com.mrmaia.superpeope.storage.adapters.SuperPeopleDTOAdapter;
import br.com.mrmaia.superpeope.storage.apis.dto.requests.SuperPeopleDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.responses.responses.SuperPeopleListResponseDTO;
import br.com.mrmaia.superpeope.storage.apis.dto.responses.responses.SuperPeopleResponseDTO;
import br.com.mrmaia.superpeope.storage.exceptions.BattleAttributeWithValueZeroException;
import br.com.mrmaia.superpeope.storage.exceptions.ExcessiveTotalBattleAttributesException;
import br.com.mrmaia.superpeope.storage.exceptions.InvalidNameException;
import br.com.mrmaia.superpeope.storage.exceptions.SuperPeopleNotFoundException;
import br.com.mrmaia.superpeope.storage.repositories.entities.SuperPeople;
import br.com.mrmaia.superpeope.storage.services.ISuperPeopleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("V1/super-people")
public class SuperPeopleAPI {

    @Autowired
    private ISuperPeopleService superPeopleService;

    @PostMapping("/new")
    public SuperPeopleResponseDTO add(@RequestBody SuperPeopleDTO superPeopleDTO)
            throws InvalidNameException, BattleAttributeWithValueZeroException,
            ExcessiveTotalBattleAttributesException {
        return SuperPeopleResponseDTO.builder()
                .data(
                        SuperPeopleDTOAdapter.convertTo(
                                superPeopleService.save(SuperPeopleAdapter.convertTo(superPeopleDTO))
                        )
                ).build();
    }

    @GetMapping("/find/{super-people}")
    public SuperPeopleListResponseDTO find(@PathVariable("super-people") String heroName)
        throws SuperPeopleNotFoundException {
        return SuperPeopleListResponseDTO.builder()
                .data(
                        SuperPeopleDTOAdapter.convertToList(
                                superPeopleService.findSuperPeopleByName(
                                        heroName)
                        )
                ).build();
    }
}
