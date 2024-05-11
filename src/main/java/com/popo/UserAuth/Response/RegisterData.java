package com.popo.UserAuth.Response;


import com.popo.UserAuth.Enum.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class RegisterData {
    Role[] roles;

}
