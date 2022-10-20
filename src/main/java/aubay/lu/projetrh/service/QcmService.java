package aubay.lu.projetrh.service;

import aubay.lu.projetrh.model.Qcm;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QcmService {

    public List<Qcm> getAllQcm();

    public Optional<Qcm> getQcmById(UUID qcmId);

    public List<Qcm> getQcmByTitle(String title);

    public Qcm createQcm(Qcm qcm);

    public Qcm updateQcm(Qcm qcm);

    public String deleteQcm(UUID qcmId);

}
