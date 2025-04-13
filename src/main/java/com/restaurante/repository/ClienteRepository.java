package restaurante.repository;

import com.restaurante.model.Cliente;
import org.hibernate.Session;
import org.hibernate.query.Query;
import java.util.List;

public class ClienteRepository extends AbstractRepository<Cliente> {
    public ClienteRepository() {
        super(Cliente.class);
    }

    public List<Cliente> findByNomeSimilar(String nome) {
        try (Session session = openSession()) {
            Query<Cliente> query = session.createQuery(
                "FROM Cliente c WHERE c.nome LIKE :nome", 
                Cliente.class);
            query.setParameter("nome", "%" + nome + "%");
            return query.getResultList();
        }
    }
}