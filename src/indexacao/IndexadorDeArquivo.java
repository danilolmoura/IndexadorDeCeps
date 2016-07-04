package indexacao;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.concurrent.ConcurrentMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.Serializer;

public class IndexadorDeArquivo 
{
	/**
	 * Tamanho de uma linha do arquivo de ceps.
	 */
	private final static long nTamanhoEndereco = 300L;
	
	/**
	 * Nomes dos arquivos
	 */
	private final static String strCepFileName = "ArquivosDoProjeto//cep.dat";
	private final static String strDBFileName  = "ArquivosDoProjeto//mapDBCep";
	
	public static void main( String[] args ) throws IOException 
	{
		Endereco         endereco       = new Endereco();
		RandomAccessFile fCep           = new RandomAccessFile( strCepFileName, "r" );
		long             nTotalEndereco = fCep.length() / 300;
		
		DB                         db  = DBMaker.fileDB( strDBFileName ).make();
		ConcurrentMap<String,Long> map = db.hashMap( "map", Serializer.STRING, Serializer.LONG ).createOrOpen();
		
		for( long i = 0L; i < nTotalEndereco; i++ ) 
		{
			fCep.seek          ( i * nTamanhoEndereco 									     );
			endereco.leEndereco( fCep 														 );
			map.put            ( endereco.getCep(), fCep.getFilePointer() - nTamanhoEndereco );
			
			System.out.println( "Linha " + i );
		}
		
		System.out.println( "Indexação concluída" );
		db.close();
	}
}
