package com.gestion.location.service;

import com.gestion.location.dao.UniteDAO;
import com.gestion.location.entities.Unite;

import java.util.List;

public class UniteService {

    private final UniteDAO uniteDAO = new UniteDAO();

    public void ajouter(Unite unite) {
        uniteDAO.save(unite);
    }

    public void modifier(Unite unite) {
        uniteDAO.update(unite);
    }

    public void supprimer(Integer id) {
        uniteDAO.delete(id);
    }

    public Unite getById(Integer id) {
        return uniteDAO.findById(id);
    }

    public List<Unite> lister() {
        return uniteDAO.findAll();
    }

    public List<Unite> listerParImmeuble(Integer immeubleId) {
        return uniteDAO.findByImmeubleId(immeubleId);
    }
}
