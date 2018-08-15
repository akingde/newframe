package com.newframe.services.user.userimpl;

import com.newframe.services.user.RoleService;
import com.newframe.services.user.UserService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author WangBin
 */
@Service
public class UserServiceImpl implements UserService {

    private Map<Integer, RoleService> roleServiceMap = new HashMap<>();

    public UserServiceImpl(List<RoleService> roleServices) {
        for (RoleService roleService : roleServices) {
            roleServiceMap.put(roleService.getRoleId(), roleService);
        }
    }

}
