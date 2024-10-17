package vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.services;

import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.entities.Role;
import vn.edu.iud.fit.lehoangkhang.week01_lab_lehoangkhang_21083791.repositories.RoleRepostory;

import java.util.List;
import java.util.Set;

public class RoleService {
    private RoleRepostory roleRepostory;

    public RoleService() {
        this.roleRepostory = new RoleRepostory();
    }

    public List<Role> getAllRolesByAccountId(String accountId) {
        return roleRepostory.getAllRolesByAccountId(accountId);
    }


}
