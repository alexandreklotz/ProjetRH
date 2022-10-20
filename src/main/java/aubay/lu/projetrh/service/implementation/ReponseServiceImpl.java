package aubay.lu.projetrh.service.implementation;

import aubay.lu.projetrh.repository.ReponseRepository;
import aubay.lu.projetrh.service.ReponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReponseServiceImpl implements ReponseService {

    private ReponseRepository reponseRepository;

    @Autowired
    ReponseServiceImpl(ReponseRepository reponseRepository){
        this.reponseRepository = reponseRepository;
    }

}
