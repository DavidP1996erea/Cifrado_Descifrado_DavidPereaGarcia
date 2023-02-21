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

    /**
     * Método que cifrará un mensaje introducido por el usuario. Para ello se recibe como parámetro un objeto
     * de tipo Key con la contraseña. Una vez cifrado se escribe en un fichero.
     * @param clave
     */
    public static void cifrar(Key clave){

        Scanner sc = new Scanner(System.in);

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // Iniciar el cifrado con la clave
         cipher.init(Cipher.ENCRYPT_MODE, clave);

            System.out.println("Introduzca un mensaje");
            String mensaje = sc.nextLine();
        //  Llevar a cabo el cifrado
        byte[] cipherText = cipher.doFinal(mensaje.getBytes());


        // Se escribe en el fichero
            escribirFichero(Base64.getEncoder().encodeToString(cipherText));

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

    /**
     * Método para descifrar, como el método cifrar se recibe un objeto Key con la clave. Luego se guarda en una
     * variable el contenido del fichero.
     * @param clave
     */
    public static void descifrar(Key clave){

        String texto = leerFichero();
        // 2 - Crear un Cipher
        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

        // Iniciar el descifrado con la clave
        cipher.init(Cipher.DECRYPT_MODE, clave);

        // Llevar a cabo el descifrado
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


    /**
     * Método para escribir en un fichero un String recibido por parámetro
     * @param mensaje
     */
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


    /**
     * Método que leerá un fichero y devolverá un String con el contenido de este
     * @return
     */
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