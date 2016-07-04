package indexacao;

import java.io.DataInput;
import java.io.IOException;
import java.nio.charset.Charset;

public class Endereco {
	
	private String logradouro;
	private String bairro;
	private String cidade;
	private String estado;
	private String sigla;
	private String cep;

	public void leEndereco(DataInput din) throws IOException
	{
		byte logradouro[] = new byte[72];
		byte bairro    [] = new byte[72];
		byte cidade    [] = new byte[72];
		byte estado    [] = new byte[72];
		byte sigla     [] = new byte[2];
		byte cep       [] = new byte[8];
		
		din.readFully( logradouro );
		din.readFully( bairro     );
		din.readFully( cidade     );
		din.readFully( estado     );
		din.readFully( sigla      );
		din.readFully( cep        );
		din.readByte (	          ); // Ultimo espaco em branco 
		din.readByte (            ); // Quebra de linha
		
		// Definie a forma como caracteres especiais estão codificados.
		Charset enc = Charset.forName("ISO-8859-1");
		
		this.cep        = new String( cep,        enc );
		this.sigla      = new String( sigla,      enc );
		this.estado     = new String( estado,     enc );
		this.bairro     = new String( bairro,     enc );
		this.cidade     = new String( cidade,     enc );
		this.logradouro = new String( logradouro, enc );
	}
	
	public String getLogradouro( ) {
		return logradouro;
	}
	
	public String getBairro( ) {
		return bairro;
	}
			
	public String getCidade( ) {
		return cidade;
	}
	
	public String getEstado( ) {
		return estado;
	}
	
	public String getSigla( ) {
		return sigla;
	}
	
	public String getCep( ) {
		return cep;
	}
	
	public String getEnderecoCompleto( )
	{
		return logradouro.trim() + ", " + bairro.trim() + ", " + cidade.trim() + ", " + sigla.trim(); 
	}
}
