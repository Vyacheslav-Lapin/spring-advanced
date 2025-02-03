package ru.ibs.training.java.spring.task01.utils;

import lombok.experimental.UtilityClass;
import org.hibernate.proxy.HibernateProxy;

@UtilityClass
public class HibernateUtils {

  @SuppressWarnings("unchecked")
  public static <T> Class<T> effectiveClass(T o) {
    return (Class<T>) (o instanceof HibernateProxy proxy ?
                       proxy.getHibernateLazyInitializer().getPersistentClass() :
                       o.getClass());
  }
}
