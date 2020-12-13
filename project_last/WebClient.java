import java.io.*;
import java.net.*;
import java.util.*;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class WebClient {

	public static void main(String[] args) throws IOException {
		WebClient client = new WebClient();

		Scanner scan = new Scanner(System.in);
		String urlString;
		String data;
		String s;
		while (true) {
			int mode = scan.nextInt();

			if (mode == 1) {
				urlString = scan.next();
				s = client.getWebContentByGet(urlString);
				System.out.println(s);
			} else if (mode == 2) {
				urlString = scan.next();
				data = scan.next();
				s = client.getWebContentByPost(urlString, data);
				System.out.println(s);
			} else {
				break;
			}
		}
	}

	public String getWebContentByPost(String urlString, String data) throws IOException {
		return getWebContentByPost(urlString, data, "UTF-8", 5000);// iso-8859-1
	}

	public String getWebContentByGet(String urlString) throws IOException {
		return getWebContentByGet(urlString, "iso-8859-1", 5000);
	}

	public String getWebContentByGet(String urlString, final String charset, int timeout) throws IOException {
		if (urlString == null || urlString.length() == 0) {
			return null;
		}
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString
				: ("http://" + urlString).intern();
		String fileType = urlString.substring(urlString.lastIndexOf(".") + 1, urlString.length());

		URL url = new URL(urlString);

		if (fileType.equals("jpg")) {
			String fileName = urlString.substring(urlString.lastIndexOf("/") + 1, urlString.length());

			ReadableByteChannel rbc = Channels.newChannel(url.openStream());
			FileOutputStream fos = new FileOutputStream(fileName);

			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE); // 처음부터 끝까지 다운로드
			fos.close();
			return "file Download Complete";
		}

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		
//		System.out.println("INPUT HEADER");
//		Scanner scan = new Scanner(System.in);
//		String s = scan.next();
		// conn.setRequestProperty("User-Agent", "Mozilla/4.0; (compatible; MSIE 6.0;
		// Windows NT 5.2; Trident/4.0; .NET CLR 1.1.4322; .NET CLR 2.0.50727)");
		conn.setRequestProperty("User-Agent", "2019044711/CHANWOONG+KIM/WEBCLIENT/COMPUTERNETWORK");
		conn.setRequestProperty("Accept", "text/html");
		conn.setConnectTimeout(timeout);
		try {
			if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		InputStream input = conn.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset));
		String line = null;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		if (reader != null) {
			reader.close();
		}
		if (conn != null) {
			conn.disconnect();
		}
		return sb.toString();
	}

	public String getWebContentByPost(String urlString, String data, final String charset, int timeout)
			throws IOException {
		if (urlString == null || urlString.length() == 0) {
			return null;
		}
		urlString = (urlString.startsWith("http://") || urlString.startsWith("https://")) ? urlString
				: ("http://" + urlString).intern();
		URL url = new URL(urlString);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		connection.setDoOutput(true);
		connection.setDoInput(true);
		connection.setRequestMethod("POST");

		connection.setUseCaches(false);
		connection.setInstanceFollowRedirects(true);

		connection.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");

//		System.out.println("INPUT HEADER");
//		Scanner scan = new Scanner(System.in);
//		String s = scan.next();
		// connection.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE
		// 8.0; Windows vista)");
		connection.setRequestProperty("User-Agent", "2019044711/CHANWOONG+KIM/WEBCLIENT/COMPUTERNETWORK");

		connection.setRequestProperty("Accept", "text/xml");
		connection.setConnectTimeout(timeout);
		connection.connect();
		DataOutputStream out = new DataOutputStream(connection.getOutputStream());

		byte[] content = data.getBytes("UTF-8");

		out.write(content);
		out.flush();
		out.close();

		try {
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				return null;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
		String line;
		StringBuffer sb = new StringBuffer();
		while ((line = reader.readLine()) != null) {
			sb.append(line).append("\r\n");
		}
		if (reader != null) {
			reader.close();
		}
		if (connection != null) {
			connection.disconnect();
		}
		return sb.toString();
	}
}
