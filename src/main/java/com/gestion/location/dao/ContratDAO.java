package com.gestion.location.dao;

import com.gestion.location.entities.Contrat;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class ContratDAO {

    private final EntityManager em;

    public ContratDAO(EntityManager em) {
        this.em = em;
    }

    public void create(Contrat contrat) {
        try {
            em.getTransaction().begin();
            em.persist(contrat);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void update(Contrat contrat) {
        try {
            em.getTransaction().begin();
            em.merge(contrat);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public void delete(Long id) {
        try {
            em.getTransaction().begin();
            Contrat contrat = em.find(Contrat.class, id);
            if (contrat != null) {
                em.remove(contrat);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
        }
    }

    public Contrat findById(Long id) {
        return em.find(Contrat.class, id);
    }

    public List<Contrat> findAll() {
        TypedQuery<Contrat> query = em.createQuery("SELECT c FROM Contrat c", Contrat.class);
        return query.getResultList();
    }

    public List<Contrat> findByLocataire(Long locataireId) {
        TypedQuery<Contrat> query = em.createQuery(
                "SELECT c FROM Contrat c WHERE c.locataire.id = :locataireId", Contrat.class);
        query.setParameter("locataireId", locataireId);
        return query.getResultList();
    }
}
