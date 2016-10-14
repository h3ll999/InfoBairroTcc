package epiccube.com.br.infobairrotcc.validator;


public class ValidatorCadUsuario {

    private String nome;
    private String email;
    private String senha;


    public ValidatorCadUsuario(String nome, String email, String senha){
        this.nome = nome;
        this.email = email;
        this.senha = senha;

    }

    public static boolean validaCadastro(String nome,String email,String senha){

        if(nome == null || email == null || senha == null || senha.length()<6){
            return false;
        }else{
            return true;
        }
    }
}
