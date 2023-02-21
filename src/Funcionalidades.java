import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;

public class Funcionalidades {

    
    /**
     *
     * Genera la clave de cifrado y descifrado a partir de la contraseña del usuario.
     */
    public static Key obtenerClave(String password){

        Key clave = new SecretKeySpec(password.getBytes(), 0, 16, "AES");


        return clave;

    }

    public static void cifrar(Key clave){

        Scanner sc = new Scanner(System.in);
        // 2 - Crear un Cipher
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // 3 - Iniciar el cifrado con la clave
         cipher.init(Cipher.ENCRYPT_MODE, clave);

            System.out.println("Introduzca un mensaje");
            String mensaje = sc.nextLine();
        // 4 - Llevar a cabo el cifrado
        byte[] cipherText = cipher.doFinal(mensaje.getBytes());


            escribirFichero(Base64.getEncoder().encodeToString(cipherText));



            System.out.println();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }


    }

    public static void descifrar(Key clave){

        String texto = leerFichero();
        // 2 - Crear un Cipher
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // 3 - Iniciar el descifrado con la clave
        cipher.init(Cipher.DECRYPT_MODE, clave);

        // 4 - Llevar a cabo el descifrado
        byte[] plainText = cipher.doFinal(Base64.getDecoder().decode(texto));

        // Imprimimos el mensaje descifrado:
        System.out.println(new String(plainText));

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException(e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException(e);
        } catch (BadPaddingException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }


    }




    public static void escribirFichero(String mensaje){
        try {
            String filePath = "C:\\Users\\dperea\\Downloads\\David_Perea_Garcia_HibernateCrud\\Cifrado_Descifrado_DavidPereaGarcia\\src\\guardarCifrados";
            FileWriter fw = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(mensaje);
            bw.newLine();
            bw.close();
        }catch (Exception e){
            System.out.println(e);
        }
    }





    public static String leerFichero() {

        BufferedReader br = null;
        String contenido="";

        try {
            br = new BufferedReader(new FileReader("C:\\Users\\dperea\\Downloads\\David_Perea_Garcia_HibernateCrud\\Cifrado_Descifrado_DavidPereaGarcia\\src\\guardarCifrados"));
            Scanner sc = new Scanner(br);

            // Se lee el fichero
            while (sc.hasNext()) {
                contenido = sc.nextLine();
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {

                br.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return contenido;
    }

}