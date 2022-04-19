package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.RoleDAO;
import ru.kata.spring.boot_security.demo.model.Role;

import javax.transaction.Transactional;
import java.util.Set;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {


    private RoleDAO roleDAO;

    public RoleServiceImpl(RoleDAO roleDao) {
        this.roleDAO = roleDao;
    }

    @Override
    public Set<Role> getAllRoles() {
        return roleDAO.getAllRoles();
    }

    @Override
    public Role getRoleById(int id) {
        return roleDAO.getRoleById(id);
    }

    @Override
    public void save(Role role) {
        roleDAO.save(role);
    }

    @Override
    public void update(int id, Role updatedRole) {

        roleDAO.update(id, updatedRole);
    }

    @Override
    public void delete(int id) {
        roleDAO.delete(id);

    }
}
