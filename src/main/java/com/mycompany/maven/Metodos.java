package com.mycompany.maven;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.AddCommand;
import org.eclipse.jgit.api.CommitCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.InitCommand;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.RemoteAddCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.kohsuke.github.GHCreateRepositoryBuilder;
import org.kohsuke.github.GitHub;

/**
 *
 * @author acabezaslopez
 */
public class Metodos {

    private static String localPath, remotePath, nombreRepositorio, commitmensaje;
    private static Repository localRepo;
    private static Git git;
    private static CredentialsProvider cp;
    private static String name = "username";
    private static String password = "password";
/**
 * Método que crea un repositorio en github con el nombre requerido.
 * @throws IOException 
 */
    //CREAR REPOSITORIO REMOTO GITHUB
    public void crearRepositorio() throws IOException {
        nombreRepositorio = JOptionPane.showInputDialog("Introduce nombre del nuevo repositorio");
        GitHub github = GitHub.connect();
        GHCreateRepositoryBuilder builder;
        builder = github.createRepository(nombreRepositorio);
        builder.create();
    }
/**
 * Método que clona un repositorio de github
 * @throws IOException
 * @throws GitAPIException 
 */
    //CLONAR
    public void clonar() throws IOException, GitAPIException {
        nombreRepositorio = JOptionPane.showInputDialog("Introduce nombre del nuevo repositorio");
        remotePath = JOptionPane.showInputDialog("Introduce dirección del repositorio en github");

        Git.cloneRepository()
                .setURI(remotePath)
                .setDirectory(new File(localPath + "/.git"))
                .setCloneAllBranches(true)
                .call();

    }
/**
 * Método para realizar commit en un proyecto de netbeans
 * @throws GitAPIException
 * @throws IOException 
 */
    //COMMIT(AÑADIR FICHERO)
    public void commit() throws GitAPIException, IOException {
        localPath = JOptionPane.showInputDialog("Introduce dirección del repositorio de Netbeans");
        remotePath = JOptionPane.showInputDialog("Introduce dirección del repositorio en github");
        commitmensaje = JOptionPane.showInputDialog("Introduce mensaje ha mostrar como commit");
        FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();

        Repository repository = repositoryBuilder.setGitDir(new File(localPath + "/.git"))
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .setMustExist(true)
                .build();
        git = new Git(localRepo);
        AddCommand add = git.add();
        add.addFilepattern(remotePath + "/.git");
        CommitCommand commit = git.commit();
        commit.setMessage(commitmensaje);
        commit.call();

    }
/**
 * Método que inicializa repositorio en netbeans
 * @throws IOException
 * @throws GitAPIException 
 */
    //INICIALIZAR REPOSITORIO
    public void inicializarRepo() throws IOException, GitAPIException {
        localPath = JOptionPane.showInputDialog("Introduce dirección del repositorio de Netbeans");
        InitCommand ini = new InitCommand();
        ini.setDirectory(new File(localPath))
                .call();

    }
/**
 * Método que realiza un push de un proyecto de netbeans a github
 * @throws IOException
 * @throws JGitInternalException
 * @throws InvalidRemoteException
 * @throws GitAPIException 
 */
//    PUSH
    public void realizaPush() throws IOException, JGitInternalException, InvalidRemoteException, GitAPIException {
        cp = new UsernamePasswordCredentialsProvider(Metodos.name, Metodos.password);
        git = new Git(localRepo);
        remotePath = JOptionPane.showInputDialog("Introduce la dirección del repositorio:");
        PushCommand pc = git.push();
        pc.setCredentialsProvider(cp)
                .setForce(true)
                .setPushAll();
        try {
            Iterator<PushResult> it = pc.call().iterator();
            if (it.hasNext()) {
                System.out.println(it.next().toString());
            }
        } catch (InvalidRemoteException e) {
            e.printStackTrace();
        }
    }
}
/**
 * 
 */
//    public  void push() {
//        try {
//            FileRepositoryBuilder repositoryBuilder = new FileRepositoryBuilder();
//            Repository repository = repositoryBuilder.setGitDir(new File(nombreRepositorio))
//                    .readEnvironment()
//                    .findGitDir()
//                    .setMustExist(true)
//                    .build();
//
//            Git git = new Git(repository);
//
//            RemoteAddCommand remoteAddCommand = git.remoteAdd();
//            remoteAddCommand.setName("origin");
//
//            remoteAddCommand.setUri(new URIish(remotePath));
//
//            remoteAddCommand.call();
//
//            PushCommand pushCommand = git.push();
//            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(name, password));
//            pushCommand.call();
//
//        } catch (IOException ex) {
//            System.out.println("Error: " + ex);
//        } catch (URISyntaxException ex) {
//            System.out.println("Error: " + ex);
//        } catch (GitAPIException ex) {
//            System.out.println("Error: " + ex);
//        }
//    }
//}
//public void push() throws URISyntaxException, GitAPIException {
//        try {
//            localRepo = new FileRepository(localPath);
//            git = new Git(localRepo);
//            String pathRepo = JOptionPane.showInputDialog("Introduce la dirección del repositorio:");
//            // Add repositorio remoto:
//            RemoteAddCommand remoteAddCommand = git.remoteAdd();
//            remoteAddCommand.setName("origin");
//            remoteAddCommand.setUri(new URIish(pathRepo));
//            remoteAddCommand.call();
//            // Se hace el push al remoto:
//            PushCommand pushCommand = git.push();
//            pushCommand.setCredentialsProvider(new UsernamePasswordCredentialsProvider(name, password));
//            pushCommand.call();
//        } catch (IOException ex) {
//            Logger.getLogger(Metodos.class.getName()).log(Level.SEVERE, null, ex);
//        }
//}



