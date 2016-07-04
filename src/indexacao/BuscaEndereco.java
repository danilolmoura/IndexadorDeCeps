package indexacao;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class BuscaEndereco {
	/**
	 * Nomes dos arquivos
	 */
	private final static String strCepFileName = "ArquivosDoProjeto//cep.dat";
	private final static String strDBFileName  = "ArquivosDoProjeto//mapDBCep";
	
	public static void main(String[] args) throws Exception 
	{
		Scanner                    sc       = new Scanner( System.in );
		Endereco                   endereco = new Endereco();
		RandomAccessFile           fCep     = new RandomAccessFile( strCepFileName, "r" );
		DB                         db       = pegaArquivoDB( );
		ConcurrentMap<String,Long> banco    = getMapDB( db );
		
		System.out.println( "Digite o Cep" );
		String strCep = sc.nextLine();
		sc.close();
		
		if ( !isnCEPvalido( strCep ) || !banco.containsKey( strCep ) )
			System.out.println( "O CEP não está cadastrado!" );
		else
		{
			long tempoInicial     = System.currentTimeMillis();
			long lPosicaoPonteiro = banco.get( strCep );
			
			fCep.seek          ( lPosicaoPonteiro );
			endereco.leEndereco( fCep             );
			
			
			System.out.println( "\n\n"                         );
			System.out.println( "Endereço encontrado!"         );
			System.out.println( endereco.getEnderecoCompleto() );
			
			System.out.println( "\nTempo total gasto: " + ( System.currentTimeMillis() - tempoInicial ) + " milisegundos." );
		}
		
		fechaDB( db );
	}
	
	/**
	 * Fecha banco de dados.
	 * @param banco instância do MapDB
	 */
	private static void fechaDB( DB db ) {
		db.close();
	}

	/**
	 * Verifica se o CEP é válido
	 * @param strCep CEP
	 * @return <code>TRUE</code> Caso o cep esteja no padrão e <code>FALSE</code> caso o cep não esteja no padrão.
	 */
	private static boolean isnCEPvalido( String strCep )  
	{
		boolean bValido = false;
		
		if( strCep.length() == 8 )
			return bValido = true;
		else
		{
			System.out.println( "CEP Inválido. O CEP deve conter 8 dígitos!" );
			return bValido;
		}
	}

	private static ConcurrentMap<String, Long> getMapDB( DB db ) throws FileNotFoundException
	{
		return db.hashMap( "map", Serializer.STRING, Serializer.LONG ).createOrOpen();
	}
	
	private static DB pegaArquivoDB( )
	{
		DB db = DBMaker.fileDB( strDBFileName ).make();
		
		return db;
	}
	
	
}
