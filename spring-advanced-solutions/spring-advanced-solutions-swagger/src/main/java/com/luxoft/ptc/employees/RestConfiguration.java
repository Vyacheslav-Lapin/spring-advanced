package com.luxoft.ptc.employees;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RestConfiguration {

  EntityManager entityManager;

  //    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**");
//            }
//        };
//    }
//
//    @Bean
//    public Docket api() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .tags(new Tag("Employee Entity",
//                        "Employee entities repository"))
//                .tags(new Tag("Employee Controller",
//                        "Utility methods for Employee entities"))
//                .tags(new Tag("Task Entity",
//                        "Task entities repository"))
//                .tags(new Tag("Tasks Controller",
//                        "Utility methods for Task repository"))
//                .select()
//                .apis(RequestHandlerSelectors.any())
//                //.apis(RequestHandlerSelectors.basePackage("com.luxoft.ptc.employees.service"))
//                .apis(filterAPI())
//                .build()
//                .apiInfo(metaData());
//    }
//
//    private ApiInfo metaData() {
//        ApiInfo apiInfo = new ApiInfo(
//                "Employee & Tasks application REST API",
//                "Employee & Tasks application for React School",
//                "1.0",
//                null,
//                null,
//                null,
//                null,
//                new ArrayList<>());
//        return apiInfo;
//    }

//  private Predicate<RequestHandler> filterAPI() {
//    return input -> {
//      Set<RequestMethod> methods = input.supportedMethods();
//      String path = input.getPatternsCondition().getPatterns().iterator().next();
//      if (path.equals("/error") ||
//          path.endsWith("subordinates/{employeeId}") ||
//          path.endsWith("tasks/{taskId}") ||
//          path.endsWith("createdTasks/{taskId}")
//      ) return false;
//      if ((path.equals("/employees/findByExample") ||
//           path.equals("/tasks/findByExample"))
//          && methods.contains(RequestMethod.GET)) return false;
//      // ONLY GET endpoint
//      if ((
//              path.endsWith("manager") ||
//              path.contains("subordinates") ||
//              path.endsWith("responsible") ||
//              path.endsWith("author") ||
//              path.contains("createdTasks") ||
//              path.endsWith("/tasks") ||
//              path.endsWith("/tasks/findByExample")
//          ) && !methods.contains(RequestMethod.GET)) return false;
//      return true;
//    };
//  }

//  public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
//    config.exposeIdsFor(
//        entityManager.getMetamodel().getEntities().stream()
//                     .map(Type::getJavaType)
//                     .toArray(Class[]::new));
//  }
}
