package sn.esmt.eapplication.productmicroservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private boolean success;
    private String message;
    private List<String> messages = new ArrayList<>();

    public ApiResponse(boolean success, String message) {
        this.success = success;
        this.message = message;
    }
}
