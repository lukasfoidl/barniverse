package at.barniverse.backend.barniverse_backend.controller;

import at.barniverse.backend.barniverse_backend.dto.StatisticDto;
import at.barniverse.backend.barniverse_backend.exception.BarniverseException;
import at.barniverse.backend.barniverse_backend.services.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static at.barniverse.backend.barniverse_backend.configuration.Context.CORS_ORIGINS;

/**
 * controller with statistic routing
 */
@RestController
@CrossOrigin(origins = CORS_ORIGINS)
@RequestMapping(path = "/api")
public class StatisticController {

    @Autowired private StatisticService statisticService;

    @GetMapping(path="/statistics")
    public ResponseEntity<Object> getStatistics() throws BarniverseException {
        List<StatisticDto> results = statisticService.getStatistics();
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}
