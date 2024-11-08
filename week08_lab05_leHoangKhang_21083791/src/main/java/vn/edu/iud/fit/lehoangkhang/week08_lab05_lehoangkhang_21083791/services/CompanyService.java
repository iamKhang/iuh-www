package vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.models.Company;
import vn.edu.iud.fit.lehoangkhang.week08_lab05_lehoangkhang_21083791.repositories.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company findById(Long id) {
        return companyRepository.findById(id).orElse(null);
    }


}
