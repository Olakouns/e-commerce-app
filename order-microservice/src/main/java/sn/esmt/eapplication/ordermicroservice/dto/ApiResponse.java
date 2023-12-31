package sn.esmt.eapplication.ordermicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse {
    private String message;
    private boolean success;
    private List<String> messages = new ArrayList<>();

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
