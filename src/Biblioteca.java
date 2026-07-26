import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class Biblioteca extends JPanel {

    private List<Livro> livro = new ArrayList<>();
    private List<Usuario> usuario = new ArrayList<>();

    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JTextField campoQuantidade;
    private JTextField campoEscolha;
    private JTextField campoDevolver;

    public Biblioteca() {

        setLayout(null);

        LocalTime hora = LocalTime.now();
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("HH:mm");

        JOptionPane.showMessageDialog(null, "Horário de Brasília: " + formato.format(hora));

        criarTela();
    }

    private void criarTela() {

        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setBounds(30, 20, 80, 25);

        campoEmail = new JTextField();
        campoEmail.setBounds(120, 20, 200, 25);

        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setBounds(30, 60, 80, 25);

        campoSenha = new JPasswordField();
        campoSenha.setBounds(120, 60, 200, 25);

        JButton cadastrar = new JButton("Cadastrar");
        cadastrar.setBounds(30, 100, 130, 30);

        JButton entrar = new JButton("Login");
        entrar.setBounds(190, 100, 130, 30);

        add(labelEmail);
        add(campoEmail);
        add(labelSenha);
        add(campoSenha);
        add(cadastrar);
        add(entrar);

        cadastrar.addActionListener(e -> cadastrarUsuario());
        entrar.addActionListener(e -> login());

        JLabel labelQuantidade = new JLabel("Quantidade de livros:");
        labelQuantidade.setBounds(30, 170, 180, 25);

        campoQuantidade = new JTextField();
        campoQuantidade.setBounds(210, 170, 50, 25);

        JButton adicionar = new JButton("Adicionar");
        adicionar.setBounds(280, 170, 120, 30);

        add(labelQuantidade);
        add(campoQuantidade);
        add(adicionar);

        adicionar.addActionListener(e -> adicionarLivros());

        JLabel labelEscolha = new JLabel("Livro para emprestar:");
        labelEscolha.setBounds(30, 240, 180, 25);

        campoEscolha = new JTextField();
        campoEscolha.setBounds(210, 240, 180, 25);

        JButton emprestar = new JButton("Pegar emprestado");
        emprestar.setBounds(410, 240, 150, 30);

        add(labelEscolha);
        add(campoEscolha);
        add(emprestar);

        emprestar.addActionListener(e -> emprestarLivro());

        JLabel labelDevolver = new JLabel("Livro para devolver:");
        labelDevolver.setBounds(30, 310, 180, 25);

        campoDevolver = new JTextField();
        campoDevolver.setBounds(210, 310, 180, 25);

        JButton devolver = new JButton("Devolver");
        devolver.setBounds(410, 310, 120, 30);

        add(labelDevolver);
        add(campoDevolver);
        add(devolver);

        devolver.addActionListener(e -> devolverLivro());
}
       private void cadastrarUsuario() {

        String email = campoEmail.getText();
        String senha = new String(campoSenha.getPassword());

        if(email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha os campos em branco.");
             return;
        }
        
        if(!email.contains("@gmail.com")) {
            JOptionPane.showMessageDialog(null, "Formato de email invalido.");
             return;
        }

        if(senha.length() < 8 ) {
            JOptionPane.showMessageDialog(null, "A senha deve ter no mínimo 8 caracteres.");
             return;
        }
        
        for (Usuario u : usuario) {
     if (u.getGmail().equals(email)) {
        JOptionPane.showMessageDialog(null, "Esse email já foi cadastrado, digite outro.");
         return;
    }

}
        Usuario info = new Usuario(email, senha);
        usuario.add(info);

         JOptionPane.showMessageDialog(null, "Cadastrado!");
        
    }

 private boolean encontrado = false;
 private void login() {

        String email = campoEmail.getText();
        String senha = new String(campoSenha.getPassword());

      encontrado = false;  
     
        for (Usuario u : usuario) {
           
         if (email.equals(u.getGmail()) && senha.equals(u.getSenha())) {
                encontrado = true;
                break;
            }
       }
        
        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha os campos em branco.");
        } else if (encontrado) {
           JOptionPane.showMessageDialog(null, "Login realizado!");
          } else if(!encontrado) {
           JOptionPane.showMessageDialog(null, "Email ou senha incorretos.");
        } 

    }

    private void adicionarLivros() {
        
        if (!encontrado) {
        JOptionPane.showMessageDialog(null, "Faça login.");
        return;
    }
        
        int quantidade;
           try {
            quantidade = Integer.parseInt(campoQuantidade.getText());
          } catch (Exception e) {
              JOptionPane.showMessageDialog(null,"Digite um número válido.");
                return;
        }

        for (int i = 0; i < quantidade; i++) {

            String titulo = JOptionPane.showInputDialog("Título:");
            String autor = JOptionPane.showInputDialog("Autor:");
            int ano;
            try {
                ano = Integer.parseInt(JOptionPane.showInputDialog("Ano:"));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,"Digite um número válido.");
                return;
            }

            Livro livros = new Livro(titulo, autor, ano, true);
            livro.add(livros);
        }

        mostrarLivrosDisponiveis();
    }

    private void mostrarLivrosDisponiveis() {

        String livros = "";
        for (Livro l : livro) {

            if (l.getDisponivel()) {
                livros += String.format("Título: %s\nAutor: %s\nAno: %d\n\n",l.getTitulo(), l.getAutor(), l.getAnoPublicacao());
          }
        }

        if (livros.isEmpty()) {
           JOptionPane.showMessageDialog(null, "Nenhum livro disponível");
        } else {
            JOptionPane.showMessageDialog(null, livros); 
        }
         
    }

    private void emprestarLivro() {

        if (!encontrado) {
        JOptionPane.showMessageDialog(null, "Faça login.");
        return;
    }
        
        String titulo = campoEscolha.getText();

        if(titulo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o espaço em branco");
            mostrarLivrosDisponiveis();
            return;
        }
        
        for (Livro l : livro) {

            if (titulo.equalsIgnoreCase(l.getTitulo()) && l.getDisponivel()) {
                  l.setDisponivel(false);
                  JOptionPane.showMessageDialog(null,"Livro emprestado!");
                  mostrarLivrosDisponiveis();
                   return;
            } 
        }
         
        JOptionPane.showMessageDialog(null, "Livro não encontrado.");
    }

    private void devolverLivro() {

        if (!encontrado) {
        JOptionPane.showMessageDialog(null, "Faça login.");
        return;
    }
        
        String titulo = campoDevolver.getText();

        if(titulo.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, preencha o espaço em branco");
            return;
        }
        
        for (Livro l : livro) {

            if (titulo.equalsIgnoreCase(l.getTitulo())) {
                 l.setDisponivel(true);
                  JOptionPane.showMessageDialog(null,"Livro devolvido!");
                  mostrarLivrosDisponiveis();
                     return;
            } 
        }
       
        JOptionPane.showMessageDialog(null,"Livro não encontrado.");
    }
     public static void main(String[] args) throws Exception {

        JFrame janela = new JFrame("Biblioteca");
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setSize(650, 450);
        janela.setLocationRelativeTo(null);
        janela.add(new Biblioteca());
        janela.setVisible(true);
    }
}
