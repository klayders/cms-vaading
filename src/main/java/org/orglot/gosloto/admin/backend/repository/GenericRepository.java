package org.orglot.gosloto.admin.backend.repository;

import org.orglot.gosloto.dao.managed.dao.ManagedEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.stereotype.Service;

@Service
public class GenericRepository<T extends ManagedEntity> {

  private Repositories repositories = null;

  public GenericRepository(ApplicationContext appContext) {
    repositories = new Repositories(appContext);
  }
  //
    public JpaRepository getRepository(AbstractPersistable entity) {
      return (JpaRepository) repositories.getRepositoryFor(entity.getClass()).orElse(null);
    }

  public Object save(AbstractPersistable entity) {
    return getRepository(entity).save(entity);
  }

  public Object findAll(AbstractPersistable entity) {
    return getRepository(entity).findAll();
  }

  public void delete(AbstractPersistable entity) {
    getRepository(entity).delete(entity);
  }

  public <T extends ManagedEntity> JpaRepository getRepository(Class<T> persistentEntity) {
    return (JpaRepository) repositories.getRepositoryFor(persistentEntity).orElse(null);
  }
}