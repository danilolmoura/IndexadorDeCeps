package indexacao;

import java.io.RandomAccessFile;
import java.util.Scanner;

public class BuscaEnderecoNaoIndexado {
	/**
	 * Nomes dos arquivos
	 */
	private final static String strCepFileName = "ArquivosDoProjeto//cep.dat";
	
	/**
	 * Tamanho de uma linha do arquivo de ceps.
	 */
	private final static long nTamanhoEndereco = 300L;
	
	public static void main(String[] args) throws Exception 
	{
		Scanner          sc             = new Scanner( System.in );
		Endereco         endereco       = new Endereco();
		RandomAccessFile fCep           = new RandomAccessFile( strCepFileName, "r" );
		long             nTotalEndereco = fCep.length() / 300;
		boolean 		 bCadastrado    = true;
		
		System.out.println( "Digite o Cep" );
		String strCep = sc.nextLine();
		sc.close();
		
		if( isnCEPvalido( strCep ) )
		{
			long tempoInicial = System.currentTimeMillis();
			
			for( long i = 0L; i < nTotalEndereco; i++ ) 
			{
				fCep.seek          ( i * nTamanhoEndereco );
				endereco.leEndereco( fCep 				  );
				
				
				if( strCep.equals( endereco.getCep() ) )
				{
					System.out.println( "\n\n" );
					System.out.println( "Endereço encontrado!"         );
					System.out.println( endereco.getEnderecoCompleto() );
					
					System.out.println( "\nTempo total gasto: " + ( System.currentTimeMillis() - tempoInicial ) + " milisegundos." );
				}
				else if( ( i == nTotalEndereco - 1 ) && !strCep.equals( endereco.getCep() ) )
					bCadastrado = false;
			}
			
			if( !bCadastrado )
				System.out.println( "O CEP não está cadastrado!" );
		}
		
        fCep.close();
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
}
