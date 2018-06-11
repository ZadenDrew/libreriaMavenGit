package com.mycompany.maven;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.JGitInternalException;

/**
 *
 * @author acabezaslopez
 */
public class Maven {

    /**
     * @param args the command line arguments
     * @throws org.eclipse.jgit.api.errors.GitAPIException
     */
    public static void main(String[] args) throws GitAPIException, IOException {

        Metodos m = new Metodos();
        int op;
        do {
            op = Integer.parseInt(JOptionPane.showInputDialog("***MENU***\n"
                    + "1) Crear Repositorio\n"
                    + "2) Clonar Repositorio\n"
                    + "3) Inicializar Repositorio\n"
                    + "4) Commit\n"
                    + "5) Realizar Push\n"
                    + "6. Salir"));
            switch (op) {
                case 1:
                    try {
                        m.crearRepositorio();

                    } catch (IOException ex) {
                        Logger.getLogger(Maven.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    break;
                case 2:
                    try {
                        m.clonar();
                    } catch (IOException ex) {
                        Logger.getLogger(Maven.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    break;
                case 3:
                    try {
                        m.inicializarRepo();
                    } catch (IOException ex) {
                        Logger.getLogger(Maven.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    break;
                case 4:
                    m.commit();
                    break;
                case 5:  {
                    m.realizaPush();
                }

                break;
                case 6:
                    System.exit(0);
                    break;
            }

        } while (op != 6);

    }
}
