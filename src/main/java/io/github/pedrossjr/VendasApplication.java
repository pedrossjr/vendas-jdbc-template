package io.github.pedrossjr;

import io.github.pedrossjr.entity.Cliente;
import io.github.pedrossjr.repository.Clientes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class VendasApplication {

    @Bean
    public CommandLineRunner init (@Autowired Clientes clientes ){
        return args -> {
            System.out.println("------- Salvando base com novos clientes -------");

            clientes.salvar(new Cliente( "Pedro"));
            clientes.salvar(new Cliente( "Rodrigo"));
            clientes.salvar(new Cliente( "André"));
            clientes.salvar(new Cliente( "Rafael"));

            List<Cliente> todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println("------- Atualizando nome de clientes -------");
            todosClientes.forEach( c -> {
                c.setNome(c.getNome() + " - atualizado");
                clientes.atualizar(c);
            });

            todosClientes = clientes.obterTodos();
            todosClientes.forEach(System.out::println);

            System.out.println(" ------- Buscando todos os clientes -------");
            clientes.buscarPorNome("Cli").forEach(System.out::println);

            todosClientes = clientes.obterTodos();

            if(todosClientes.isEmpty()) {
                System.out.println("------- Nenhum cliente encontrado -------");
            } else {
                todosClientes.forEach(System.out::println);
            }

            clientes.buscarPorNome("Rafael").forEach( c -> {
                System.out.println("------- Deletando o cliente Rafael -------");
                clientes.deletar(c.getId());
            });

            System.out.println("------- Exibindo lista atualizada de clientes -------");

            todosClientes = clientes.obterTodos();

            if(todosClientes.isEmpty()) {
                System.out.println("------- Nenhum cliente encontrado -------");
            } else {
                todosClientes.forEach(System.out::println);
            }
        };
    }

    public static void main(String[] args) {

        SpringApplication.run(VendasApplication.class, args);
    }
}