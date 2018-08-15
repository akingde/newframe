package com.newframe.services.user.roleImpl;

import com.newframe.enums.RoleEnum;
import com.newframe.services.user.RoleService;
import org.springframework.stereotype.Service;

/**
 * @author WangBin
 */
@Service
public class HirerServiceImpl implements RoleService {

    @Override
    public Integer getRoleId() {
        return RoleEnum.HIRER.getRoleId();
    }
}
