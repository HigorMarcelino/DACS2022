package br.univille.apidacs2022;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import br.univille.coredacs2022.repository.UserRepository;
import br.univille.coredacs2022.entity.User;

@Component
public class Startup {

    @Autowired
    private UserRepository repository;
    
    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event){
        if (repository.findByUser("admin").isEmpty()) {
            var adminUser = new User();

            adminUser.setUser("admin");
            adminUser.setSenha("senha");
            repository.save(adminUser);

        }
    }
}
