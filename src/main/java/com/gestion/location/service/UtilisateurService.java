package com.gestion.location.service;

import com.gestion.location.dao.UtilisateurDAO;
import com.gestion.location.entities.Utilisateur;
import com.gestion.location.config.JPAUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class UtilisateurService {
    private final EntityManager em = JPAUtil.getEntityManager();
    private final UtilisateurDAO dao = new UtilisateurDAO(em);

    public void ajouter(Utilisateur u) { dao.ajouter(u); }
    public void modifier(Utilisateur u) { dao.modifier(u); }
    public void supprimer(Utilisateur u) { dao.supprimer(u); }
    public Utilisateur trouverParId(Long id) { return dao.trouverParId(id); }
    public List<Utilisateur> lister() { return dao.lister(); }
    public List<Utilisateur> listerParRole(String role) { return dao.listerParRole(role); }
    public Utilisateur getById(Long id) {
        return trouverParId(id);
    }
    // Locataires
    public List<Utilisateur> listerLocataires() { return dao.listerParRole("LOCATAIRE"); }
}
