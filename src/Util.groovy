import java.security.MessageDigest

class Util
{

	def static download(String url, String filename )
	{
		while(url)
		{
			new URL(url).openConnection().with
			{ conn ->
				conn.instanceFollowRedirects = false
				url = conn.getHeaderField( "Location" )
				if( !url )
				{
					new File( filename ).withOutputStream
					{ out ->
						conn.inputStream.with
						{ inp ->
							out << inp
							inp.close()
						}
					}
				}
			}
		}
	}

	public static getOS()
	{
		def name = System.properties["os.name"].toLowerCase()

		if (name.contains("windows"))
			return os.WINDOWS
		else if (name.contains("mac"))
			return os.MAC
		else if (name.contains("nix"))
			return os.LINUX.
			else
			return null
	}

	def static getSha1(file)
	{
		int KB = 1024
		int MB = 1024*KB

		File f = new File(file)

		def messageDigest = MessageDigest.getInstance("SHA1")

		long start = System.currentTimeMillis()

		f.eachByte(MB)
		{ byte[] buf, int bytesRead ->
			messageDigest.update(buf, 0, bytesRead);
		}

		def sha1Hex = new BigInteger(1, messageDigest.digest()).toString(16).padLeft( 40, '0' )
		long delta = System.currentTimeMillis()-start
	}

	def static unzip(file, outputDir)
	{
		def zipFile = new java.util.zip.ZipFile(new File(file))
	
		zipFile.entries().each
		{
			def name = it.name;
			if (!name.contains("META-INF") && !name.endsWith("/"))
			{
				new File(outputDir+"/"+it) << zipFile.getInputStream(it).bytes
			}
		}
	}

	enum os
	{
		WINDOWS, MAC, LINUX
	}
}