package com.esthetic.servicesmicroservices.services;

import com.esthetic.servicesmicroservices.dto.MenuServiceDTO;
import com.esthetic.servicesmicroservices.dto.ResponseDTO;
import com.esthetic.servicesmicroservices.entity.MenuService;
import com.esthetic.servicesmicroservices.repository.MenuServiceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuServiceSerices {
    private final MenuServiceRepository menuServiceRepository;
    public ResponseDTO _GetMenuServiceByIdUser(String idUser){
        return ResponseDTO.builder().items(
                menuServiceRepository.findByIdUser(idUser)
                        .stream()
                        .map(item -> new MenuServiceDTO(item))
                        .collect(Collectors.toList()))
                .build();
    }
    public ResponseDTO _GetMenuServiceById(Long idMenuService) {
        return ResponseDTO.builder().items(
                new MenuServiceDTO(
                        menuServiceRepository.findById(idMenuService).get()
                )).build();
    }
    public ResponseDTO _SaveMenuService(MenuServiceDTO menuServiceDTO) {
        menuServiceRepository.save(new MenuService(menuServiceDTO));
        return ResponseDTO.builder().message("Se guardo el registro con éxito").build();
    }
    public ResponseDTO _UpdateMenuService(MenuServiceDTO menuServiceDTO) {
        MenuService menuService = new MenuService(menuServiceDTO);
        menuService.setId(menuServiceDTO.id);
        menuServiceRepository.save(menuService);
        return ResponseDTO.builder().message("Se actualizo el registro con éxito").build();
    }
    public ResponseDTO _DeleteMenuService(String idUser, Long idMenuService) {
        Optional<MenuService> menuService = menuServiceRepository.findById(idMenuService);
        if(menuService.isPresent()) {
            if(menuService.get().getIdUser().equals(idUser)) {
                menuServiceRepository.delete(menuService.get());
                return ResponseDTO.builder().message("Registro eliminado con éxito").build();
            }
        }
        return ResponseDTO.builder().error(true).message("No se encontro el registro").build();
    }
}
