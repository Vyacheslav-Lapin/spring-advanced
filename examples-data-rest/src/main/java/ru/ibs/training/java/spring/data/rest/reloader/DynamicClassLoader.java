package ru.ibs.training.java.spring.data.rest.reloader;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

@Service
@RequiredArgsConstructor
public class DynamicClassLoader {

    ApplicationContext applicationContext;
    RequestMappingHandlerMapping requestMappingHandlerMapping;

    static class ReloadClassLoader extends URLClassLoader {
        static ReloadClassLoader INSTANCE;
        private static final Set<String> classNamesToReload = new HashSet<>();
        private Map<String, Class<?>> loadedClasses = new HashMap<>();

        /**
         * Return actual class for className - from original loader or from ReloadClassLoader
         * if it was already used
         * @param clazz
         * @return
         * @throws ClassNotFoundException
         */
        static Class<?> forName(String clazz) throws ClassNotFoundException {
          return INSTANCE != null && classNamesToReload.contains(clazz) ?
                 INSTANCE.loadClass(clazz) :
                 Class.forName(clazz);
        }

        static void addClassToReload(String clazz) {
            System.out.println("Added "+clazz+" to reload");
            classNamesToReload.add(clazz);
        }

        public ReloadClassLoader(URL[] urls, ClassLoader parent) {
            super(urls, parent);
        }

        public static ReloadClassLoader getInstance(Class<?> loadedClass) throws ClassNotFoundException, MalformedURLException {
            URL url = loadedClass.getProtectionDomain().getCodeSource().getLocation();
            String sURL = url.getPath().replaceAll("%20", " ");
            URL[] urls = new URL[]{new URL("file:///" + sURL)};
            final ClassLoader delegateParent = loadedClass.getClassLoader();
            ReloadClassLoader reloadClassLoader = new ReloadClassLoader(urls, delegateParent);
            for (String c: classNamesToReload) {
                reloadClassLoader.getClass(c);
            }
            INSTANCE = reloadClassLoader;
            return reloadClassLoader;
        }

        public Class<?> getClass(String clazz) throws ClassNotFoundException {
            if (loadedClasses.keySet().contains(clazz)) {
                return loadedClasses.get(clazz);
            }
            Class<?> loadedClass = findClass(clazz);
            loadedClasses.put(clazz, loadedClass);
            return loadedClass;
        }

    }

    public Map<String, Set<String>> getBeanDependencies() {
        Map<String, Set<String>> beanDeps = new TreeMap<>();
        ConfigurableListableBeanFactory factory = (
                (GenericApplicationContext) applicationContext).getBeanFactory();
        for (String beanName : factory.getBeanDefinitionNames()) {
            if (factory.getBeanDefinition(beanName).isAbstract()) {
                continue;
            }
            String[] dependenciesForBean = factory.getDependenciesForBean(beanName);
            Set<String> set = beanDeps.get(beanName);
            if (set == null) {
                set = new TreeSet<>();
                beanDeps.put(beanName, set);
            }
            for (String dependency : dependenciesForBean) {
                set.add(dependency);
            }
        }
        return beanDeps;
    }

    public Map<String, Set<String>> getReverseBeanDependencies() {
        Map<String, Set<String>> reverseBeanDeps = new TreeMap<>();
        Map<String, Set<String>> beanDeps = getBeanDependencies();

        beanDeps.forEach((beanName, deps) -> {
            for (String dep : deps) {
                Set<String> set = reverseBeanDeps.get(dep);
                if (set == null) {
                    set = new TreeSet<>();
                    reverseBeanDeps.put(dep, set);
                }
                set.add(beanName);
            }
        });

        return reverseBeanDeps;
    }

    public Class<?> reloadBean(String clazz) throws ClassNotFoundException, MalformedURLException {

        // get the last loaded class of the bean
        Class<?> loadedClass = ReloadClassLoader.forName(clazz);
        // create a new class loader
        ReloadClassLoader reloadClassLoader = ReloadClassLoader.getInstance(loadedClass);

        // unregister all mappings
        Set<RequestMappingInfo> mappingInfoSet = new HashSet<>();
        mappingInfoSet.addAll(requestMappingHandlerMapping.getHandlerMethods().keySet());
        for (RequestMappingInfo mappingInfo : mappingInfoSet) {
            requestMappingHandlerMapping.unregisterMapping(mappingInfo);
        }

        // remove all dependencies and all reverseDependencies beans
        // and keep it for reloading and re-registration
        Set<String> beanDependencies = new HashSet<>();
        Set<String> beanReverseDevendencies = new HashSet<>();

        Map<String, Set<String>> reverseBeanDependencies = getReverseBeanDependencies();
        ConfigurableListableBeanFactory factory =
                ((GenericApplicationContext) applicationContext).getBeanFactory();
        String[] beanNames = factory.getBeanNamesForType(loadedClass);
        for (String beanName: beanNames) {
            Set<String> reverseDependenciesForBean = reverseBeanDependencies.get(beanName);
            String[] dependenciesForBean = factory.getDependenciesForBean(beanName);
            for (String dependencyBean: dependenciesForBean) {
                Object bean = factory.getBean(dependencyBean);
                removeBean(bean.getClass());
                beanDependencies.add(bean.getClass().getName());
            }
            if (reverseDependenciesForBean != null) {
                for (String reverseDependencyBean : reverseDependenciesForBean) {
                    Object bean = factory.getBean(reverseDependencyBean);
                    removeBean(bean.getClass());
                    beanReverseDevendencies.add(bean.getClass().getName());
                }
            }
        }

        // remove old bean and reload the class of the bean
        removeBean(loadedClass);

        Class<?> reloadedClass = reloadClassLoader.loadClass(loadedClass.getName());

        // reload and register all the depencencies of the bean
        for (String beanClassName: beanDependencies) {
            Class<?> beanClass = reloadClassLoader.getClass(beanClassName);
            addBean(beanClass);
        }

        // register the bean itself
        addBean(reloadedClass);

        // reload and register all the reverse depencencies
        for (String beanClassName: beanReverseDevendencies) {
            Class<?> beanClass = reloadClassLoader.getClass(beanClassName);
            addBean(beanClass);
        }

        //AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
        factory.autowireBean(requestMappingHandlerMapping);

        // register all mappings again
        requestMappingHandlerMapping.afterPropertiesSet();

        return reloadedClass;
    }

    public void addBean(Class<?> loadedClass) {
        AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
        String className = loadedClass.getSimpleName();
        String beanName = Character.toLowerCase(className.charAt(0))
                + className.substring(1);
//        String beanName = loadedClass.getName();
//        factory.applyBeanPostProcessorsBeforeInitialization(bean, beanName);
//        factory.initializeBean(bean, beanName);
//        factory.applyBeanPostProcessorsAfterInitialization(bean, beanName);
//        factory.configureBean(bean, beanName);
//        factory.autowireBean(bean);
        ConfigurableApplicationContext context =
                (ConfigurableApplicationContext) applicationContext;

        ((DefaultSingletonBeanRegistry) context.getBeanFactory())
                .destroySingleton(beanName);

        Object bean = factory
                .createBean(loadedClass,
                        AutowireCapableBeanFactory.AUTOWIRE_NO,
                        false);

        context.getBeanFactory().registerSingleton(beanName, bean);

        factory.configureBean(bean, beanName);
        factory.autowireBean(bean);

//        ((DefaultSingletonBeanRegistry) context.getBeanFactory())
//                .destroySingleton("personController");
//
//        Object bean2 = factory
//                .createBean(PersonController.class,
//                        AutowireCapableBeanFactory.AUTOWIRE_BY_TYPE,
//                        false);
//
//        context.getBeanFactory().registerSingleton("personController", bean2);
//
        // TODO: take a list of controllers from request mappings of @Run
//        if (!beanName.equals("personController")) {
//            PersonController personController = factory.getBean(PersonController.class);
//            factory.configureBean(personController, "personController");
//            factory.autowireBean(personController);
//        }

        if (isApplicationListener(loadedClass)) {
            context.addApplicationListener((ApplicationListener<?>) bean);
        }
    }

    private boolean isApplicationListener(Class<?> loadedClass) {
        Class<?>[] interfaces = loadedClass.getInterfaces();
        for (Class<?> i: interfaces) {
            if (i.getSimpleName().equals("ApplicationListener")) return true;
        }
        return false;
    }

    public void removeBean(Class<?> loadedClass) {
        AutowireCapableBeanFactory factory = applicationContext.getAutowireCapableBeanFactory();
        if (applicationContext.getBeansOfType(loadedClass).size() == 0) return;
        Object bean = applicationContext.getBean(loadedClass);
        ApplicationEventMulticaster aem = applicationContext.getBean(
                AbstractApplicationContext.APPLICATION_EVENT_MULTICASTER_BEAN_NAME,
                ApplicationEventMulticaster.class);

        if (isApplicationListener(loadedClass)) {
            aem.removeApplicationListener((ApplicationListener<?>) bean);
        }
        String className = loadedClass.getSimpleName();
        String beanName = Character.toLowerCase(className.charAt(0))
                + className.substring(1);
        System.out.println("removing bean "+beanName);
        aem.removeApplicationListenerBean(beanName);

        factory.destroyBean(bean);
        ConfigurableApplicationContext context =
                (ConfigurableApplicationContext) applicationContext;
        ((DefaultSingletonBeanRegistry) context.getBeanFactory())
                .destroySingleton(beanName);
    }

}
