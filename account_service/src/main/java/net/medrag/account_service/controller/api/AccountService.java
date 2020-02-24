package net.medrag.account_service.controller.api;

import io.swagger.annotations.*;

/**
 * Requirement api
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Api(value = "amount", tags = {"Amount operations"}, description = "Add amount, get amount")
public interface AccountService {
    /**
     * Retrieves current balance or zero if addAmount() method was not called before for specified id
     *
     * @param id balance identifier
     *               
     */
    @ApiOperation(value = "Get current amount", nickname = "getAmount")
    @ApiResponses(value = {
            @ApiResponse(code = 555, message = "database error")})
    Long getAmount(@ApiParam(value = "Id of account - amount owner", required = true) Integer id);

    /**
     * Increases balance or set if addAmount() method was called first time
     *
     * @param id    balance identifier
     * @param value positive or negative value, which must be added to current balance
     *                  
     */
    @ApiOperation(value = "Add additional amount", nickname = "addAmount")
    @ApiResponses(value = {
            @ApiResponse(code = 555, message = "database error")})
    void addAmount(@ApiParam(value = "Id of account - amount owner", required = true) Integer id,
                   @ApiParam(value = "Amount value, that should be added", required = true) Long value);
}
