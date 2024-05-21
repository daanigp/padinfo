package com.backend.padinfo_backend.mappers;

import com.backend.padinfo_backend.model.entity.Role;
import com.backend.padinfo_backend.model.repository.IRoleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RolesListConverter extends AbstractConverter<List<Long>, List<Role>> {
    private final IRoleRepository roleRepository;

    public RolesListConverter(IRoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    protected List<Role> convert(List<Long> longs) {
        return (List<Role>) roleRepository.findAllById(longs);
    }
}
