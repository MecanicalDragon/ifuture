package net.medrag.account_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Data
@AllArgsConstructor
public final class Statistics {
    private final Integer getAmountRequestQuantity;
    private final Integer addAmountRequestQuantity;
}
