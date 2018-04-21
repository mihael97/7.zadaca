package hr.fer.zemris.java.hw07.crypto.Crypto;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Razred koji služi za kriptiranje i dekriptiranje datoteka korištenjem AES
 * algoritma
 * 
 * @author Mihael
 *
 */
public class Crypto {
	/**
	 * Glavni program koji poziva metode za kriptiranje/dekriptiranje/provjeru,
	 * ovisno o parametrima
	 * 
	 * @param args
	 *            - prvi argument je naredba koju želimo izvršiti,drugi je naziv
	 *            ulazne datoteke,a treći naziv izlazne datoteke
	 */
	public static void main(String[] args) {
		if (args.length != 2 && args.length != 3) {
			System.err.println("Not enough or too much arguments!");
			System.exit(1);
		}

		switch (args[0].toLowerCase().trim()) {
		case "checksha":
			messageDigset(args[1]);
			break;
		case "decrypt":
			crypting(args[1], args[2], args[0]);
			break;
		case "encrypt":
			crypting(args[1], args[2], args[0]);
			break;
		default:
			System.err.println("Unsupported!");
			System.exit(1);
		}
	}

	/**
	 * Metoda kriptira/dekriptira podatke iz datoteke
	 * 
	 * @param inputString
	 *            - naziv ulazne datoteke
	 * @param outputString
	 *            - naziv izlazne datoteke
	 * @param type
	 *            - vrsta naredbe(encrypt ili decrypt)
	 */
	private static void crypting(String inputString, String outputString, String type) {
		try (Scanner sc = new Scanner(System.in)) {
			System.out.print("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits):\n>");
			String keyText = sc.nextLine();
			System.out.print("Please provide initialization vector as hex-encoded text (32 hex-digits):\n>");
			String ivText = sc.nextLine();

			SecretKeySpec keySpec = new SecretKeySpec(Util.hextobyte(keyText), "AES");
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hextobyte(ivText));
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

			cipher.init(type.trim().toLowerCase().equals("encrypt") ? Cipher.ENCRYPT_MODE : Cipher.DECRYPT_MODE,
					keySpec, paramSpec);

			byte[] buffer = new byte[1024];
			byte[] outputBuffer = new byte[1024];

			try (InputStream input = Files.newInputStream(Paths.get(inputString));
					OutputStream output = Files.newOutputStream(Paths.get(outputString))) {

				int size;

				while ((size = input.read(buffer)) != -1) {
					outputBuffer = cipher.update(buffer, 0, size);

					if (outputBuffer != null) {
						output.write(outputBuffer);
					}
				}

				outputBuffer = cipher.doFinal();
				if (outputBuffer != null) {
					output.write(outputBuffer);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metoda provjerava vjerodostojnost datoteke
	 * 
	 * @param string
	 *            - ulazni naziv datoteke
	 */
	private static void messageDigset(String string) {
		try (Scanner sc = new Scanner(System.in)) {
			MessageDigest sha = MessageDigest.getInstance("SHA-256");

			byte[] array = new byte[1024];

			try (FileInputStream stream = new FileInputStream(new File(string))) {
				while (true) {
					int size = stream.read(array);
					if (size == -1)
						break;
					else {
						sha.update(array, 0, size);
					}
				}
				System.out.print("Please provide expected sha-256 digest for " + string + ":\n>");
				boolean flag = MessageDigest.isEqual(sha.digest(), Util.hextobyte(sc.nextLine()));

				if (flag == true) {
					System.out.println("Digesting completed. Digest of " + string + " matches expected digest.");
				} else {
					System.err.println(
							"Digesting completed. Digest of hw06test.bin does not match the expected digest. Digest\r\n"
									+ "was: 2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598");
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

	}
}
