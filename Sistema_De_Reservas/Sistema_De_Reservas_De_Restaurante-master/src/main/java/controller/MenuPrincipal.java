package controller;

import repository.ClienteRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


public class MenuPrincipal {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("nome-da-sua-persistence-unit");
        EntityManager em = emf.createEntityManager();

        ClienteRepository clienteRepo = new ClienteRepository(em); //


        em.close();
        emf.close();
    }
}

