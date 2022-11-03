package air.statisticsservice.web;

import air.statisticsservice.service.OrderStatistics;
import air.statisticsservice.service.StatisticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/statistics", produces = "application/json")
@CrossOrigin(origins = "*")
public class StatisticsController {
    private final StatisticsService statisticsService;

    @Autowired
    public StatisticsController(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping("/order")
    public OrderStatistics get() {
        return statisticsService.get()
                .orElseThrow(() -> new NoSuchElementException("No statistics found"));
    }
}
