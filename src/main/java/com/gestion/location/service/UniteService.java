package com.gestion.location.service;

import com.gestion.location.dao.UniteDAO;
import com.gestion.location.entities.Immeuble;
import com.gestion.location.entities.Unite;
import com.gestion.location.util.JpaUtil;
import jakarta.persistence.EntityManager;
import java.util.List;

public class UniteService {
    private final EntityManager entityManager;
    private final UniteDAO uniteDAO;

    public UniteService() {
        this.entityManager = JpaUtil.getEntityManager();
        this.uniteDAO = new UniteDAO(entityManager);
    }

    public Unite creerUnite(String numeroUnite, int nombrePieces, double superficie,
                            double loyerMensuel, String description, Immeuble immeuble) {
        try {
            Unite unite = new Unite(numeroUnite, nombrePieces, superficie, loyerMensuel, description);
            unite.setImmeuble(immeuble);

            uniteDAO.create(unite);
            return unite;
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de l'unité: " + e.getMessage(), e);
        }
    }

    public Unite trouverUniteParId(Long id) {
        try {
            return uniteDAO.findById(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche de l'unité: " + e.getMessage(), e);
        }
    }

    public List<Unite> listerToutesLesUnites() {
        try {
            return uniteDAO.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des unités: " + e.getMessage(), e);
        }
    }

    public List<Unite> listerUnitesParImmeuble(Immeuble immeuble) {
        try {
            return uniteDAO.findByImmeuble(immeuble);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des unités par immeuble: " + e.getMessage(), e);
        }
    }

    public List<Unite> listerUnitesDisponibles() {
        try {
            return uniteDAO.findDisponibles();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du listing des unités disponibles: " + e.getMessage(), e);
        }
    }

    public List<Unite> trouverUnitesParNombrePieces(int nombrePieces) {
        try {
            return uniteDAO.findByNombrePieces(nombrePieces);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des unités par nombre de pièces: " + e.getMessage(), e);
        }
    }

    public List<Unite> trouverUnitesParPlageLoyer(double min, double max) {
        try {
            return uniteDAO.findByLoyerBetween(min, max);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la recherche des unités par plage de loyer: " + e.getMessage(), e);
        }
    }

    public void modifierUnite(Unite unite) {
        try {
            uniteDAO.update(unite);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification de l'unité: " + e.getMessage(), e);
        }
    }

    public void supprimerUnite(Long id) {
        try {
            uniteDAO.delete(id);
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la suppression de l'unité: " + e.getMessage(), e);
        }
    }

    public void marquerUniteCommeDisponible(Long id, boolean disponible) {
        try {
            Unite unite = uniteDAO.findById(id);
            if (unite != null) {
                unite.setEstDisponible(disponible);
                uniteDAO.update(unite);
            }
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la modification du statut de l'unité: " + e.getMessage(), e);
        }
    }

    public void close() {
        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }
}