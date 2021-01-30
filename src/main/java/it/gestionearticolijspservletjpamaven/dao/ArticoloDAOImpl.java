package it.gestionearticolijspservletjpamaven.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;


import it.gestionearticolijspservletjpamaven.model.Articolo;

public class ArticoloDAOImpl implements ArticoloDAO {

	private EntityManager entityManager;

	@Override
	public List<Articolo> list() throws Exception {
		return entityManager.createQuery("from Articolo", Articolo.class).getResultList();
	}

	@Override
	public Articolo findOne(Long id) throws Exception {
		return entityManager.find(Articolo.class, id);
	}

	@Override
	public void update(Articolo input) throws Exception {
		if (input == null)
			throw new Exception("Problema valore in input");

		entityManager.merge(input);

	}

	@Override
	public void insert(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.persist(input);
	}

	@Override
	public void delete(Articolo input) throws Exception {
		if (input == null) {
			throw new Exception("Problema valore in input");
		}
		entityManager.remove(entityManager.merge(input));

	}

	public List<Articolo> findByExample(Articolo input) throws Exception {
		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		String queryBase = "select a from Articolo where 1 = 1";

		TypedQuery<Articolo> query = entityManager.createQuery(queryBase, Articolo.class);

		if (input.getCodice() != null && !input.getCodice().isEmpty()) {
			queryBase += " and codice = '" + input.getCodice() + "'";
		}

		if (input.getDescrizione() != null && !input.getDescrizione().isEmpty()) {
			queryBase += " and descrizione = '" + input.getDescrizione() + "'";
		}

		if (input.getPrezzo() != null) {
			queryBase += " and prezzo = '" + input.getPrezzo() + "'";
		}

		if (input.getDataArrivo() != null) {
			queryBase += " and dataarrivo = '" + new java.sql.Date(input.getDataArrivo().getTime()) + "'";
		}

		return query.getResultList();

	}

	@Override
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

}
