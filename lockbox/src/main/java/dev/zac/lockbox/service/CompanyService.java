package dev.zac.lockbox.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.zac.lockbox.entity.Company;
import dev.zac.lockbox.repository.impl.CompanyRepository;

@Service
public class CompanyService {

    @Autowired
    private CompanyRepository companyRepository;

    public Company createCompany(Company company) {
        companyRepository.save(company);
        return company;
    }

    public Company updateCompany(Company company) {
        companyRepository.update(company);
        return company;
    }

    public void deleteCompany(String id) {
        companyRepository.delete(id);
    }

    public Company getCompanyById(String id) throws Exception {
        return companyRepository.findById(id);
    }

    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    public List<Company> getCompaniesByUserId(String userId) {
        return companyRepository.findByUserId(userId);
    }
}
