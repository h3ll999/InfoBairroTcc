package epiccube.com.br.infobairrotcc.validator;


public class Validar {

    public static boolean CADASTRO(String nome,String email,String senha){

        if(nome == null || email == null || senha == null || senha.length()<6){
            return false;
        }else{
            return true;
        }
    }
}
