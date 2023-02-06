package aubay.lu.projetrh.controller;

import aubay.lu.projetrh.model.Roles;
import aubay.lu.projetrh.repository.RolesRepository;
import aubay.lu.projetrh.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class RolesController {

    private RolesRepository rolesRepository;

    @Autowired
    RolesController(RolesRepository rolesRepository){
        this.rolesRepository = rolesRepository;
    }

    @JsonView(CustomJsonView.RolesView.class)
    @GetMapping("admin/roles/all")
    public List<Roles> getAllRoles(){
        return rolesRepository.findAll();
    }

}
