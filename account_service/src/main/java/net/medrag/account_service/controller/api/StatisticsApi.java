package net.medrag.account_service.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import net.medrag.account_service.model.Statistics;
import org.springframework.http.ResponseEntity;

/**
 * Meant for providing request statistics to custom statistics service
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Api(value = "statistic", tags = {"Statistic operations"}, description = "Retrieve statistic")
public interface StatisticsApi {

    /**
     * @return {@link Statistics} object
     */
    @ApiOperation(value = "Retrieve statistics", nickname = "getStats")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation", response = Statistics.class) })
    ResponseEntity<Statistics> getStats();
}
