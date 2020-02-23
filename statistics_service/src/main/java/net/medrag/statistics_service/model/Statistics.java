package net.medrag.statistics_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@author} Stanislav Tretyakov
 * 22.02.2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Statistics {
    private Integer getAmountRequestQuantity;
    private Integer addAmountRequestQuantity;
}
