package com.gestion.location.dao;

import com.gestion.location.entities.Locataire;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class LocataireDAO {

    private final EntityManager em;

    public LocataireDAO(EntityManager em) {
        this.em = em;
    }

    //  Ajouter un locataire
    public void create(Locataire locataire) {
        try {
            em.getTransaction().begin();
            em.persist(locataire);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    //  Modifier un locataire
    public void update(Locataire locataire) {
        try {
            em.getTransaction().begin();
            em.merge(locataire);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    //  Supprimer un locataire
    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            Locataire locataire = em.find(Locataire.class, id);
            if (locataire != null) {
                em.remove(locataire);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    //  Rechercher par ID
    public Locataire findById(Long id) {
        return em.find(Locataire.class, id);
    }

    //  Lister tous les locataires
    public List<Locataire> findAll() {
        TypedQuery<Locataire> query =
                em.createQuery("SELECT l FROM Locataire l", Locataire.class);
        return query.getResultList();
    }

    //  Rechercher par nom
    public List<Locataire> findByNom(String nom) {
        TypedQuery<Locataire> query =
                em.createQuery("SELECT l FROM Locataire l WHERE l.nom LIKE :nom", Locataire.class);
        query.setParameter("nom", "%" + nom + "%");
        return query.getResultList();
    }
}
