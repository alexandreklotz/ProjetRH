package aubay.lu.projetrh.controller;

import aubay.lu.projetrh.model.Qcm;
import aubay.lu.projetrh.service.QcmService;
import aubay.lu.projetrh.view.CustomJsonView;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin
public class QcmController {

    private QcmService qcmService;

    @Autowired
    QcmController(QcmService qcmService){
        this.qcmService = qcmService;
    }


    @JsonView(CustomJsonView.QcmView.class)
    @GetMapping("admin/qcm/all")
    public List<Qcm> getAllQcm(){
        return qcmService.getAllQcm();
    }

    @JsonView(CustomJsonView.QcmView.class)
    @GetMapping("admin/qcm/id/{qcmId}")
    public Optional<Qcm> getQcmById(@PathVariable UUID qcmId){
        return qcmService.getQcmById(qcmId);
    }

    @JsonView(CustomJsonView.QcmView.class)
    @GetMapping("admin/qcm/title/{title}")
    public List<Qcm> getQcmByTitle(@PathVariable String title){
        return qcmService.getQcmByTitle(title);
    }

    @JsonView(CustomJsonView.QcmView.class)
    @PostMapping("admin/qcm/create")
    public Qcm createQcm(@RequestBody Qcm qcm){
        return qcmService.createQcm(qcm);
    }

    @JsonView(CustomJsonView.QcmView.class)
    @PutMapping("admin/qcm/update")
    public Qcm updateQcm(@RequestBody Qcm qcm){
        return qcmService.updateQcm(qcm);
    }

    @JsonView(CustomJsonView.QcmView.class)
    @DeleteMapping("admin/qcm/delete/{qcmId}")
    public String deleteQcm(@PathVariable UUID qcmId){
        return qcmService.deleteQcm(qcmId);
    }

}
