package course18.homework.budgetapp.model;

import lombok.Builder;
import lombok.With;

@Builder(toBuilder = true)
@With
public record Transaction(
        String id,
        String product,
        TransactionType type,
        Double amount
) {
}
