package epiccube.com.br.infobairrotcc.validator;


public class Validar {

    public static boolean CADASTRO(String nome,String email,String senha){
        if(nome == null || email == null || senha == null ||
                nome.equals("") || email.equals("") || senha.equals("") ||
                senha.length()<6){
            return false;
        }else{
            return true;
        }
    }

    public static boolean LOGIN(String email,String senha){
        if(email == null || senha == null || email.equals("") || senha.equals("")){
            return false;
        }else{
            return true;
        }
    }
}
